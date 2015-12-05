package ru.jts_dev.authserver.util;

import java.util.Random;

/**
 * @author Camelion
 * @since 01.12.15
 */
public class Rnd {
    private static final Random random = new Random(System.currentTimeMillis());


    /**
     * @param n - max value
     * @return - 0...n
     */
    public static int get(int n) {
        return (int) Math.floor(random.nextDouble() * n);
    }

    public static int nextInt() {
        return random.nextInt();
    }
}
