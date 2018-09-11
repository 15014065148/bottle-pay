package com.eveb.bottlepay.pays.pzpay.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eveb.bottlepay.common.entity.BalanceRecord;
import com.eveb.bottlepay.common.entity.Channel;
import com.eveb.bottlepay.common.entity.PayParams;
import com.eveb.bottlepay.common.entity.PayRecord;
import com.eveb.bottlepay.common.entity.Response;
import com.eveb.bottlepay.common.entity.Vendor;
import com.eveb.bottlepay.common.mapper.BalanceRecordMapper;
import com.eveb.bottlepay.common.mapper.ChannelMapper;
import com.eveb.bottlepay.common.mapper.PayRecordMapper;
import com.eveb.bottlepay.common.mapper.UserMapper;
import com.eveb.bottlepay.common.mapper.VendorMapper;
import com.eveb.bottlepay.common.service.CommonPayService;
import com.eveb.bottlepay.common.utils.ASCIIUtils;
import com.eveb.bottlepay.common.utils.DateUtil;
import com.eveb.bottlepay.common.utils.JsonUtil;
import com.eveb.bottlepay.common.utils.MD5;
import com.eveb.bottlepay.common.utils.OkHttpProxyUtils;
import com.eveb.bottlepay.common.utils.R;
import com.eveb.bottlepay.pays.pzpay.entity.PzpayContent;

import lombok.extern.slf4j.Slf4j;


@Service("PzpayService")
@Slf4j
public class PzpayService implements CommonPayService {

    @Value("${pays.onlinePay.Pzpay.pzPayUrl}")
    private String pzPayUrl;
    @Value("${pays.onlinePay.Pzpay.pzPayQueryUrl}")
    private String pzPayQueryUrl;

    @Autowired
    private OkHttpProxyUtils okHttpProxyUtils;
    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private VendorMapper vendorMapper;
    
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    
    @Autowired
    private UserMapper userMapper;


    @Override
    public Map<String, Object> getPayUrl(PayParams pz, Vendor vendor) {
        return lianLianPay(pz.getUserId(), pz.getFee(), pz.getBankCode(), pz.getSubject(), pz.getOutTradeNo(), pz.getExtra(), pz.getChannelId(), pz.getReturnUrl(), pz.getNotifyUrl(), vendor);
    }

    /**
     * 连连支付，借记卡
     * 微信支付返回的是请求路径,要请求这个路径,才能返回二维码支付的路径,路径要转成解下码
     *
     * @param fee
     * @param bankCode
     * @param subject
     * @param out_trade_no
     * @param extra
     * @param channelId
     * @return
     */
    public Map<String, Object> lianLianPay(Integer userId, BigDecimal fee, String bankCode, String subject, String out_trade_no, String extra, Integer channelId, String returnUrl, String notifyUrl, Vendor vendor) {
    	Channel channel = channelMapper.getChannelById(channelId);
    	BigDecimal oneHundred = new BigDecimal("100");
    	fee = fee.multiply(oneHundred);
        Map<String, Object> map = getSign(out_trade_no, channel, fee, bankCode, vendor.getReturnUrl(), vendor.getNotifyUrl(), vendor.getPassword());
        String res = pzPayUrl + "?subject=" + subject + "&extra=" + extra + "&user_id=" + userId + "&" + map.get("url");
        if (map.get("urlMethod").equals(1)) {//是二维码则请求盘子
            map.put("url", okHttpProxyUtils.postForm(okHttpProxyUtils.client, res, null));
        } else {
            map.put("url", res);
        }
        return map;
    }

    private Map<String, Object> getSign(String out_trade_no, Channel channel, BigDecimal fee, String bankCode, String returnUrl, String notifyUrl, String password) {
        Map<String, Object> map = new HashMap<>();
        if (1 != channel.getId()) {
            bankCode = "";
        }

        // 取消枚举，自己组装需要的map参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("partner_id", channel.getMerNo());
        params.put("bank_id", channel.getAccount());
        params.put("pay_type", channel.getPayCode());
        params.put("notify_url", notifyUrl);
        params.put("return_url", returnUrl);
        params.put("out_trade_no", out_trade_no);
        params.put("total_fee", fee);
        params.put("bank_code", bankCode);

        // 调用获取加密签证url
        String signUrl = getSignParams(params, password);
        map.put("urlMethod", channel.getUrlMethod());
        map.put("url", signUrl);
        return map;
    }


    /**
     * 盘子支付,查询并更新
     *
     * @param billDetail
     * @return
     */
    @Override
    public R getPayResult(PayRecord record) {
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", record.getOutTradeNo());
        params.put("trade_no", record.getCustomerTradeNo());
        Channel c =channelMapper.getChannelById(record.getChannelId());
        
        Vendor vendor =vendorMapper.getVendorById(c.getVendorId());

        params.put("partner_id", c.getMerNo());
        String urlParams = ASCIIUtils.formatUrlMap(params, false, false);
        String sign = MD5.getMD5((urlParams + "&key="+vendor.getPassword()));
        String payUrl = pzPayQueryUrl + "?" + urlParams + "&sign=" + sign;
        String payRS = okHttpProxyUtils.get(okHttpProxyUtils.client, payUrl);
        Map<String, Object> rs = jsonUtil.toMap(payRS);
        if (rs.get("success").toString().equals("true")) {
            PzpayContent pzpayContent = jsonUtil.fromJson(jsonUtil.toJson(rs.get("content")), PzpayContent.class);
            //支付成功
            updatePayRecordDetail(pzpayContent, record);
        }
        return R.ok().put("code",200).put("info", record);
    }

    private PayRecord updatePayRecordDetail(PzpayContent pzpayContent, PayRecord record){
        //支付成功
        if(record.getStatus() != 1) {
            //支付状态
            Integer status = pzpayContent.getTrade_status().equals("SUCCESS") ? 1 : 0;
            record.setStatus(status);
            record.setCustomerTradeNo(pzpayContent.getTrade_no());
            record.setPayTime(pzpayContent.getPay_time().equals("")?DateUtil.format(new Date(), DateUtil.FORMAT_18_DATE_TIME):pzpayContent.getPay_time());
            record.setPayError(pzpayContent.getPay_error());
            record.setUserBalance(status==1?(record.getDiscountsCharge()==null?record.getFee():record.getDiscountsCharge()):BigDecimal.ZERO);
            payRecordMapper.updateRayRecordInfo(record);
            
            if(status==1) {
            	//修改用户余额
            	userMapper.updateUserInfoPay(record.getUserId(), record.getDiscountsCharge()==null?record.getFee():record.getDiscountsCharge());
            }
            //同步修改用户余额状态数据
            BalanceRecord balanceRecord = balanceRecordMapper.getBalanceRecordInfo(record.getId(), record.getUserId(), 0);
            balanceRecordMapper.updateBalanceRecordStatus(balanceRecord.getId(), balanceRecord.getUserId(), status);
        }
        return record;
    }


    /**
     * 获取必须要签名的参数，并进行MD5计算并拼接参数
     *
     * @param params
     * @param password
     * @return
     */
    private String getSignParams(Map<String, Object> params, String password) {
        //Map<String, Object> params = jsonUtil.Entity2Map(entity);
        String urlParams = ASCIIUtils.formatUrlMap(params, false, false);
        String sign = MD5.getMD5((urlParams + "&key=" + password));
        return urlParams + "&sign=" + sign;
    }

}
