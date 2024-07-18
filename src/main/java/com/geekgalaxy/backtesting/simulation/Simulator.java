package com.geekgalaxy.backtesting.simulation;

import com.geekgalaxy.jpacore.order.OrderAction;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Simulator {
    UUID createSimulation(String name,UUID portfolioid, LocalDateTime start, LocalDateTime stop);
    void pauseSimulationAt(UUID portfolioid, LocalDateTime... time);
    void startSimulation();
    void stopSimulation();
    void pauseSimulation();
    void resumeSimulation();
    void createHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date);
    void removeHolding(UUID simulationId, UUID assetId, LocalDateTime date);
    void updateHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date, OrderAction orderAction);

}
