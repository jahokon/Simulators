package com.geekgalaxy.backtesting.simulation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The ClockTicker interface represents a clock that ticks at a specified resolution.
 * It allows for the registration of listeners that will be notified at each tick.
 * The clock can be started, paused, and resumed, and the start and end times can be set.
 */
public interface ClockTicker {


    void stop();

    void pause();

    /**
     * The TickListener interface represents a listener that will be notified at each tick of the ClockTicker.
     */
    public interface TickListener {

        /**
         * Method called at each tick of the ClockTicker.
         *
         * @param currentTime The current time of the tick.
         */
        void onTick(LocalDateTime currentTime);
    }

    /**
     * Registers a TickListener to be notified at each tick.
     *
     * @param listener The listener to be registered.
     */
    void addTickListener(TickListener listener);

    /**
     * Unregisters a TickListener so it will no longer be notified at each tick.
     *
     * @param listener The listener to be unregistered.
     */
    void removeTickListener(TickListener listener);

    /**
     * Sets the resolution of the ClockTicker.
     *
     * @param amount The amount of time units for the resolution.
     * @param unit The unit of time for the resolution.
     */
    void setResolution(int amount, ChronoUnit unit);

    /**
     * Sets the start time of the ClockTicker.
     *
     * @param startTime The start time for the ClockTicker.
     */
    void setStartTime(LocalDateTime startTime);

    /**
     * Sets the end time of the ClockTicker.
     *
     * @param endTime The end time for the ClockTicker.
     */
    void setEndTime(LocalDateTime endTime);

    /**
     * Pauses the ClockTicker at specified times.
     *
     * @param time The times at which the ClockTicker should be paused.
     */
    void pauseTickerAt(LocalDateTime... time);

    /**
     * Resumes the ClockTicker after it has been paused.
     */
    void resumeTicker();

    void start();
}