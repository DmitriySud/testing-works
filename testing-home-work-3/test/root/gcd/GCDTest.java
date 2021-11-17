package root.gcd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GCDTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        instance = new GCD();
    }

    GCD instance;

    @Test
    void positives() {
        assertEquals(2, instance.gcd(16, 6));
        assertEquals(3, instance.gcd(9, 48));
        assertEquals(7, instance.gcd(14, 105));
    }

    @Test
    void negatives() {
        assertEquals(2,instance.gcd(-16, 6));
        assertEquals(3, instance.gcd(9, -48));
        assertEquals(7, instance.gcd(-14, -105));
    }

    @Test
    void zeroArg() {
        assertEquals(6, instance.gcd(0, 6));
        assertEquals(9, instance.gcd(9, 0));
        assertEquals(0, instance.gcd(0, 0));
    }

    @Test
    void coprime() {
        assertEquals(1, instance.gcd(3571, 2029));
        assertEquals(1, instance.gcd( 3571 * 2, 2029 * 3));
    }

    @Test
    void equals() {
        assertEquals(12345, instance.gcd(12345, 12345));
        assertEquals(2029, instance.gcd( -2029, 2029) );
    }

    @Test
    void divisor() {
        assertEquals(5, instance.gcd(12345, 5));
        assertEquals(17*5, instance.gcd( 2029*17*2*5, 17*5));
    }

    @Test
    void commonCase() {
        assertEquals(11*32, instance.gcd(1024*7*11, 11*32*17));
        assertEquals(17*5, instance.gcd( 2029*17*2*5, 17*5*3));
    }

    @Test
    void cornerCase() {
        assertEquals(Integer.MAX_VALUE, instance.gcd(Integer.MAX_VALUE, Integer.MAX_VALUE));
        // Error. -2^31  при попытке преобразовать к положительному произойдёт переполнение, так как
        // максимальное положительное 2^31-1. Да и результат тогда должен быть 2^31, что не возможно.
        // Возможные выходы : Сделать возвращаемое заничние типа long и заифать случай. Или
        // запретить вычисление именно этого случая. Звучит странно... Придерживаемся 1ого варианта.
        assertEquals((long)Integer.MAX_VALUE + 1L, instance.gcd(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @Test
    void fibonacciCase() {
        assertEquals(1, instance.gcd(102334155,165580141));
        assertEquals(1, instance.gcd( 39088169, 63245986));
    }
}