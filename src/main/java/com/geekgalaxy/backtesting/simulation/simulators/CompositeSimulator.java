package com.geekgalaxy.backtesting.simulation.simulators;

import com.geekgalaxy.backtesting.simulation.Simulator;
import com.geekgalaxy.jpacore.order.OrderAction;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompositeSimulator implements Simulator {
    @Override
    public UUID createSimulation(String name,UUID portfolioid, LocalDateTime start, LocalDateTime stop) {
        return null;
    }

    @Override
    public void pauseSimulationAt(UUID portfolioid, LocalDateTime... time) {

    }

    @Override
    public void startSimulation() {

    }

    @Override
    public void stopSimulation() {

    }

    @Override
    public void pauseSimulation() {

    }

    @Override
    public void resumeSimulation() {

    }

    @Override
    public void createHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date) {

    }

    @Override
    public void removeHolding(UUID simulationId, UUID assetId, LocalDateTime date) {

    }

    @Override
    public void updateHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date, OrderAction orderAction) {

    }
}
