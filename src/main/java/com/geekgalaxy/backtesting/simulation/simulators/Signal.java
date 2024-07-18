package com.geekgalaxy.backtesting.simulation.simulators;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Represents a trading signal.
 * <p>
 * A Signal is a suggestion for a potential trade in a specific financial instrument.
 * It includes information about the type of the signal (BUY, SELL, HOLD, NEUTRAL),
 * the confidence level of the signal, the instrument for which the signal is generated,
 * the time when the signal was generated, the source of the signal, the closing price of the instrument,
 * the strength of the signal, the duration for which the signal is valid, the quality of the signal,
 * and the timestamp of the signal.
 * </p>
 * <p>
 * The confidence level is a value between 0 and 1, where 1 represents the highest confidence.
 * The strength of the signal is a value between 0 and 100.
 * The duration represents the number of days the signal is valid.
 * </p>
 * <p>
 * The source of the signal can be one of the following: ALGO, FUNDAMENTAL, SENTIMENT, PATTERN, TECHNICAL.
 * The quality of the signal can be one of the following: HIGH, MEDIUM, LOW.
 * </p>
 */
@Data
public class Signal {
    /**
     * The type of the signal.
     */
    private SignalType signal;
    /**
     * The confidence level of the signal, from 0 to 1 where 1 is the highest confidence.
     */
    private double confidence;
    /**
     * The ID of the instrument for which the signal is generated.
     */
    private UUID instrumentId;
    /**
     * The time when the signal was generated.
     */
    private LocalDateTime time;
    /**
     * The source of the signal.
     */
    private Source source;
    /**
     * The closing price of the instrument in SEK.
     */
    private double price;
    /**
     * The strength of the signal, from 0 to 100.
     */
    private int strength;
    /**
     * The duration for which the signal is valid, represented as a number of days.
     */
    private Duration duration;
    /**
     * The quality of the signal.
     */
    private SignalQuality quality; // signal quality (HIGH, MEDIUM, LOW)

    /**
     * The possible sources of a signal.
     */
    public enum Source {
        ALGO, FUNDAMENTAL, SENTIMENT, PATTERN, TECHNICAL
    }
    /**
     * The possible types of a signal.
     */
    public enum SignalType {
        BUY, SELL, HOLD, NEUTRAL
    }
    /**
     * The possible qualities of a signal.
     */
    public enum SignalQuality {
        HIGH, MEDIUM, LOW
    }
}
