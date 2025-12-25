package com.nikunj.codenex.dto.subscription.response;

import com.nikunj.codenex.enums.SubscriptionStatus;
import java.time.Instant;

public record SubscriptionResponse(
        PlanResponse plan,
        SubscriptionStatus status,
        Instant periodEnd,
        Long tokensUsedThisCycle) {
}
