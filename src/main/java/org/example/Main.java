package org.example;

import java.time.Duration;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter=new RateLimiter(2,Duration.ofSeconds(10));
        System.out.println(rateLimiter.allow("333",Instant.now()));
        System.out.println(rateLimiter.allow("333",Instant.now().plusSeconds(10)));
    }
}