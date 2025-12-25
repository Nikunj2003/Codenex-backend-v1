package com.nikunj.codenex.controller;

import com.nikunj.codenex.dto.usage.response.PlanLimitsResponse;
import com.nikunj.codenex.dto.usage.response.UsageTodayResponse;
import com.nikunj.codenex.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;

    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getTodayUsage() {
        Long userId = 1L; // TODO: Get from security context
        return ResponseEntity.ok(usageService.getTodayUsage(userId));
    }

    @GetMapping("/limits")
    public ResponseEntity<PlanLimitsResponse> getPlanLimits() {
        Long userId = 1L; // TODO: Get from security context
        return ResponseEntity.ok(usageService.getCurrentSubscriptionLimits(userId));
    }
}
