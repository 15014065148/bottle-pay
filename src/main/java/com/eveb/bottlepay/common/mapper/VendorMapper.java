package com.eveb.bottlepay.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.Vendor;

/**
 * Created by Lebron on 2018/7/3.
 */
@Component
@Mapper
public interface VendorMapper{
	
	List<Vendor> getPaymentVendor();
	
	Vendor getVendorById(@Param("id") Integer id);
}
