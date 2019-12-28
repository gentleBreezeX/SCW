package com.gentle.scw.order.service;

import com.gentle.scw.order.bean.TOrder;

public interface OrderService {

	void createOrder(TOrder order);

	void updateOrderState(String ordernum, Integer status);

}
