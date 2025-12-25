package com.nikunj.codenex.dto.usage.response;

public record PlanLimitsResponse(
        String planName,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Boolean unlimitedAi) {
}
