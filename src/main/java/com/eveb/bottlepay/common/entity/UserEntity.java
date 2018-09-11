package com.eveb.bottlepay.common.entity;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserEntity {

	/**
	 * 主键id
	 */
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 用户余额
     */
    private BigDecimal balance;
    
}
