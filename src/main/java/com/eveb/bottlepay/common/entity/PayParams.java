package com.eveb.bottlepay.common.entity;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PayParams", description = "瓶子支付支付入参")
public class PayParams {
	
	@ApiModelProperty(value = "充值记录id")
    private Integer id;
	
    @ApiModelProperty(value = "充值数值，最小100,单位分")
    private BigDecimal fee;
    
    @ApiModelProperty(value = "手续费")
    private BigDecimal handlingCharge;
    
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountsCharge;
    
    @ApiModelProperty(value = "备注")
    private String extra;
    
    @ApiModelProperty(value = "商品名称")
    private String subject;
    
    @ApiModelProperty(value = "支付方式Id")
    private Integer channelId;
    
    @ApiModelProperty(value = "则用户支付完成后，会返回到该 url 地址")
    private String returnUrl;
    
    @ApiModelProperty(value = "异步通知地址")
    private String notifyUrl;
    
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    
    @ApiModelProperty(value = "用户名")
    private String userName;
    
    @ApiModelProperty(value = "ip")
    private String ip;
    
    @ApiModelProperty(value = "订单号")
    private String outTradeNo;
    
    @ApiModelProperty(value = "前端订单号")
    private String customerTradeNo;
    
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    
    @ApiModelProperty(value = "页面")
    private Integer pageNo;

    @ApiModelProperty(value = "每页多少")
    private Integer pageSize;
}
