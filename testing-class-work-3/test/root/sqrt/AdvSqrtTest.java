package root.sqrt;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AdvSqrtTest {
    AdvSqrt advSqrt;

    @org.junit.jupiter.api.BeforeEach
    void init() {
        advSqrt = new AdvSqrt();
    }

    @org.junit.jupiter.api.Test
    void sqrtNegative() {
        assertTrue(Double.isNaN(advSqrt.sqrt(-5)));
    }

    @org.junit.jupiter.api.Test
    void sqrtNegativeInf() {
        assertTrue(Double.isNaN(advSqrt.sqrt(Double.NEGATIVE_INFINITY)));
    }

    @org.junit.jupiter.api.Test
    void sqrtNegativeZero() {
        double minusZero = Double.longBitsToDouble(1L << 63);
        assertAnswer(minusZero, advSqrt.sqrt(minusZero));
    }

    @org.junit.jupiter.api.Test
    void sqrtPositiveInf() {
        assertAnswer(Double.POSITIVE_INFINITY, advSqrt.sqrt(Double.POSITIVE_INFINITY));
    }

    @org.junit.jupiter.api.Test
    void sqrtNaN() {
        assertAnswer(Double.NaN, advSqrt.sqrt(Double.NaN));
    }

    @org.junit.jupiter.api.Test
    void sqrtPositiveZero() {
        assertAnswer(0d, advSqrt.sqrt(0d));
    }

    @org.junit.jupiter.api.Test
    void sqrtPositiveValue() {
        assertAnswer(Math.sqrt(5d), advSqrt.sqrt((5d)));
    }

    @org.junit.jupiter.api.Test
    void sqrtPositiveWithZeroExponent() {
        double value = Double.longBitsToDouble(0x0000000000000011L);
        assertAnswer(Math.sqrt(value), advSqrt.sqrt(value));
    }

    @org.junit.jupiter.api.Test
    void sqrtPositiveWithNonZeroEvenExponent() {
        double value = Double.longBitsToDouble(0x0060000000011L);
        assertAnswer(Math.sqrt(value), advSqrt.sqrt(value));
    }


    @org.junit.jupiter.api.Test
    void sqrtPositiveWithNoZeroOddExponent() {
        double value = Double.longBitsToDouble(0x0050000008000312L);
        assertAnswer(Math.sqrt(value), advSqrt.sqrt(value));
        value = Double.longBitsToDouble(0x3FF416E9592F380AL);
        assertAnswer(Math.sqrt(value), advSqrt.sqrt(value));
    }

    @org.junit.jupiter.api.Test
    void stress() {
        Random rnd = new Random();
        for (int i = 0; i < 1000; ++i){
            double value = rnd.nextDouble() / rnd.nextDouble();
//            if (Math.sqrt(value) != advSqrt.sqrt(value)){
//                System.out.printf("0x%016X%n", Double.doubleToLongBits(advSqrt.sqrt(value) ));
//                System.out.printf("0x%016X%n", Double.doubleToLongBits(Math.sqrt(value) ));
//                System.out.printf("0x%016X%n", Double.doubleToLongBits(value));
//            }
            assertAnswer(Math.sqrt(value), advSqrt.sqrt(value));
        }
    }

    void assertAnswer(double a, double b) {
        long al = Double.doubleToLongBits(a);
        long bl = Double.doubleToLongBits(b);
        assertTrue(Math.abs(al-bl) <= 1);
    }
}