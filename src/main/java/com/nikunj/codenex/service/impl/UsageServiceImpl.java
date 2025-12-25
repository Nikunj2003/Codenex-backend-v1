package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.usage.response.PlanLimitsResponse;
import com.nikunj.codenex.dto.usage.response.UsageTodayResponse;
import com.nikunj.codenex.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    @Override
    public UsageTodayResponse getTodayUsage(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimits(Long userId) {
        return null;
    }
}
