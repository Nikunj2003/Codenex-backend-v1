package com.nikunj.codenex.dto.subscription.response;


public record PlanResponse(
        Long id,
        String name,
        String price,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Boolean unlimitedAi) {
}
