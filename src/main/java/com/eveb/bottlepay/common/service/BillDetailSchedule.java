package com.eveb.bottlepay.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.mapper.BillDetailMapper;

@Component
public class BillDetailSchedule {

    @Autowired
    BillDetailMapper billDetailMapper;
    
    @Scheduled(cron="0 */1 * * * ?")
    public void updateListByPeriodOf(){
    	billDetailMapper.updateListByPeriodOf();
    }
    
}
