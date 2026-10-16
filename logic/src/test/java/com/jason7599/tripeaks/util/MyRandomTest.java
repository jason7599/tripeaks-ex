package com.jason7599.tripeaks.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MyRandomTest {

    @Test
    void goldenSequence() {
        MyRandom rng = new MyRandom(12345);

        assertEquals(0xC6E5747A, rng.nextInt());
        assertEquals(0x652A09AF, rng.nextInt());
        assertEquals(0xA7E08FA0, rng.nextInt());
        assertEquals(0x748E41EA, rng.nextInt());
        assertEquals(0x2AD8A9D3, rng.nextInt());
    }

    @Test
    void zeroSeedStillProducesRandomValues() {
        MyRandom rng = new MyRandom(0);

        assertNotEquals(0, rng.nextInt());
    }
}
