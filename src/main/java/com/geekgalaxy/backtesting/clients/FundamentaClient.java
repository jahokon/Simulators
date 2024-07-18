package com.geekgalaxy.backtesting.clients;

import com.geekgalaxy.backtesting.simulation.Simulator;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

public class FundamentaClient extends ForgeClient {

    public FundamentaClient(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }
}
