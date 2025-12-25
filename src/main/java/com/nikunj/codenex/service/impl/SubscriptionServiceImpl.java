package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.subscription.request.CheckoutRequest;
import com.nikunj.codenex.dto.subscription.response.CheckoutResponse;
import com.nikunj.codenex.dto.subscription.response.PortalResponse;
import com.nikunj.codenex.dto.subscription.response.SubscriptionResponse;
import com.nikunj.codenex.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(Long userId, CheckoutRequest request) {
        return null;
    }

    @Override
    public PortalResponse createCustomerPortalSession(Long userId) {
        return null;
    }
}
