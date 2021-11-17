package root.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account instance;
    static final int START_MAX_CREDIT = 100;
    // Можем просто задать руками, потому что явно прописано в условии в п.4
    static final int BOUND = 1000000;

    @BeforeEach
    void setup() {
        instance = new Account();
        setMaxCredit(START_MAX_CREDIT);
        int startBalance = instance.getBalance();
        // Set start balance to zero, to make our tests simpler
        assertTrue(startBalance >= 0 ? instance.withdraw(startBalance) : instance.deposit(startBalance));
    }

    void setMaxCredit(int maxCredit) {
        instance.block();
        assertTrue(instance.setMaxCredit(maxCredit));
        assertTrue(instance.unblock());
        assertEquals(maxCredit, instance.getMaxCredit());
    }

    @Test
    void cornerCase() {
        setMaxCredit(BOUND);
        assertTrue(instance.withdraw(BOUND));
        assertEquals(-BOUND, instance.getBalance());

        assertTrue(instance.deposit(BOUND));
        assertTrue(instance.deposit(BOUND));
        assertEquals(BOUND, instance.getBalance());
    }

    @Test
    void withdraw() {
        assertFalse(instance.withdraw(START_MAX_CREDIT + 1));
        assertEquals(0, instance.getBalance());

        assertTrue(instance.withdraw(START_MAX_CREDIT));
        assertEquals(-START_MAX_CREDIT, instance.getBalance());

        assertFalse(instance.withdraw(-1));
        assertEquals(-START_MAX_CREDIT, instance.getBalance());

        assertFalse(instance.withdraw(BOUND + 1));
        assertEquals(-START_MAX_CREDIT, instance.getBalance());

        instance.block();
        assertFalse(instance.withdraw(10));
    }

    @Test
    void getBalance() {
        assertEquals(0, instance.getBalance());
        assertTrue(instance.deposit(300));
        assertEquals(300, instance.getBalance());
    }

    @Test
    void getMaxCredit() {
        assertEquals(START_MAX_CREDIT, instance.getMaxCredit());
        setMaxCredit(300);
        assertEquals(300, instance.getMaxCredit());
        assertTrue(instance.deposit(300));
        setMaxCredit(-300);
        assertEquals(-300, instance.getMaxCredit());
    }

    @Test
    void isBlocked() {
        assertFalse(instance.isBlocked());
        instance.block();
        assertTrue(instance.isBlocked());
        assertTrue(instance.unblock());
        assertFalse(instance.isBlocked());

    }

    @Test
    void deposit() {
        int justValue = 300;
        assertTrue(instance.deposit(0));
        assertFalse(instance.deposit(-justValue));
        assertEquals(0, instance.getBalance());

        assertTrue(instance.deposit(justValue));
        assertEquals(justValue, instance.getBalance());

        assertFalse(instance.deposit(BOUND+1));
        assertEquals(justValue, instance.getBalance());

        assertFalse(instance.deposit(BOUND));
        assertEquals(justValue, instance.getBalance());

        instance.block();
        assertFalse(instance.deposit(justValue));
    }

    @Test
    void unblock() {
        instance.block();
        assertTrue(instance.setMaxCredit(-300));
        assertFalse(instance.unblock());
        assertTrue(instance.isBlocked());
        assertTrue(instance.setMaxCredit(300));
        assertTrue(instance.unblock());
    }

    @Test
    void setMaxCredit() {
        int newCreditValue = 300;
        assertFalse(instance.setMaxCredit(newCreditValue));
        assertEquals(START_MAX_CREDIT, instance.getMaxCredit());

        instance.block();
        assertTrue(instance.setMaxCredit(newCreditValue));
        assertEquals(newCreditValue, instance.getMaxCredit());

        assertFalse(instance.setMaxCredit(BOUND+1));
        assertEquals(newCreditValue, instance.getMaxCredit());
        assertFalse(instance.setMaxCredit(-BOUND-1));
        assertEquals(newCreditValue, instance.getMaxCredit());
        assertTrue(instance.setMaxCredit(-BOUND));
        assertTrue(instance.setMaxCredit(BOUND));
    }
}