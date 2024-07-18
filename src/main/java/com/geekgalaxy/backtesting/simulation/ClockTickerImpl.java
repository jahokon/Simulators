package com.geekgalaxy.backtesting.simulation;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Component
public class ClockTickerImpl implements ClockTicker {
    private final List<TickListener> listeners;
    private final List<LocalDateTime> pauseTimes;
    private ChronoUnit unit;
    private int amount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime pauseTime;
    private boolean paused;
    private LocalDateTime currentTime;
    private final ScheduledExecutorService executorService;

    /**
     * Constructor for ClockTickerImpl.
     * Initializes the listeners and pauseTimes lists, sets paused to false, and creates a single-threaded executor service.
     */
    public ClockTickerImpl() {
        listeners = new ArrayList<>();
        pauseTimes = new ArrayList<>();
        paused = false;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }

    @Override
    public void pause() {
        paused = true;
    }

    /**
     * Adds a TickListener to the listeners list.
     *
     * @param listener The listener to be added.
     */
    @Override
    public void addTickListener(TickListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a TickListener from the listeners list.
     *
     * @param listener The listener to be removed.
     */
    @Override
    public void removeTickListener(TickListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the resolution of the ClockTicker.
     *
     * @param amount The amount of time units for the resolution.
     * @param unit The unit of time for the resolution.
     */
    @Override
    public void setResolution(int amount, ChronoUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Sets the start time of the ClockTicker.
     *
     * @param startTime The start time for the ClockTicker.
     */
    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the ClockTicker.
     *
     * @param endTime The end time for the ClockTicker.
     */
    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Adds times to the pauseTimes list. The ClockTicker will be paused at these times.
     *
     * @param time The times at which the ClockTicker should be paused.
     */
    @Override
    public void pauseTickerAt(LocalDateTime... time) {
        pauseTimes.addAll(List.of(time));
    }

    /**
     * Resumes the ClockTicker after it has been paused.
     * Schedules the tick() method to run at a fixed rate.
     */
    @Override
    public void resumeTicker() {
        paused = false;
        currentTime = pauseTime.plus(amount, unit);
        long delay = unit.getDuration().toSeconds() * amount;
        executorService.scheduleAtFixedRate(this::tick, 0, delay, TimeUnit.SECONDS);
    }

    /**
     * Starts the ClockTicker.
     * Sets the current time to the start time and schedules the tick() method to run at a fixed rate.
     */
    public void start() {
        currentTime = startTime;
        long delay = unit.getDuration().toSeconds() * amount;
        executorService.scheduleAtFixedRate(this::tick, 0, delay, TimeUnit.SECONDS);
    }

    /**
     * Ticks the ClockTicker.
     * If the current time is before the end time and the ClockTicker is not paused, notifies the listeners and increments the current time.
     * If the current time is in the pauseTimes list, pauses the ClockTicker.
     */
    public void tick() {
        if (currentTime.isBefore(endTime) && !paused) {
            notifyListeners();
            currentTime = currentTime.plus(amount, unit);
            if (pauseTimes.contains(currentTime)) {
                pauseTicker(currentTime);
            }
        }
    }

    /**
     * Notifies the listeners of a tick.
     * Calls the onTick() method of each listener with the current time.
     */
    private void notifyListeners() {
        for (TickListener listener : listeners) {
            listener.onTick(currentTime);
        }
    }

    /**
     * Pauses the ClockTicker.
     *
     * @param pauseTime The time at which the ClockTicker is paused.
     */
    private void pauseTicker(LocalDateTime pauseTime) {
        paused = true;
        this.pauseTime = pauseTime;
    }
}