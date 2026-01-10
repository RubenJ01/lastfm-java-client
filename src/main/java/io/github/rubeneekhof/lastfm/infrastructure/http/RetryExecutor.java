package io.github.rubeneekhof.lastfm.infrastructure.http;

import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailure;
import io.github.rubeneekhof.lastfm.domain.model.error.RateLimited;
import io.github.rubeneekhof.lastfm.domain.model.error.ServiceUnavailable;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;

import java.util.function.Supplier;

/**
 * Executes actions with retry logic for transient failures.
 * Only retries RateLimited and ServiceUnavailable failures with exponential backoff.
 */
public class RetryExecutor {

    public static <T> T execute(RetryPolicy policy, Supplier<T> action) {
        LastFmException lastException = null;
        
        for (int attempt = 1; attempt <= policy.maxAttempts(); attempt++) {
            try {
                return action.get();
            } catch (LastFmException e) {
                lastException = e;
                
                LastFmFailure failure = LastFmErrorMapper.map(e.code(), e.getMessage());
                
                // Only retry transient failures
                if (!(failure instanceof RateLimited) && !(failure instanceof ServiceUnavailable)) {
                    throw e; // Don't retry for non-transient failures
                }
                
                // On last attempt, throw immediately instead of retrying
                if (attempt >= policy.maxAttempts()) {
                    throw e;
                }
                
                // Calculate delay with exponential backoff: delay = initial * (multiplier ^ (attempt - 1))
                long delayMs = (long) (policy.initialDelayMs() * Math.pow(policy.backoffMultiplier(), attempt - 1));
                
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new LastFmException(0, "Retry interrupted", ie);
                }
            }
        }
        
        // This should never be reached due to throw on last attempt, but handle it for safety
        if (lastException != null) {
            throw lastException;
        }
        throw new LastFmException(0, "All retry attempts exhausted without completing the request");
    }
}
