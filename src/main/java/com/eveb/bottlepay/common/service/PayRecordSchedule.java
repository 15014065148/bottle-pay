package com.eveb.bottlepay.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eveb.bottlepay.common.mapper.PayRecordMapper;

@Component
public class PayRecordSchedule {

    @Autowired
    PayRecordMapper payRecordMapper;
    
    @Scheduled(cron="0 */1 * * * ?")
    public void updateListByPeriodOf(){
    	payRecordMapper.updateListByPeriodOf();
    }
}
