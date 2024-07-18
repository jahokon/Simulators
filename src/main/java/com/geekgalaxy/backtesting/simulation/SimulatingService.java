package com.geekgalaxy.backtesting.simulation;

import com.geekgalaxy.backtesting.simulation.simulators.SimpleSimulator;
import com.geekgalaxy.jpacore.order.OrderAction;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SimulatingService {
    private final ApplicationContext appCtx;
    private Simulator simulator;

    public SimulatingService(ApplicationContext appCtx) {
        this.appCtx = appCtx;
    }

    public UUID createSimulation(SimulatorType simulatorType,String name, UUID portfolioid, LocalDateTime start, LocalDateTime stop) {

        switch (simulatorType) {
            case SIMPLE_HISTORICAL:
                simulator=appCtx.getBean(SimpleSimulator.class);
                return simulator.createSimulation(name, portfolioid, start, stop);

            // Add other cases here as new simulator types become available
            default:
                throw new IllegalArgumentException("Invalid simulator type: " + simulatorType);
        }
    }

    public void pauseSimulationAt(UUID portfolioid, LocalDateTime... time) {
        simulator.pauseSimulationAt(portfolioid, time);
    }

    public void startSimulation() {
        simulator.startSimulation();
    }

    public void stopSimulation() {
        simulator.stopSimulation();
    }

    public void pauseSimulation() {
        simulator.pauseSimulation();
    }

    public void resumeSimulation() {
        simulator.resumeSimulation();
    }

    public void createHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date) {
        simulator.createHolding(simulationId, assetId, amount, price, date);
    }

    public void removeHolding(UUID simulationId, UUID assetId, LocalDateTime date) {
        simulator.removeHolding(simulationId, assetId, date);
    }

    public void updateHolding(UUID simulationId, UUID assetId, int amount, double price, LocalDateTime date, OrderAction orderAction) {
        simulator.updateHolding(simulationId, assetId, amount, price, date, orderAction);
    }


}
