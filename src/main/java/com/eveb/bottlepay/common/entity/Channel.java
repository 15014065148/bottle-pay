package com.eveb.bottlepay.common.entity;


import java.util.List;

import javax.persistence.Table;

import lombok.Data;

/**
 * Created by Lebron on 2018/7/3.
 */
@Data
@Table(name ="channel")
public class Channel {

	/**
	 * id
	 */
    private Integer id;
    
    /**
     * 供应商
     */
    private Integer vendorId;
    
    /**
     * 商户号，第三方给
     */
    private String merNo;
    
    /**
     * 账户名称
     */
    private String name;
    
    /**
     * 收款账户
     */
    private String account;
    
    /**
     * 支付编码
     */
    private String code;
    
    /**
     * 支持设备 1:pc  2:移动 3: pc + 移动
     */
    private Integer suportMachine;
    
    /**
     * 单笔存款最大值
     */
    private Integer maxLimit;
    
    /**
     * 单日最大限额
     */
    private Integer maxLimitDaily;
    
    /**
     * 单笔存款最小值
     */
    private Integer minLimit;
    
    /**
     * 账户描述
     */
    private String description;
    
    /**
     * 是否启用 1启用 2禁用
     */
    private Integer isEnable;
    
    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 创建者
     */
    private String createUser;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 更新人
     */
    private String modifyUser;
    
    /**
     * 最后一次更新时间
     */
    private String modifyTime;
    
    /**
     * 第三方支付中這個支付渠道的代號
     */
    private String payCode;
    
    /**
     * 是否二维码 0不是, 1是
     */
    private Integer urlMethod;
    
    /**
     * 支付方式logo
     */
    private String logoUrl;
    
    private List<Bank> bankList;
    
}
