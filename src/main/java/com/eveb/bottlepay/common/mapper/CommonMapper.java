package com.eveb.bottlepay.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by William on 2018/2/1.
 */
public interface CommonMapper<T> extends MySqlMapper<T>,Mapper<T> {
}
