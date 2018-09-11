package com.eveb.bottlepay.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.PayRecord;

@Mapper
@Component
public interface PayRecordMapper {

	void inserPayRecord(PayRecord record);
	
	PayRecord selecInfoByTradeNo(@Param("outTradeNo") String outTradeNo);
	
	void updateRayRecordInfo(PayRecord record);
	
	void updateRayRecordStatus(@Param("status") Integer status, @Param("outTradeNo") String outTradeNo);
	
	void updateCustomerTradeNo(@Param("customerTradeNo") String customerTradeNo, @Param("outTradeNo") String outTradeNo);
	
	List<PayRecord> findExchangeRecord(Integer userId);
	
	int updateListByPeriodOf();
	
	int deleteRecord(@Param("outTradeNo") String outTradeNo);
	
	int updateStatus(@Param("outTradeNo") String outTradeNo, @Param("status") Integer status, @Param("isSysPay") Integer isSysPay, @Param("modifyTime") String modifyTime, @Param("userId") Integer userId);
	
	List<PayRecord> findAllPayRecord(PayRecord payRecord);
}
