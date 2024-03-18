package ru.sbrf.part2.service;

import ru.sbrf.part2.exceptions.NotEnoughMoneyException;

public interface Terminal {

    Integer getBalance();

    void addMoney(Integer addSum);

    void takeMoney(Integer subtractSum) throws NotEnoughMoneyException;

}
