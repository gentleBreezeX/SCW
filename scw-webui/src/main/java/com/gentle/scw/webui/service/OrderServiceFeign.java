package com.gentle.scw.webui.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.vo.response.OrderConfirmVo;
@FeignClient("SCW-ORDER")
public interface OrderServiceFeign {

	@PostMapping("/order/createorder")
	public AppResponse<String> createOrder(@RequestBody OrderConfirmVo vo);
	
	@PostMapping("/order/updateOrderState")
	public AppResponse<String> updateOrderState(@RequestParam("ordernum")String ordernum, @RequestParam("status")Integer status);
}
