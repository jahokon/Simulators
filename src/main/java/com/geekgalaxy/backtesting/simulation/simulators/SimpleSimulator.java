package com.geekgalaxy.backtesting.simulation.simulators;

import com.geekgalaxy.backtesting.clients.PortfolioClient;
import com.geekgalaxy.backtesting.simulation.ClockTicker;
import com.geekgalaxy.backtesting.simulation.ClockTickerImpl;
import com.geekgalaxy.backtesting.simulation.Simulator;
import com.geekgalaxy.jpacore.entities.Portfolio;
import com.geekgalaxy.jpacore.entities.Simulering;
import com.geekgalaxy.jpacore.order.OrderAction;
import com.geekgalaxy.jpacore.repositories.PortfolioRepository;
import com.geekgalaxy.jpacore.repositories.SimuleringRepository;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Data
@Component
@Scope("prototype")
public class SimpleSimulator implements Simulator, ClockTicker.TickListener {
    private final PortfolioClient portfolioClient;
    private final SimuleringRepository simuleringRepository;
    private final PortfolioRepository portfolioRepository;
    private Simulering simulering;
    private ClockTicker clockTicker;
    private Map<LocalDateTime, List<HoldingInOrder>> createHoldingMap =new HashMap<>();
    private Map<LocalDateTime, List<HoldingInOrder>> removeHoldingMap =new HashMap<>();
    private Map<LocalDateTime, List<HoldingInOrder>> updateHoldingMap =new HashMap<>();

    @Data
    @Builder
    public static class HoldingInOrder {
        private UUID assetId;
        private int amount;
        private double price;
        private LocalDateTime date;
        private OrderAction orderAction;
    }
    @Autowired
    public SimpleSimulator(RestTemplate restTemplate, SimuleringRepository simuleringRepository, PortfolioRepository portfolioRepository) {
        portfolioClient = new PortfolioClient(restTemplate, "http://192.168.0.6:5510");
        this.simuleringRepository = simuleringRepository;
        this.portfolioRepository = portfolioRepository;
        clockTicker = new ClockTickerImpl();
        clockTicker.addTickListener(this);
    }

    @Override
    public UUID createSimulation(String name, UUID portfolioid, LocalDateTime start, LocalDateTime stop) {
        log.info("Creating simulation with name: {}, portfolio id: {}, start time: {}, stop time: {}", name, portfolioid, start, stop);

        Portfolio portfolio = portfolioRepository.findById(portfolioid).get();
        simulering = Simulering.builder()
                .name(name)
                .endDate(stop)
                .portfolio_id(portfolioid)
                .startDate(start)
                .startMoney(portfolio.getCashAccount().getStartingBalance().intValue())
                .build();
        simulering = simuleringRepository.save(simulering);
        clockTicker.setStartTime(start);
        clockTicker.setEndTime(stop);
        return simulering.getId();
    }

    @Override
    public void pauseSimulationAt(UUID simuleringId, LocalDateTime... time) {
        log.info("Pausing simulation with id: {} at times: {}", simuleringId, Arrays.toString(time));

        if (simulering.getId().equals(simuleringId)) {
            clockTicker.pauseTickerAt(time);
        }
    }

    @Override
    public void startSimulation() {
        log.info("Starting simulation");
        clockTicker.start();
    }

    @Override
    public void stopSimulation() {
        log.info("Stopping simulation");
        clockTicker.stop();
    }

    @Override
    public void pauseSimulation() {
        log.info("Pausing simulation");
        clockTicker.pause();
    }

    @Override
    public void resumeSimulation() {
        log.info("Resuming simulation");
        clockTicker.resumeTicker();
    }

    @Override
    public void createHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date) {
        log.info("Creating holding with simulation id: {}, asset id: {}, amount: {}, price: {}, date: {}", simulationId, assetId, amount, price, date);
        HoldingInOrder holdingInOrder = HoldingInOrder.builder()
                .assetId(assetId)
                .amount(amount)
                .price(price)
                .date(date)
                .orderAction(OrderAction.BUY)
                .build();
        createHoldingMap.put(date, List.of(holdingInOrder));
    }

    @Override
    public void removeHolding(UUID simulationId, UUID assetId, LocalDateTime date) {
        log.info("Removing holding with simulation id: {}, asset id: {}, date: {}", simulationId, assetId, date);
        HoldingInOrder holdingInOrder = HoldingInOrder.builder()
                .assetId(assetId)
                .date(date)
                .orderAction(OrderAction.SELL)
                .build();
        removeHoldingMap.put(date, List.of(holdingInOrder));

    }

    @Override
    public void updateHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date, OrderAction orderAction) {
        log.info("Updating holding with simulation id: {}, asset id: {}, amount: {}, price: {}, date: {}, order action: {}", simulationId, assetId, amount, price, date, orderAction);
        HoldingInOrder holdingInOrder = HoldingInOrder.builder()
                .assetId(assetId)
                .amount(amount)
                .price(price)
                .date(date)
                .orderAction(orderAction)
                .build();
        updateHoldingMap.put(date, List.of(holdingInOrder));

    }

    @Override
    public void onTick(LocalDateTime currentTime) {
        log.info("Tick: {}", currentTime);
        if (createHoldingMap.containsKey(currentTime)) {
            createHoldingMap.get(currentTime).forEach(holdingInOrder -> {
                portfolioClient.addHolding(simulering.getPortfolio_id(), holdingInOrder.getAssetId(), holdingInOrder.getAmount(), holdingInOrder.getPrice(), holdingInOrder.getDate());
            });
        }
        if (removeHoldingMap.containsKey(currentTime)) {
            removeHoldingMap.get(currentTime).forEach(holdingInOrder -> {
                portfolioClient.removeHolding(simulering.getPortfolio_id(), holdingInOrder.getAssetId());
            });
        }
        if (updateHoldingMap.containsKey(currentTime)) {
            updateHoldingMap.get(currentTime).forEach(holdingInOrder -> {
                portfolioClient.updateHolding(simulering.getPortfolio_id(), holdingInOrder.getAssetId(), holdingInOrder.getAmount(), holdingInOrder.getPrice(), holdingInOrder.getDate(), holdingInOrder.getOrderAction());
            });
        }
    }
}
