package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.subscription.response.PlanResponse;
import com.nikunj.codenex.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
