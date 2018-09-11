package com.eveb.bottlepay.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by William on 2018/2/1.
 */
@Data
public class Response {

    public Response() {}
    @ApiModelProperty(value = "商户Id")
    private String compId;
    @ApiModelProperty(value = "订单号")
    private String tradeNo;
    @ApiModelProperty(value = "第三方订单号")
    private String customerTradeNo;
    @ApiModelProperty(value = "充值金额")
    private BigDecimal fee ;
    private Integer payType;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "支付状态 0:失败 1:成功 ")
    private Integer status ;
    @ApiModelProperty(value = "请求时携带的参数 ")
    private String extra ;
    @ApiModelProperty(value = "请求时携带的参数 ")
    private String subject;
    @ApiModelProperty(value = "请求时携带的参数 ")
    private Integer channelId;
    @ApiModelProperty(value = "支付时间 ")
    private String payTime;
    @ApiModelProperty(value = "支付成功与否信息")
    private String payError;

    /**
     *
     * @param compId 商户Id
     * @param tradeNo 订单号
     * @param customerTradeNo 第三方订单号
     * @param fee 充值金额
     * @param createTime 创建时间
     * @param status 支付状态 0:失败 1:成功 2:待支付 (未支付订单,1小时候自动变失败)
     */
    public Response(String tradeNo, String customerTradeNo, BigDecimal fee, String createTime, Integer status,String extra,Integer channelId, String payTime, String payError) {
        this.tradeNo = tradeNo;
        this.customerTradeNo = customerTradeNo;
        this.fee = fee;
        this.createTime = createTime;
        this.status = status;
        this.extra=extra;
        this.channelId =channelId;
        this.payTime = payTime;
        this.payError = payError;
    }

}
