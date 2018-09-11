package com.eveb.bottlepay.common.entity;


import javax.persistence.Table;

import lombok.Data;

/**
 * Created by Lebron on 2018/7/3.
 */
@Data
@Table(name ="vendor")
public class Vendor {

    private Integer id;
    /**
     * 供应商Code 不可变涉及实际代码.和实际类名一致
     */
    private String vendorCode ;

    /**
     *支付机构
     */
    private String vendorName;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    /**
     * 是否启用 1启用 0 不启用
     */
    private Integer isEnable ;

    private String password ;

    private String returnUrl;
    
    private String notifyUrl;
}
