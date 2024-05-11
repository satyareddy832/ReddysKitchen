package com.satya.service;

import com.satya.model.Order;
import com.satya.response.PaymentResponse;

public interface IPaymentService {
	
	public PaymentResponse createPaymentLink(Order order) throws Exception;

}
