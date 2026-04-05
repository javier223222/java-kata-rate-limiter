import org.example.RateLimiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
public class RateLimiterTest {
    @Test
    @DisplayName("Basic test with One key only")
    void test_basic_oneKey(){
        RateLimiter rateLimiter=new RateLimiter(3, Duration.ofSeconds(10));
        Instant instant=Instant.parse("2026-01-01T00:00:00Z");
        assertTrue(rateLimiter.allow("u1", instant.plusSeconds(0)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(1)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(2)));
        assertFalse(rateLimiter.allow("u1",instant.plusSeconds(3)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(11)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(12)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(13)));


    }
    @Test
    @DisplayName("Basic test with independent keys")
    void test_basic_independentKeys(){
    RateLimiter rateLimiter=new RateLimiter(2,Duration.ofSeconds(5));
    Instant instant=Instant.parse("2026-01-01T00:00:00Z");
    assertTrue(rateLimiter.allow("u1", instant.plusSeconds(0)));
    assertTrue(rateLimiter.allow("u1",instant.plusSeconds(1)));
    assertFalse(rateLimiter.allow("u1",instant.plusSeconds(2)));
    assertTrue(rateLimiter.allow("u2",instant.plusSeconds(2)));
    assertTrue(rateLimiter.allow("u2",instant.plusSeconds(3)));
    assertFalse(rateLimiter.allow("u2",instant.plusSeconds(4)));
    }
    @Test
    @DisplayName("Test to check the sliding window(cleaning)")
    void test_slidingWindow(){
        RateLimiter rateLimiter=new RateLimiter(3, Duration.ofSeconds(4));
        Instant instant=Instant.parse("2026-01-01T00:00:00Z");
        assertTrue(rateLimiter.allow("u1", instant.plusSeconds(0)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(1)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(2)));
        assertFalse(rateLimiter.allow("u1",instant.plusSeconds(3)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(5)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(6)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(6)));
    }
    @Test
    @DisplayName("Test duplicate time case")
    void test_duplicates_case(){
      RateLimiter rateLimiter=new RateLimiter(2,Duration.ofSeconds(10));
      Instant instant=Instant.parse("2026-01-01T00:00:00Z");
      assertTrue(rateLimiter.allow("u2", instant.plusSeconds(100)));
      assertTrue(rateLimiter.allow("u2",instant.plusSeconds(100)));
      assertFalse(rateLimiter.allow("u2",instant.plusSeconds(100)));
    }
    @Test
    @DisplayName("Test duplicate time case")
    void test_boundary_case(){
        RateLimiter rateLimiter=new RateLimiter(2,Duration.ofSeconds(10));
        Instant instant=Instant.parse("2026-01-01T00:00:00Z");
        assertTrue(rateLimiter.allow("u1", instant.plusSeconds(0)));
        assertTrue(rateLimiter.allow("u1",instant.plusSeconds(10)));
    }
}
