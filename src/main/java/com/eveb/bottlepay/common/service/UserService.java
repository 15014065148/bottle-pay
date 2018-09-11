package com.eveb.bottlepay.common.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eveb.bottlepay.common.entity.BalanceRecord;
import com.eveb.bottlepay.common.entity.UserEntity;
import com.eveb.bottlepay.common.mapper.BalanceRecordMapper;
import com.eveb.bottlepay.common.mapper.UserMapper;
import com.eveb.bottlepay.common.utils.BeanUtil;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.R;
import com.github.pagehelper.PageHelper;

/**
 * 用户管理实现类
 */
@Service("bottleUserController")
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    
    public R createUserInfo(Integer userId) {
    	UserEntity userEntity = userMapper.findUserInfoById(userId);
    	if(null == userEntity) {
    		return R.ok().put("success", userMapper.createUserInfo(userId, BigDecimal.ZERO));
    	}
        return R.ok().put("success", userEntity);
    }
    
    public R getUserBalance(Integer userId) {
        return R.ok().put("balance", userMapper.getUserBalance(userId));
    }
    
    public PageUtils getBalanceRecordList(Integer userId, Integer pageNo, Integer pageSize) {
    	PageHelper.startPage(pageNo, pageSize);
    	List<BalanceRecord> resultList = balanceRecordMapper.getBalanceRecordList(userId);
    	PageUtils page = BeanUtil.toPagedResult(resultList);
        return page;
    }
}
