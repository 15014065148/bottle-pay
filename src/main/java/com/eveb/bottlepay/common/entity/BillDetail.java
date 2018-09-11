package com.eveb.bottlepay.common.entity;

import java.math.BigDecimal;

import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付动作实体类
 * @author Lebron
 *
 */
@Data
@Table(name ="bill_detail")
public class BillDetail {
	
    @ApiModelProperty(value = "订单号")
    private Integer id ;
    
    @ApiModelProperty(value = "用户Id")
    private Integer userId;
    
    @ApiModelProperty(value = "用户名")
    private String userName;
    
    @ApiModelProperty(value = "模板id")
    private Integer templateId;
    
    @ApiModelProperty(value = "支付金额")
    private BigDecimal money;
    
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountsCharge;
    
    @ApiModelProperty(value = "套餐")
    private String sets;
    
    @ApiModelProperty(value = "套餐详情")
    private String setDetail;
    
    @ApiModelProperty(value = "支付类型 1、余额支付")
    private Integer payType;
    
    @ApiModelProperty(value = "产品图片地址")
    private String imagePath;
    
    @ApiModelProperty(value = "产品链接地址")
    private String urlPath;
    
    @ApiModelProperty(value = "状态 1、已支付 0未支付 2已取消 3已失效")
    private Integer status;
    
    @ApiModelProperty(value = " 0非 1是")
    private Integer isSysPay;
    
    @ApiModelProperty(value = "站点编号")
    private String siteNo;
    
    @ApiModelProperty(value = "站点名称")
    private String siteName;
    
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    
    @ApiModelProperty(value = "最后修改时间")
    private String modifyTime;
    
    private String startTime;
    private String endTime;

}
