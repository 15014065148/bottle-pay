package com.eveb.bottlepay.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eveb.bottlepay.common.entity.UserEntity;
import com.eveb.bottlepay.common.service.UserService;
import com.eveb.bottlepay.common.utils.Assert;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(value = "user", description = "用户信息")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @PostMapping("/createUser")
    @ApiOperation(value = "创建用户", notes = "创建用户")
    public R createUserInfo(@RequestBody UserEntity userEntity) {
    	Assert.isNull(userEntity, "参数对象不能为空");
    	Assert.isNull(userEntity.getUserId(), "userId不能为空");
        return userService.createUserInfo(userEntity.getUserId());
    }
    
    
    @GetMapping("/getUserBalance")
    @ApiOperation(value = "获取用户余额", notes = "获取用户余额")
    public R getUserBalance(@RequestParam Integer userId) {
    	Assert.isNull(userId, "用户id为空");
        return userService.getUserBalance(userId);
    }
    
    @GetMapping("/getBalanceRecord")
    @ApiOperation(value = "获取用户余额", notes = "获取用户余额变动记录")
    public R getBalanceRecord(@RequestParam Integer userId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
    	Assert.isNull(userId, "用户id不能为空");
    	Assert.isNull(pageNo, "pageNo不能为空");
    	Assert.isNull(pageSize, "pageSize不能为空");
    	PageUtils pageList = userService.getBalanceRecordList(userId, pageNo, pageSize);
    	return R.ok().put("page", pageList);
    }
}
