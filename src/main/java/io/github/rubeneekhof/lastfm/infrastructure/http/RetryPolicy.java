package io.github.rubeneekhof.lastfm.infrastructure.http;

/**
 * Configuration for retry behavior with exponential backoff.
 */
public record RetryPolicy(int maxAttempts, long initialDelayMs, double backoffMultiplier) {
    
    public static RetryPolicy lastFmDefaults() {
        return new RetryPolicy(3, 500, 2.0);
    }
}
