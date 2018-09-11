package com.eveb.bottlepay.common.entity;


import lombok.Data;

/**
 * Created by Lebron on 2018/7/3.
 */
@Data
public class SiteCode {

	/**
	 * 模板id
	 */
    private Integer id;
    
    /**
     * siteCode
     */
    private String siteCode;
    
    /**
     * 站点名称
     */
    private String siteName;
    
    /**
     * 站点url
     */
    private String siteUrl;
    
    /**
     * schemaName
     */
    private String schemaName;
    
    /**
     * 是否被引用 1是 0否
     */
    private Integer status;
}
