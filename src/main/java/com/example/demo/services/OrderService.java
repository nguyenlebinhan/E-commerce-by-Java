package com.example.demo.services;

import com.example.demo.payloads.OrderDTO;
import com.example.demo.payloads.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderDTO placeOrder(String emailId, Long cartId, String paymentMethod);

    OrderDTO getOrder(String emailId, Long orderId);

    List<OrderDTO> getOrderByUser(String emailId);

    OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderDTO  updateOrder(String emailId, Long orderId,String orderStatus);
}
