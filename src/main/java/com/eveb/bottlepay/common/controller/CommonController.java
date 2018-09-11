package com.eveb.bottlepay.common.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eveb.bottlepay.common.entity.PayRecord;
import com.eveb.bottlepay.common.service.PayService;
import com.eveb.bottlepay.common.utils.Assert;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Created by Lebron on 2018/7/2.
 */
@RestController
@RequestMapping("/recharge")
@Api(value = "bootle-pay", description = "瓶子支付")
public class CommonController {
	
    @Autowired
    private PayService payService;
    
    @GetMapping("/getPaymentType")
    @ApiOperation(value = "获取支付方式", notes = "获取支付方式")
    public R getPaymentType(@RequestParam Integer vendorId) {
    	Assert.isNull(vendorId, "支付渠道不能为空");
        return payService.getPaymentType(vendorId);
    }
    
    @GetMapping("/getPaymentVendor")
    @ApiOperation(value = "获取支付渠道", notes = "获取支付渠道")
    public R getPaymentVendor() {
        return payService.getPaymentVendor();
    }

    /**
     * 支付接口
     * @param pz
     * @return
     */
    @GetMapping("/payUrl")
    @ApiOperation(value = "瓶子支付,线上支付，充值,获取充值的路径", notes = "瓶子支付,线上支付，充值,获取充值的路径")
    public R getPayUrl(@RequestParam Integer userId, @RequestParam String userName, @RequestParam BigDecimal fee, @RequestParam Integer channelId,
    		@RequestParam String extra, @RequestParam String subject, @RequestParam String bankCode, HttpServletRequest request) {
    	Assert.isNull(userId, "用户id不能为空");
    	Assert.isNull(userName, "用户名不能为空");
    	Assert.isNull(fee, "金额不能为空");
    	Assert.isNull(channelId, "支付方式不能为空");
    	Assert.isNull(subject, "商品名称不能为空");
    	Assert.isNull(bankCode, "银行代码不能为空");
        return payService.getPayUrl(userId, userName, fee, channelId, extra, subject, bankCode, request);
    }

    /**
     * 查询支付状态接口
     * @param 
     * @return
     */
    @GetMapping("/findResultByTradeNo")
    @ApiOperation(value = "查询充值结果", notes = "查询充值结果")
    public R getPayResult(@RequestParam @ApiParam("订单号") String tradeNo) {
        return payService.getPayResult(tradeNo);
    }
    
    @GetMapping("/findExchangeRecord")
    @ApiOperation(value = "查询用户充值记录", notes = "查询用户充值记录")
    public R findExchangeRecord(@RequestParam Integer userId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
    	Assert.isNull(userId, "用户id不能为空");
    	Assert.isNull(pageNo, "pageNo不能为空");
    	Assert.isNull(pageSize, "pageSize不能为空");
    	PageUtils pageList = payService.findExchangeRecord(userId, pageNo, pageSize);
        return R.ok().put("page", pageList);
    }
    
    
    @DeleteMapping("/deleteRecord/{tradeNo}")
    @ApiOperation(value = "删除充值记录", notes = "删除充值记录")
    public R deleteRecord(@PathVariable("tradeNo") String tradeNo) {
    	Assert.isBlank(tradeNo, "订单号不能为空");
    	return payService.deleteRecord(tradeNo);
    }
    
    @GetMapping("/updateStatus")
    @ApiOperation(value = "后台修改充值记录状态", notes = "后台修改充值记录状态")
    public R updateStatus(@RequestParam String tradeNo, @RequestParam Integer status) {
    	Assert.isBlank(tradeNo, "订单号不能为空");
    	Assert.isNull(status, "状态不能为空");
        return payService.updateStatus(tradeNo, status);
    }
    
    @GetMapping("/findAllPayRecord")
    @ApiOperation(value = "查询所有用户充值记录", notes = "查询所有用户充值记录")
    public R findAllPayRecord(@ModelAttribute PayRecord payRecord, @RequestParam("pageNo") @NotNull Integer pageNo, @RequestParam("pageSize") @NotNull Integer pageSize) {
    	Assert.isNull(payRecord, "参数对象不能为空");
    	Assert.isNull(pageNo, "pageNo不能为空");
    	Assert.isNull(pageSize, "pageSize不能为空");
    	PageUtils pageList = payService.findAllPayRecord(payRecord, pageNo, pageSize);
        return R.ok().put("page", pageList);
    }

}
