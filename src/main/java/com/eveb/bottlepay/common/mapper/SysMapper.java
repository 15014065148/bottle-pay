package com.eveb.bottlepay.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMapper {

    List<String> selectProxys(@Param("groups") String groups, @Param("keys") String keys);
}
