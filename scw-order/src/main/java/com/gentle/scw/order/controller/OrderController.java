package com.gentle.scw.order.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.gentle.scw.common.consts.AppConsts;
import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.order.bean.TMember;
import com.gentle.scw.order.bean.TOrder;
import com.gentle.scw.order.service.OrderService;
import com.gentle.scw.order.vo.request.OrderConfirmVo;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@RestController
public class OrderController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private OrderService orderService;
	
	//更新订单状态的请求
	@PostMapping("/order/updateOrderState")
	public AppResponse<String> updateOrderState(@RequestParam("ordernum")String ordernum, @RequestParam("status")Integer status){
		orderService.updateOrderState(ordernum , status);
		return AppResponse.ok(null, "更新状态成功");
	}
	
	
	//创建订单保存到数据库
	@PostMapping("/order/createorder")
	public AppResponse<String> createOrder(@RequestBody OrderConfirmVo vo){
		//memberid需要设置
		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + vo.getAccessToken() + AppConsts.MEMBER_SUFFIX);
		TMember member = JSON.parseObject(memberStr, TMember.class);
		vo.setMemberid(member.getId());
		//将vo转为Torder
		TOrder order = new TOrder();
		BeanUtils.copyProperties(vo, order);
		order.setStatus(vo.getStatus()+"");
		order.setInvoice(vo.getInvoice()+"");
		//调用业务层方法存入数据库
		orderService.createOrder(order);
		//给webui响应结果
		return AppResponse.ok(null, "订单保存成功");
	}
}
