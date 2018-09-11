package com.eveb.bottlepay.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.entity.Channel;

@Mapper
@Component
public interface ChannelMapper {

    List<Channel> getPaymentType(@Param("vendorId") Integer vendorId);
    
    Channel getChannelById(@Param("id") Integer id);

}
