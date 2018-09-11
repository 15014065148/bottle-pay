package com.eveb.bottlepay.common.entity;

import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付动作实体类
 * @author Lebron
 *
 */
@Data
@Table(name ="bank")
public class Bank {
	
    @ApiModelProperty(value = "银行id")
    private Integer id;
    
    @ApiModelProperty(value = "支付方式id")
    private Integer channelId;
    
    @ApiModelProperty(value = "银行编码")
    private String bankCode;
    
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    
    @ApiModelProperty(value = "银行logo")
    private String bankLog;

}
