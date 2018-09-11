package com.eveb.bottlepay.common.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.BillDetail;

@Component
@Mapper
public interface BillDetailMapper {
	
	int inserBillDetail(BillDetail billDetail);
	
	List<BillDetail> findOrderList(@Param("userId") Integer userId);
	
	BillDetail findOrderInfo(@Param("id") Integer id);
	
	BillDetail findOrderInfoByTempId(@Param("userId") Integer id, @Param("templateId") Integer templateId);
	
	int updateBillDetailInfo(BillDetail billDetail);
	
	int updateListByPeriodOf();
	
	int updateBillDetail(BillDetail billDetail);
	
	int deleteOrder(@Param("id") Integer id);
	
	List<BillDetail> findAllBillDetail(BillDetail billDetail);
	
	int updateStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("isSysPay") Integer isSysPay, @Param("modifyTime") String modifyTime);

	int updatePrice(@Param("id") Integer id, @Param("discountsCharge") BigDecimal discountsCharge);
}
