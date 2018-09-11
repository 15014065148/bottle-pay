package com.eveb.bottlepay.common.service;

import com.eveb.bottlepay.common.mapper.SysMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("sysConfigService")
public class SysConfigService {

    @Autowired
    private SysMapper sysMapper;

    public List<String> getProxys(String groups, String keys) {
        return sysMapper.selectProxys(groups, keys);
    }
}