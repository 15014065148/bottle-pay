package com.eveb.bottlepay.common.entity;

import java.math.BigDecimal;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name ="balance_record")
public class BalanceRecord {
	
	/**
	 * id
	 */
    private Integer id;
	  
	/**
	  * 用户id
	*/
	private Integer userId;
	
	/**
	  * 交易类型 1购买模板 0充值
	*/
	private Integer payType;
	
	/**
	  * 支付类型id，充值记录id或模板记录id
	*/
	private Integer payTypeId;
	
	/**
     * 实际支付时间
     */
    private String payTime;
    
    /**
     * 余额变动
     */
    private BigDecimal balanceChange;
    
    /**
     * 此时余额
     */
    private BigDecimal balance;
    
    /**
     * 状态 1成功 0失败
     */
    private Integer payStatus;
    
    /**
     * 创建时间
     */
    private String createTime;
}
