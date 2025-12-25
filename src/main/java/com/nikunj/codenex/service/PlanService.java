package com.nikunj.codenex.service;

import java.util.List;

import com.nikunj.codenex.dto.subscription.response.PlanResponse;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();
}
