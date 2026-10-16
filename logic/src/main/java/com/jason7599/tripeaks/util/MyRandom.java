package com.jason7599.tripeaks.util;

import java.util.List;

/**
 * Tiny deterministic rng (xorshift32 algorithm)
 *
 * The same seed must produce the exact same numbers in the browser(TeaVM) and the server.
 * TeaVM doesn't have a traditional JDK so it has its own version of Random and Collections
 * This is why we need our own deterministic prng
 */
public final class MyRandom {
    private int state;

    public MyRandom(int seed) {
        state = seed == 0 ? 0x6D2B79F5 : seed;
    }

    public int nextInt() {
        int x = state;
        x ^= x << 13;
        x ^= x >>> 17;
        x ^= x << 5;
        state = x;
        return x;
    }

    public int nextInt(int bound) {
        if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
        return (int) (Integer.toUnsignedLong(nextInt()) % bound);
    }

    /** fisher-yates shuffle */
    public static <T> void shuffle(List<T> items, MyRandom rng) {
        for (int i = items.size() - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            T tmp = items.get(i);
            items.set(i, items.get(j));
            items.set(j, tmp);
        }
    }
}
