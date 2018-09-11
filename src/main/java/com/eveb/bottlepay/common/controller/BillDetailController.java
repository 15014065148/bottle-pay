package com.eveb.bottlepay.common.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eveb.bottlepay.common.entity.BillDetail;
import com.eveb.bottlepay.common.service.BillDetailService;
import com.eveb.bottlepay.common.utils.Assert;
import com.eveb.bottlepay.common.utils.PageUtils;
import com.eveb.bottlepay.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(value = "bootle-pay", description = "订单管理")
public class BillDetailController {
	
    @Autowired
    private BillDetailService billDetailService;
    
    @PostMapping("/createOrder")
    @ApiOperation(value = "购买模板,创建订单", notes = "购买模板,创建订单")
    public R createOrder(@RequestBody BillDetail billDetail) {
    	Assert.isNull(billDetail, "参数对象不能为空");
    	Assert.isNull(billDetail.getUserId(), "用户id为空");
    	Assert.isBlank(billDetail.getUserName(), "用户名为空");
    	Assert.isNull(billDetail.getTemplateId(), "模板id为空");
    	Assert.isNull(billDetail.getMoney(), "金额不能为空");
    	Assert.isNull(billDetail.getSets(), "套餐不能为空");
    	Assert.isNull(billDetail.getSetDetail(), "套餐详情不能为空");
    	Assert.isNull(billDetail.getSiteNo(), "站点编号不能为空");
    	Assert.isNull(billDetail.getSiteName(), "站点名称不能为空");
    	Assert.isNull(billDetail.getImagePath(), "产品图片地址不能为空");
    	Assert.isNull(billDetail.getUrlPath(), "产品链接地址不能为空");
        return billDetailService.createOrder(billDetail);
    }
    
    @PostMapping("/coverOrder")
    @ApiOperation(value = "覆盖模板,修改订单", notes = "覆盖模板,修改订单")
    public R coverOrder(@RequestBody BillDetail billDetail) {
    	Assert.isNull(billDetail, "参数对象不能为空");
    	Assert.isNull(billDetail.getUserId(), "用户id为空");
    	Assert.isNull(billDetail.getTemplateId(), "模板id为空");
    	Assert.isNull(billDetail.getMoney(), "金额不能为空");
    	Assert.isNull(billDetail.getSets(), "套餐不能为空");
    	Assert.isNull(billDetail.getSetDetail(), "套餐详情不能为空");
    	Assert.isNull(billDetail.getSiteNo(), "站点编号不能为空");
    	Assert.isNull(billDetail.getSiteName(), "站点名称不能为空");
    	Assert.isNull(billDetail.getImagePath(), "产品图片地址不能为空");
    	Assert.isNull(billDetail.getUrlPath(), "产品链接地址不能为空");
        return billDetailService.coverOrder(billDetail);
    }
    
    @GetMapping("/payOrder")
    @ApiOperation(value = "支付扣款,购买模板", notes = "支付扣款,购买模板")
    public R payOrder(@RequestParam Integer id) {
    	Assert.isNull(id, "订单id为空");
        return billDetailService.payOrder(id);
    }
    
    @GetMapping("/cancelOrder")
    @ApiOperation(value = "取消购买模板订单", notes = "取消购买模板订单")
    public R cancelOrder(@RequestParam Integer id) {
    	Assert.isNull(id, "订单id为空");
        return billDetailService.cancelOrder(id);
    }
    
    
    @GetMapping("/findAllOrderList")
    @ApiOperation(value = "查询用户所有订单接口", notes = "查询用户所有订单接口")
    public R findAllOrderList(@RequestParam Integer userId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
    	Assert.isNull(userId, "用户id为空");
    	Assert.isNull(pageNo, "pageNo为空");
    	Assert.isNull(pageSize, "pageSize为空");
    	PageUtils pageList = billDetailService.findOrderList(userId, pageNo, pageSize);
        return R.ok().put("page", pageList);
    }
    
    
    @GetMapping("/findOrderInfo")
    @ApiOperation(value = "查询用户单条订单详细信息接口", notes = "查询用户单条订单详细信息接口")
    public R findOrderInfo(@RequestParam Integer id) {
    	Assert.isNull(id, "订单id为空");
        return billDetailService.findOrderInfo(id);
    }
    
    
    @DeleteMapping("/deleteOrder/{id}")
    @ApiOperation(value = "删除模板订单记录", notes = "删除模板订单记录")
    public R deleteRecord(@PathVariable("id") Integer id) {
    	Assert.isNull(id, "订单号不能为空");
    	return billDetailService.deleteOrder(id);
    }
    
    @GetMapping("/findAllBillDetail")
    @ApiOperation(value = "查询所有用户模板记录", notes = "查询所有用户模板记录")
    public R findAllBillDetail(@ModelAttribute BillDetail billDetail, @RequestParam("pageNo") @NotNull Integer pageNo, @RequestParam("pageSize") @NotNull Integer pageSize) {
    	Assert.isNull(billDetail, "参数对象不能为空");
    	Assert.isNull(pageNo, "pageNo不能为空");
    	Assert.isNull(pageSize, "pageSize不能为空");
    	PageUtils pageList = billDetailService.findAllBillDetail(billDetail, pageNo, pageSize);
        return R.ok().put("page", pageList);
    }
    
    
    @GetMapping("/updateStatus")
    @ApiOperation(value = "后台修改模板记录状态", notes = "后台修改模板记录状态")
    public R updateStatus(@RequestParam Integer id, @RequestParam Integer status) {
    	Assert.isNull(id, "订单号不能为空");
    	Assert.isNull(status, "状态不能为空");
        return billDetailService.updateStatus(id, status);
    }
    
    @GetMapping("/updatePrice")
    @ApiOperation(value = "后台修改模板价格", notes = "后台修改模板价格")
    public R updatePrice(@RequestParam Integer id, @RequestParam BigDecimal discountsCharge) {
    	Assert.isNull(id, "订单号不能为空");
    	Assert.isNull(discountsCharge, "价格不能为空");
        return billDetailService.updatePrice(id, discountsCharge);
    }

    @GetMapping("/getSiteCode")
    @ApiOperation(value = "获取siteCode", notes = "获取siteCode")
    public R bindTemplateDomain(@RequestParam String templateName) {
        Assert.isBlank(templateName, "模板名称不能为空");
        return billDetailService.bindTemplateDomain(templateName);
    }
    
    
    @GetMapping("/getSiteUrl")
    @ApiOperation(value = "获取siteUrl", notes = "获取siteUrl")
    public R getSiteUrl(@RequestParam List<String> siteCodes) {
    	Assert.isNull(siteCodes, "SiteCode不能为空");
        return billDetailService.getSiteUrl(siteCodes);
    }
    
}
