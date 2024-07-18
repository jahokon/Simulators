package com.geekgalaxy.backtesting;

import com.geekgalaxy.backtesting.simulation.SimulatingService;
import com.geekgalaxy.backtesting.simulation.SimulatorType;
import com.geekgalaxy.jpacore.order.OrderAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
public class SimulationController {
    @Autowired
    SimulatingService simulatingService;

    @PostMapping("/createSimulation/{simulatorType}/{name}/{portfolioid}/{start}/{stop}")
    public UUID createSimulation(@PathVariable SimulatorType simulatorType, @PathVariable String name, @PathVariable UUID portfolioid, @PathVariable LocalDateTime start, @PathVariable LocalDateTime stop) {
        return simulatingService.createSimulation(simulatorType, name, portfolioid, start, stop);
    }

    @PostMapping("/pauseSimulationAt/{portfolioid}/{time}")
    public void pauseSimulationAt(@PathVariable UUID portfolioid, @PathVariable LocalDateTime... time) {
        simulatingService.pauseSimulationAt(portfolioid, time);
    }

    @PostMapping("/startSimulation")
    public void startSimulation() {
        simulatingService.startSimulation();
    }

    @PostMapping("/stopSimulation")
    public void stopSimulation() {
        simulatingService.stopSimulation();
    }

    @PostMapping("/pauseSimulation")
    public void pauseSimulation() {
        simulatingService.pauseSimulation();
    }

    @PostMapping("/resumeSimulation")
    public void resumeSimulation() {
        simulatingService.resumeSimulation();
    }

    @PostMapping("/createHolding/{simulationId}/{assetId}/{amount}/{price}/{date}")
    public void createHolding(@PathVariable UUID simulationId, @PathVariable UUID assetId, @PathVariable int amount, @PathVariable double price, @PathVariable LocalDateTime date) {
        simulatingService.createHolding(simulationId, assetId, amount, price, date);
    }

    @PostMapping("/removeHolding/{simulationId}/{assetId}/{date}")
    public void removeHolding(@PathVariable UUID simulationId, @PathVariable UUID assetId, @PathVariable LocalDateTime date) {
        simulatingService.removeHolding(simulationId, assetId, date);
    }

    @PostMapping("/updateHolding/{simulationId}/{assetId}/{amount}/{price}/{date}/{orderAction}")
    public void updateHolding(@PathVariable UUID simulationId, @PathVariable UUID assetId, @PathVariable int amount, @PathVariable double price, @PathVariable LocalDateTime date, @PathVariable OrderAction orderAction) {
        simulatingService.updateHolding(simulationId, assetId, amount, price, date, orderAction);
    }

}
