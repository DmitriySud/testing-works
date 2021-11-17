/**
 * Copyright (c) 2009 ISP RAS.
 * 109004, A. Solzhenitsina, 25, Moscow, Russia.
 * All rights reserved.
 * <p>
 * $Id$
 * Created on 08.10.2009
 */

package root.account;

/**
 * @author Victor Kuliamin
 */
public class Account {
    boolean blocked = false;
    int bound = 1000000;
    int balance = 0;
    int maxCredit = -1000;

    public boolean deposit(int sum) {
        if (blocked)
            return false;
        // Ошибка 4. Нет проверки на то, что по итогу баланс не превышает BOUND
        else if (balance + sum > bound || sum < 0 || sum > bound)
            return false;
        else {
            balance += sum;
            return true;
        }
    }

    public boolean withdraw(int sum) {
        if (blocked)
            return false;
        else if (sum < 0 || sum > bound)
            return false;
        // Ошибка 3. Из п.2 "В незаблокированном состоянии значение текущей суммы на счете не должно быть
        // МЕНЬШЕ отрицательного кредитного максимума." ситуация, когда они равны - допустима.
        else if (balance < maxCredit + sum)
            return false;
        else {
            balance -= sum;
            return true;
        }
    }

    public int getBalance() {
        return balance;
    }

    public int getMaxCredit() {
        return -maxCredit;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        blocked = true;
    }

    public boolean unblock() {
        if (balance < maxCredit)
            return false;
        else
            blocked = false;

        return true;
    }

    // Ошибка 1. Не сходится сигнатура. mc -> c
    public boolean setMaxCredit(int c) {
        // Ошибка 2. Не было проверки на то, что счёт заблокирован п.3
        if (!isBlocked() || c < -bound || c > bound) {
            return false;
        } else
            maxCredit = -c;

        return true;
    }
}