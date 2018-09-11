package com.eveb.bottlepay.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eveb.bottlepay.common.entity.BalanceRecord;
import com.eveb.bottlepay.common.entity.BillDetail;
import com.eveb.bottlepay.common.entity.SiteCode;
import com.eveb.bottlepay.common.mapper.BalanceRecordMapper;
import com.eveb.bottlepay.common.mapper.BillDetailMapper;
import com.eveb.bottlepay.common.mapper.SiteCodeMapper;
import com.eveb.bottlepay.common.mapper.UserMapper;
import com.eveb.bottlepay.common.utils.Assert;
import com.eveb.bottlepay.common.utils.BeanUtil;
import com.eveb.bottlepay.common.utils.DateUtil;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.R;
import com.github.pagehelper.PageHelper;

/**
 * 订单处理接口实现类
 */
@Service("BillDetailService")
public class BillDetailService {

    @Autowired
    private BillDetailMapper billDetailMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    
    @Autowired
    private SiteCodeMapper siteCodeMapper;
    
    public R createOrder(BillDetail billDetail) {
    	billDetail.setStatus(0);//未支付
    	billDetail.setPayType(1); //当前只有余额支付
    	billDetail.setIsSysPay(0); //是否后台操作
    	billDetail.setCreateTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	billDetail.setModifyTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	
    	BillDetail resultBill = null;
    	//查询是否存在记录
		try {
			resultBill = billDetailMapper.findOrderInfoByTempId(billDetail.getUserId(), billDetail.getTemplateId());
	    	if (null == resultBill) {
	    		billDetailMapper.inserBillDetail(billDetail);
	    		
	    		//插入记录到余额变动表
	    		BalanceRecord bRecord = new BalanceRecord();
	    		bRecord.setUserId(billDetail.getUserId());
	    		bRecord.setPayTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
	    		bRecord.setCreateTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
	    		bRecord.setPayType(1); //购买模板
	    		bRecord.setPayTypeId(billDetail.getId());
	    		bRecord.setBalanceChange(billDetail.getDiscountsCharge()==null?billDetail.getMoney():billDetail.getDiscountsCharge());
	    		bRecord.setPayStatus(0);//未成功
	    		//余额变动记录
	    		balanceRecordMapper.insertBalanceRecordInfo(bRecord);
	    		return R.ok().put("success", billDetail);
			}
		} catch (Exception e) {
			return R.error(201, "已存在此模板记录");
		}
    	//状态未0返回数据，未1提示已有此模板
		if(0 == resultBill.getStatus()) {
			return R.ok().put("success", resultBill);
		}
		return R.error(201, "已存在此模板记录");
    }
    
    
    public R coverOrder(BillDetail billDetail) {
    	billDetail.setStatus(0);//未支付
    	billDetail.setPayType(1); //当前只有余额支付
    	billDetail.setIsSysPay(0);
    	billDetail.setCreateTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	billDetail.setModifyTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	
		return R.ok().put("success", billDetailMapper.updateBillDetail(billDetail));
    }
    
    @Transactional
    public R payOrder(Integer id) {
    	BillDetail billDetail = billDetailMapper.findOrderInfo(id);
    	Assert.isNull(billDetail, "不存在该订单");
    	if(1==billDetail.getStatus()) {
    		return R.ok().put("success", billDetail);
    	}
    	BigDecimal balance = userMapper.getUserBalance(billDetail.getUserId());
    	if(null ==balance) {
    		return R.error(201, "余额不足");
    	}
    	BigDecimal surplusBalance = BigDecimal.ZERO;
    	
    	//BillDetail billDetail11 = billDetailMapper.findInfoByTempId(billDetail.getUserId(), billDetail.getTemplateId());
    	BalanceRecord bRecord = balanceRecordMapper.getBalanceRecordInfo(billDetail.getId(), billDetail.getUserId(), 1);
    	//有优惠金额优先扣减优惠金额
    	if(null != billDetail.getDiscountsCharge()) {
    		surplusBalance = balance.subtract(billDetail.getDiscountsCharge());
    		
    		//判断余额是否不足，供前端调用，不抛异常
    		if(-1==surplusBalance.compareTo(BigDecimal.ZERO)) {
    	    	balanceRecordMapper.updateBalanceRecordStatus(bRecord.getId(), bRecord.getUserId(), 0);
    			return R.error(201, "余额不足");
    		}
    	} else {
    		surplusBalance = balance.subtract(billDetail.getMoney());
    		//判断余额是否不足，供前端调用，不抛异常
    		if(-1==surplusBalance.compareTo(BigDecimal.ZERO)) {
    			balanceRecordMapper.updateBalanceRecordStatus(bRecord.getId(), bRecord.getUserId(), 0);
    			return R.error(201, "余额不足");
    		}
    	}
    	userMapper.updateUserInfo(billDetail.getUserId(), surplusBalance);
    	
    	billDetail.setStatus(1);//已支付
    	billDetail.setModifyTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	billDetailMapper.updateBillDetailInfo(billDetail);
    	balanceRecordMapper.updateBalanceRecordStatus(bRecord.getId(), bRecord.getUserId(), billDetail.getStatus());
        return R.ok().put("success", billDetail);
    }
    
    public R cancelOrder(Integer id) {
    	BillDetail billDetail = billDetailMapper.findOrderInfo(id);
    	Assert.isNull(billDetail, "不存在该订单");
    	billDetail.setStatus(2);//已失效
    	billDetail.setModifyTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	
    	//取消订单不需要设置值，失效也是失败
    	/*BalanceRecord bRecord = balanceRecordMapper.getBalanceRecordInfo(billDetail.getId(), billDetail.getUserId(), 1);
    	if(null != bRecord) {
    		balanceRecordMapper.updateBalanceRecordStatus(bRecord.getId(), bRecord.getUserId(), 2);
    	}*/
    	return R.ok().put("success", billDetailMapper.updateBillDetailInfo(billDetail));
    }
    
    public PageUtils findOrderList(Integer userId, Integer pageNo, Integer pageSize) {
    	PageHelper.startPage(pageNo, pageSize);
    	List<BillDetail> list = billDetailMapper.findOrderList(userId);
        PageUtils page = BeanUtil.toPagedResult(list);
		return page;
    }
    
    
    public R findOrderInfo(Integer id) {
        return R.ok().put("info", billDetailMapper.findOrderInfo(id));
    }
    
    
    /**
     * 删除模板订单
     * @param id
     * @return
     */
    public R deleteOrder(Integer id) {
    	BillDetail billDetail = billDetailMapper.findOrderInfo(id);
    	Assert.isNull(billDetail, "无该订单记录");
    	if(billDetail.getStatus().equals(1)){
    		return R.error().put("faild", "不能删除已购买订单");
    	}
        return R.ok().put("success", billDetailMapper.deleteOrder(id));
    }
    
    /**
     * 查询所有用户模板记录
     * @return
     */
    public PageUtils findAllBillDetail(BillDetail billDetail, Integer pageNo, Integer pageSize) {
    	PageHelper.startPage(pageNo, pageSize);
        List<BillDetail> list =billDetailMapper.findAllBillDetail(billDetail);
        PageUtils page = BeanUtil.toPagedResult(list);
        return page;
    }
    
    /**
     * 修改模板记录状态
     * @param id，status
     * @return
     */
    public R updateStatus(Integer id, Integer status) {
    	BillDetail billDetail = billDetailMapper.findOrderInfo(id);
    	Assert.isNull(billDetail, "订单不存在");
    	billDetailMapper.updateStatus(id, status, status.equals(1)?1:0, DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
    	//同步修改用户余额状态数据
        return R.ok().put("success", balanceRecordMapper.deleteBalanceRecord(billDetail.getId(), 1, billDetail.getUserId()));
    }
    
    /**
     * 修改模板订单价格
     * @param id, discountsCharge
     * @return
     */
    public R updatePrice(Integer id, BigDecimal discountsCharge) {
    	BillDetail billDetail = billDetailMapper.findOrderInfo(id);
    	Assert.isNull(billDetail, "订单不能为空");
    	if(!billDetail.getStatus().equals(0)) {
    		return R.ok().put("success", "只能修改未完成的订单");
    	}
    	//修改订单价格
    	billDetailMapper.updatePrice(id, discountsCharge);
    	//修改余额变动记录
    	balanceRecordMapper.updatePrice(billDetail.getId(), 1, billDetail.getUserId(), discountsCharge);
        return R.ok().put("success", 0);
    }


	public R bindTemplateDomain(String templateName) {
		//获取manager未使用的站点
		SiteCode siteCode = siteCodeMapper.getSiteCodeInfo();
		if(null==siteCode) {
			return R.error(201, "已无站点分配");
		}
		siteCodeMapper.updateSiteCodeInfo(siteCode.getId(), templateName);
		return R.ok().put("success", siteCode.getSiteCode());
	}
    
    
    public R getSiteUrl(List<String> siteCode) {
    	return R.ok().put("success", siteCodeMapper.getSiteUrlList(siteCode));
    }
}
