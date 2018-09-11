package com.eveb.bottlepay.pays.pzpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eveb.bottlepay.pays.pzpay.service.PzpayService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bottlePay/pzPay")
@Api(value = "pzPay", description = "盘子充值，支付")
@Slf4j
public class PzpayController {

    @Autowired
    private PzpayService pzpayService;

}
