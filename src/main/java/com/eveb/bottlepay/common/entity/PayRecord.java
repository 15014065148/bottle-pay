package com.eveb.bottlepay.common.entity;


import java.math.BigDecimal;

import javax.persistence.Table;

import lombok.Data;

/**
 * Created by Lebron on 2018/7/3.
 */
@Data
@Table(name ="pay_record")
public class PayRecord {

	/**
	 * id
	 */
    private Integer id;
    
    /**
     * 金额
     */
    private BigDecimal fee;
    
    /**
     * 支付渠道id
     */
    private Integer vendorId;
    
    /**
     * 手续费
     */
    private BigDecimal handlingCharge;
    
    /**
     *优惠金额
     */
    private BigDecimal discountsCharge;
    
    /**
     * 订单号
     */
    private String outTradeNo;
    
    /**
     * 盘子订单号
     */
    private String customerTradeNo;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 商品名称
     */
    private String subject;
    
    /**
     * 状态 1成功 0未成功 2已取消 3已失效
     */
    private Integer status;
    
    /**
     * 用户当前余额
     */
    private BigDecimal userBalance;
    
    /**
     * 充值描述
     */
    private String remark;
    
    /**
     * 支付方式
     */
    private Integer channelId;
    
    /**
     * 用户ip
     */
    private String ip;
    
    /**
     * 是否为后台支付 0非 1是
     */
    private Integer isSysPay;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 修改时间
     */
    private String modifyTime;
    
    /**
     * 实际支付时间
     */
    private String payTime;
    
    /**
     * 支付信息
     */
    private String payError;
    
    private String startTime;
    private String endTime;
    
}
