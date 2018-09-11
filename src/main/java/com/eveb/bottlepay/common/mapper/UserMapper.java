package com.eveb.bottlepay.common.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.UserEntity;

@Component
@Mapper
public interface UserMapper {
	
	int createUserInfo(@Param("userId")Integer userId, @Param("balance") BigDecimal balance);
	
	int updateUserInfo(@Param("userId")Integer userId, @Param("balance") BigDecimal balance);
	
	int updateUserInfoPay(@Param("userId")Integer userId, @Param("balance") BigDecimal balance);
	
	BigDecimal getUserBalance(@Param("userId") Integer userId);
	
	UserEntity findUserInfoById(@Param("userId")Integer userId);
	
}
