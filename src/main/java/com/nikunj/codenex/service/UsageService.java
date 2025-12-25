package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.usage.response.PlanLimitsResponse;
import com.nikunj.codenex.dto.usage.response.UsageTodayResponse;

public interface UsageService {
    UsageTodayResponse getTodayUsage(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimits(Long userId);
}
