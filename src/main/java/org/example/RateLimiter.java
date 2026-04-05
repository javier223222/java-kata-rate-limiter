package org.example;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
public class RateLimiter {
    private int maxRequests;
    private Duration window;
    private Map<String, Deque<Instant>> recentHistory;
    public RateLimiter(int maxRequests, Duration window) {
        if(maxRequests <= 0 || window.isZero() || window.isNegative()){
            throw new IllegalArgumentException("maxRequests and  Window must be a positive integer");
        }
        this.recentHistory=new HashMap<>();
        this.maxRequests = maxRequests;
        this.window = window;
    }
    public boolean allow(String key, Instant now){
        if(key==null || key.isBlank()){
            throw new IllegalArgumentException("Key must be non-null and non-blank");
        }
        if(now==null){
            throw new IllegalArgumentException("now must be non_null");
        }
        Deque<Instant> q=recentHistory.computeIfAbsent(key,k->new ArrayDeque<>());
        Instant cutOff=now.minus(window);
        while (!q.isEmpty() && !q.peekFirst().isAfter(cutOff)){
            q.removeFirst();
        }
        if(q.size()>=maxRequests){
            return false;
        }
        q.addLast(now);
        return true;
    }

}
