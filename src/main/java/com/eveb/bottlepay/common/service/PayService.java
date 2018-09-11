package com.eveb.bottlepay.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.eveb.bottlepay.common.entity.BalanceRecord;
import com.eveb.bottlepay.common.entity.Bank;
import com.eveb.bottlepay.common.entity.Channel;
import com.eveb.bottlepay.common.entity.PayParams;
import com.eveb.bottlepay.common.entity.PayRecord;
import com.eveb.bottlepay.common.entity.Vendor;
import com.eveb.bottlepay.common.exception.R200Exception;
import com.eveb.bottlepay.common.mapper.BalanceRecordMapper;
import com.eveb.bottlepay.common.mapper.BankMapper;
import com.eveb.bottlepay.common.mapper.ChannelMapper;
import com.eveb.bottlepay.common.mapper.PayRecordMapper;
import com.eveb.bottlepay.common.mapper.UserMapper;
import com.eveb.bottlepay.common.mapper.VendorMapper;
import com.eveb.bottlepay.common.utils.Assert;
import com.eveb.bottlepay.common.utils.BeanUtil;
import com.eveb.bottlepay.common.utils.CommonUtil;
import com.eveb.bottlepay.common.utils.DateUtil;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.PayContents;
import com.eveb.bottlepay.common.utils.R;
import com.eveb.bottlepay.common.utils.SnowFlake;
import com.eveb.bottlepay.common.utils.SpringContextUtil;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Lebron on 2018/7/3.
 */
@Slf4j
@Service
public class PayService {

    @Resource(name = "BillDetailService")
    private BillDetailService billDetailService;
    
    @Autowired
    private ChannelMapper channelMapper;
    
    @Autowired
    private PayRecordMapper payRecordMapper;
    
    @Autowired
    private VendorMapper vendorMapper;
    
    @Autowired
    private BankMapper bankMapper;
    
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 线上支付，充值,获取充值的路径
     * @param pz
     * @return
     */
    public R getPayUrl(Integer userId, String userName, BigDecimal fee, Integer channelId,
    		String extra, String subject, String bankCode, HttpServletRequest request) {
    	PayParams pz = new PayParams();
    	pz.setFee(fee);
        if(StringUtils.isEmpty(fee)){
            return R.ok().put("code", 2000).put("请输入充值金额");
        }
        Long outTradeNo = new SnowFlake().nextId();
        //验证用户充值金额在不在允许范围
        Channel channel = channelMapper.getChannelById(channelId);
        if (null==channel) { 
        	throw new R200Exception("支付类型不存在!");
        }
        
        pz.setUserId(userId);
        pz.setUserName(userName);
    	pz.setChannelId(channelId);
    	pz.setExtra(extra);
    	pz.setSubject(subject);
        pz.setOutTradeNo(outTradeNo.toString());
        pz.setIp(CommonUtil.getIpAddress(request));
        pz.setBankCode(bankCode);
        
        PayRecord record = new PayRecord();
        BalanceRecord bRecord = new BalanceRecord();
        //保存充值信息
        saveFundDespoit(pz, record, bRecord);
        
        Vendor vendor = vendorMapper.getVendorById(channel.getVendorId());
        CommonPayService pay=getCommonPayService(vendor);
        Map<String, Object> paramr = pay.getPayUrl(pz, vendor);
        paramr.put("outTradeNo", outTradeNo.toString());
        if (paramr.get("urlMethod").equals(1) && paramr.get("url").toString().contains("维护中")) {
        	//修改订单状态为失败
        	payRecordMapper.updateRayRecordStatus(2, outTradeNo.toString());
        	//修改余额变动表为失败
        	balanceRecordMapper.updateBalanceRecordStatus(bRecord.getId(), bRecord.getUserId(), 0);
            return R.error(2000, "收款账户维护中");
        } 
        
        if(paramr.get("urlMethod").equals(1) && paramr.get("url").toString().contains("SUCCESS")) {
        	Map urlMaps = (Map)JSON.parse(paramr.get("url").toString());
        	Map contentMaps = (Map)JSON.parse(urlMaps.get("content").toString());
        	String customerTradeNo = contentMaps.get("trade_no").toString();
        	payRecordMapper.updateCustomerTradeNo(customerTradeNo, outTradeNo.toString());
        }
        return R.ok().put("code", 200).put("URL",paramr);
    }
    
    /**
	 * 保存支付信息 TODO: 手续费，优惠金额暂时写死，后期要动态根据配置计算
	 * 
	 * @param pzpayPayParams
	 */
	public void saveFundDespoit(PayParams pz, PayRecord record, BalanceRecord bRecord) {
		record.setFee(pz.getFee());
		//BigDecimal feeScale = getFeeScale(pz.getFee().divide(new BigDecimal(100)),pz.getUserId());
		//record.setHandlingCharge(feeScale);
		//无优惠金额
		//record.setDiscountsCharge();
		record.setCustomerTradeNo(pz.getCustomerTradeNo());
		record.setSubject(pz.getSubject());
		record.setOutTradeNo(pz.getOutTradeNo());
		record.setUserId(pz.getUserId());
		record.setUserName(pz.getUserName());
		record.setStatus(0); //0未完成
		record.setRemark(pz.getExtra());
		record.setChannelId(pz.getChannelId());
		record.setIp(pz.getIp());
		record.setIsSysPay(0);
		record.setCreateTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
		record.setModifyTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
		
		//充值记录保存到表中
		payRecordMapper.inserPayRecord(record);
		
		bRecord.setUserId(pz.getUserId());
		bRecord.setPayTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
		bRecord.setCreateTime(DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME));
		bRecord.setPayType(0); //充值
		bRecord.setPayTypeId(record.getId());
		bRecord.setBalanceChange(pz.getFee());
		bRecord.setPayStatus(0);//未成功
		//余额变动记录
		balanceRecordMapper.insertBalanceRecordInfo(bRecord);
	}
	
	
	public R getPaymentType(Integer vendorId){
        List<Channel> channelList = channelMapper.getPaymentType(vendorId);
        Set<Integer> idSets = new HashSet<>();
        for (Channel channel : channelList) {
        	idSets.add(channel.getId());
		}
        
        List<Bank> bankList = bankMapper.findBankListByChannelId(idSets);
        for (Channel channel : channelList) {
        	List<Bank> bnkList = new ArrayList<>();
	        for (Bank bank : bankList) {
				if(channel.getId().equals(bank.getChannelId())) {
					bnkList.add(bank);
				}
			}
	        channel.setBankList(bnkList);
        }
        return R.ok().put("code",200).put("list",channelList);
    }
	
	public R getPaymentVendor(){
        List<Vendor> vendorList = vendorMapper.getPaymentVendor();
        return R.ok().put("code",200).put("info", vendorList);
    }
    
    
    /**
	 * 判断支付是否可进行,比较单笔存款的最大值或最小值 并且用户非正常操作判断
	 * 
	 * @param fee 单位是分
	 * @param payType
	 * @return
	 */
	public boolean judgeSaveConditions(BigDecimal fee, int channelId) {
		// 根据支付方式获取支付最大值
		Channel channel = channelMapper.getChannelById(channelId);
		return !(fee.divide(new BigDecimal(100)).compareTo(new BigDecimal(channel.getMinLimit())) == -1
				|| fee.divide(new BigDecimal(100)).compareTo(new BigDecimal(channel.getMaxLimit())) == 1);
	}

    /**
     * 获取代理对象
     * @param vendor
     * @return
     */
    private CommonPayService getCommonPayService(Vendor vendor){
        String payService =vendor.getVendorCode() + PayContents.SERVICE;
        return (CommonPayService) SpringContextUtil.getBean(payService);
    }
    
    /**
     * 查询支付状态,并更新
     * @param billDetail
     * @return
     */
    private R updateBillDetail(PayRecord record){
    	//等于0为未完成
        if(record.getStatus() == 0) {
            Vendor vendor = vendorMapper.getVendorById(record.getVendorId());
            CommonPayService pay=getCommonPayService(vendor);
            return pay.getPayResult(record);
        }
        return R.ok().put("code",200).put("info",record);
    }
    
    /**
     * 根据订单号查询
     * @param tradeNo 订单号
     * @return
     */
    public R getPayResult(String tradeNo) {
        PayRecord record =payRecordMapper.selecInfoByTradeNo(tradeNo);
        Assert.isNull(record, "无该订单记录");
        return updateBillDetail(record);
    }

    /**
     * 根据用户id查询充值记录
     * @param tradeNo 订单号
     * @return
     */
    public PageUtils findExchangeRecord(Integer userId, Integer pageNo, Integer pageSize) {
    	PageHelper.startPage(pageNo, pageSize);
        List<PayRecord> list =payRecordMapper.findExchangeRecord(userId);
        PageUtils page = BeanUtil.toPagedResult(list);
        return page;
    }
    
    /**
     * 删除充值订单
     * @param tradeNo
     * @return
     */
    public R deleteRecord(String tradeNo) {
    	PayRecord payRecord = payRecordMapper.selecInfoByTradeNo(tradeNo);
    	Assert.isNull(payRecord, "无该订单记录");
    	if(payRecord.getStatus().equals(1)){
    		return R.error().put("faild", "不能删除已支付订单");
    	}
        return R.ok().put("success", payRecordMapper.deleteRecord(tradeNo));
    }
    
    /**
     * 修改充值记录状态
     * @param tradeNo
     * @return
     */
    public R updateStatus(String tradeNo, Integer status) {
    	PayRecord payRecord = payRecordMapper.selecInfoByTradeNo(tradeNo);
    	Assert.isNull(payRecord, "订单不存在");
    	if(!payRecord.getStatus().equals(1) && status.equals(1)) {
        	//修改用户余额
        	userMapper.updateUserInfoPay(payRecord.getUserId(), payRecord.getDiscountsCharge()==null?payRecord.getFee():payRecord.getDiscountsCharge());
        }
    	payRecordMapper.updateStatus(tradeNo, status, status.equals(1)?1:0, DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME), payRecord.getUserId());
    	//同步修改用户余额状态数据
        BalanceRecord balanceRecord = balanceRecordMapper.getBalanceRecordInfo(payRecord.getId(), payRecord.getUserId(), 0);
        balanceRecordMapper.updateBalanceRecordStatus(balanceRecord.getId(), balanceRecord.getUserId(), status.equals(1)?1:0);
        return R.ok().put("success", 0);
    }
    
    /**
     * 查询所有用户充值记录
     * @return
     */
    public PageUtils findAllPayRecord(PayRecord payRecord, Integer pageNo, Integer pageSize) {
    	PageHelper.startPage(pageNo, pageSize);
        List<PayRecord> list =payRecordMapper.findAllPayRecord(payRecord);
        PageUtils page = BeanUtil.toPagedResult(list);
        return page;
    }
}

