package com.nikunj.codenex.controller;

import com.nikunj.codenex.dto.subscription.request.CheckoutRequest;
import com.nikunj.codenex.dto.subscription.response.CheckoutResponse;
import com.nikunj.codenex.dto.subscription.response.PlanResponse;
import com.nikunj.codenex.dto.subscription.response.PortalResponse;
import com.nikunj.codenex.dto.subscription.response.SubscriptionResponse;
import com.nikunj.codenex.service.PlanService;
import com.nikunj.codenex.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BillingController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/plans")
    public ResponseEntity<List<PlanResponse>> getPlans() {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription() {
        Long userId = 1L; // TODO: Get from security context
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutSession(@RequestBody CheckoutRequest request) {
        Long userId = 1L; // TODO: Get from security context
        return ResponseEntity.ok(subscriptionService.createCheckoutSessionUrl(userId, request));
    }

    @PostMapping("/stripe/portal")
    public ResponseEntity<PortalResponse> createCustomerPortal() {
        Long userId = 1L; // TODO: Get from security context
        return ResponseEntity.ok(subscriptionService.createCustomerPortalSession(userId));
    }
}
