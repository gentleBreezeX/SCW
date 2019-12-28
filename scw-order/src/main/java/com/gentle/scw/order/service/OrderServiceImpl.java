package com.gentle.scw.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gentle.scw.order.bean.TOrder;
import com.gentle.scw.order.bean.TOrderExample;
import com.gentle.scw.order.mapper.TOrderMapper;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TOrderMapper orderMapper;
	
	@Override
	public void createOrder(TOrder order) {
		orderMapper.insertSelective(order);
	}

	@Override
	public void updateOrderState(String ordernum, Integer status) {
		TOrderExample example = new TOrderExample();
		example.createCriteria().andOrdernumEqualTo(ordernum);
		TOrder record = new TOrder();
		record.setStatus(status+"");
		orderMapper.updateByExampleSelective(record , example);
	}

}
