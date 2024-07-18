package com.geekgalaxy.backtesting.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Slf4j
public class ForgeClient  {
    protected final RestTemplate restTemplate;
    protected final String baseUrl;
    public ForgeClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    protected <T> List<T> fetchData(String url, Map<String, Object> parameters, Class<T[]> responseType) {

        String finalUrl = baseUrl + url;
        String delimiter="?";
        for (String key : parameters.keySet()) {
            finalUrl += delimiter + key + "=" + parameters.get(key);
            delimiter="&";
        }
        try {
            T[] dataArray = restTemplate.getForObject(finalUrl, responseType);
            return Arrays.asList(dataArray);
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
}
