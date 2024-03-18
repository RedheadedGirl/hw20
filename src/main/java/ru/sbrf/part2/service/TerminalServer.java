package ru.sbrf.part2.service;

import org.springframework.stereotype.Service;
import ru.sbrf.part2.exceptions.NotEnoughMoneyException;

@Service
public class TerminalServer implements Terminal {

    private Integer balance = 500; // таков изначальный баланс

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void addMoney(Integer addSum) {
        this.balance = this.balance + addSum;
    }

    @Override
    public void takeMoney(Integer subtractSum) throws NotEnoughMoneyException {
        if (this.balance - subtractSum < 0) {
            throw new NotEnoughMoneyException();
        }
        this.balance = this.balance - subtractSum;
    }
}
