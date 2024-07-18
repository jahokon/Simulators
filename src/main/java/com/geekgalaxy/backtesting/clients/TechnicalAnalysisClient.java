package com.geekgalaxy.backtesting.clients;

import org.springframework.web.client.RestTemplate;

public class TechnicalAnalysisClient extends ForgeClient {
    public TechnicalAnalysisClient(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }


}
