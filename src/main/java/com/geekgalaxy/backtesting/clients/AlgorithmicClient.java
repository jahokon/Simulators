package com.geekgalaxy.backtesting.clients;

import org.springframework.web.client.RestTemplate;

public class AlgorithmicClient extends ForgeClient {

    public AlgorithmicClient(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }
}
