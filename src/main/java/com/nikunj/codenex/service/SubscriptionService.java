package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.subscription.response.CheckoutResponse;
import com.nikunj.codenex.dto.subscription.response.PortalResponse;
import com.nikunj.codenex.dto.subscription.response.SubscriptionResponse;
import com.nikunj.codenex.dto.subscription.request.CheckoutRequest;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(Long userId, CheckoutRequest request);

    PortalResponse createCustomerPortalSession(Long userId);
}
