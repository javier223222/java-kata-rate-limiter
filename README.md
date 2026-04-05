# Rate Limiter Kata (Java 17)

This kata implements a **per-key sliding window rate limiter**.

It answers a simple question:

> Given a `key` (userId / apiKey / IP) and the current time, should we allow this request?

---

## Core Idea

For each `key`, the rate limiter stores timestamps of **recent allowed requests** and enforces:

- **maxRequests** requests per **time window**
- Requests are evaluated using a **sliding window**: only the requests that happened within the last `window` duration count.

---

## Implementation Details (based on this code)

### Class
`RateLimiter(int maxRequests, Duration window)`

- `maxRequests` must be `> 0`
- `window` must be positive (`!isZero()` and `!isNegative()`)
- Request history is stored as:

`Map<String, Deque<Instant>> recentHistory`

### Policy
The `allow(key, now)` method:

1. Validates inputs:
    - `key` must be non-null and non-blank
    - `now` must be non-null
2. Computes the cutoff time:
    - `cutOff = now.minus(window)`
3. Cleans old timestamps for this key:
    - Removes timestamps `<= cutOff`
    - This means the effective window is **(now - window, now]**
4. If the remaining count is `>= maxRequests`, the request is **blocked** (`false`)
5. Otherwise, the request is **allowed** (`true`) and `now` is added to the history

✅ Requests with the **same timestamp** count as separate requests.

---

## Function Signature

`boolean allow(String key, Instant now)`

---

## Examples

With `maxRequests = 3` and `window = 10s`, for key `"u1"`:

- `t=0` → true
- `t=1` → true
- `t=2` → true
- `t=3` → false (4th request within the window)

---

## Test Coverage (included)

The test suite validates:

- **Basic single-key limiting**
- **Independent limits per key**
- **Sliding window cleanup (old timestamps expire)**
- **Duplicate timestamp behavior**
- **Boundary behavior** (cutoff edge)

