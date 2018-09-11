package com.eveb.bottlepay.common.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.Bank;

@Component
@Mapper
public interface BankMapper {
	
	List<Bank> findBankListByChannelId(@Param("sets") Set<Integer> sets);
	
}
