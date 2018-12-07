package fr.l3info.factory.strategy;

public class Multiplication implements Operator{
    @Override
    public String symbol() {
        return "*";
    }

    @Override
    public double apply(double value1, double value2) {
        return value1*value2;
    }
}
