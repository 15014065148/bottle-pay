package com.eveb.bottlepay.common.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.BalanceRecord;

@Component
@Mapper
public interface BalanceRecordMapper {
	
	int insertBalanceRecordInfo(BalanceRecord balanceRecord);
	
	int updateBalanceRecordStatus(@Param("id") Integer id, @Param("userId") Integer userId, @Param("payStatus") Integer payStatus);
	
	BalanceRecord getBalanceRecordInfo(@Param("payTypeId") Integer payTypeId, @Param("userId") Integer userId, @Param("payType") Integer payType);
	
	List<BalanceRecord> getBalanceRecordList(@Param("userId") Integer userId);
	
	int updatePrice(@Param("payTypeId") Integer id, @Param("payType") Integer payType, @Param("userId") Integer userId, @Param("balanceChange") BigDecimal balanceChange);
	
	int deleteBalanceRecord(@Param("payTypeId") Integer id, @Param("payType") Integer payType, @Param("userId") Integer userId);
}
