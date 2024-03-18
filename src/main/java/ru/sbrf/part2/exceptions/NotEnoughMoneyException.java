package ru.sbrf.part2.exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("На счету недостаточно средств");
    }
}