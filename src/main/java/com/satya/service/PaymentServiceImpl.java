package com.satya.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Service;

import com.satya.model.Order;
import com.satya.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;



@Service
public class PaymentServiceImpl implements IPaymentService {

	@Value("${stripe.api.key}")
	private String stripeSecretKey;
	
	@Override
	public PaymentResponse createPaymentLink(Order order) throws Exception {
		
		Stripe.apiKey=stripeSecretKey;
		
		SessionCreateParams params=SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:3000/payment/success"+order.getId())
				.setCancelUrl("http://localhost:3000/payment/")
				.addLineItem(SessionCreateParams.LineItem.builder()
						.setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency("inr")
								.setUnitAmount((Long) order.getTotalPrice()*100)
								.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("Reddys Kitchen").build())
								.build()
								).build()
						).build();
		
		Session session =Session.create(params);
		PaymentResponse res=new PaymentResponse();
		res.setPayment_url(session.getUrl());
		
		
		return res;
	}

}
