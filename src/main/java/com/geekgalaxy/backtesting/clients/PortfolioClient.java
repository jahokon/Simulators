package com.geekgalaxy.backtesting.clients;

import com.geekgalaxy.jpacore.order.OrderAction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.ta4j.core.BarSeries;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PortfolioClient extends ForgeClient {
    public PortfolioClient(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }

    public String isAlive() {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/isAlive").build().toUri();
        return restTemplate.getForObject(uri, String.class);
    }

    public UUID createPortfolio(String name, LocalDateTime startDate, Double startingCash) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/createPortfolio")
                .queryParam("name", name)
                .queryParam("startDate", startDate)
                .queryParam("startingCash", startingCash)
                .build().toUri();
        return restTemplate.postForObject(uri, null, UUID.class);
    }

    public String deletePortfolio(UUID portfolioId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/deletePortfolio")
                .queryParam("portfolioId", portfolioId.toString())
                .build().toUri();
        return restTemplate.postForObject(uri, null, String.class);
    }

    public UUID addHolding(UUID portfolioId, UUID instrumentId, long quantity, double price, LocalDateTime date) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/addHolding")
                .queryParam("portfolioId", portfolioId.toString())
                .queryParam("instrumentId", instrumentId.toString())
                .queryParam("quantity", quantity)
                .queryParam("price", price)
                .queryParam("date", date)
                .build().toUri();
        return restTemplate.postForObject(uri, null, UUID.class);
    }

    public String removeHolding(UUID portfolioId, UUID instrumentId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/removeHolding")
                .queryParam("portfolioId", portfolioId.toString())
                .queryParam("instrumentId", instrumentId.toString())
                .build().toUri();
        return restTemplate.postForObject(uri, null, String.class);
    }

    public String updateHolding(UUID portfolioId, UUID instrumentId, long quantity, double price, LocalDateTime date, OrderAction orderAction) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/updateHolding")
                .queryParam("portfolioId", portfolioId.toString())
                .queryParam("instrumentId", instrumentId.toString())
                .queryParam("quantity", quantity)
                .queryParam("price", price)
                .queryParam("date", date)
                .queryParam("orderAction", orderAction)
                .build().toUri();
        return restTemplate.postForObject(uri, null, String.class);
    }

    public Optional<BarSeries> getHoldingAsBarSeries(UUID holdingId, LocalDate from, int daysBack) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/getHoldingAsBarSeries")
                .queryParam("holdingId", holdingId.toString())
                .queryParam("from", from)
                .queryParam("daysBack", daysBack)
                .build().toUri();
        return Optional.ofNullable(restTemplate.getForObject(uri, BarSeries.class));
    }

    public Optional<BarSeries> getPortfolioAsBarSeries(UUID portfolioId, LocalDate from, int daysBack) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/portfolio/getPortfolioAsBarSeries")
                .queryParam("portfolioId", portfolioId.toString())
                .queryParam("from", from)
                .queryParam("daysBack", daysBack)
                .build().toUri();
        return Optional.ofNullable(restTemplate.getForObject(uri, BarSeries.class));
    }
}
