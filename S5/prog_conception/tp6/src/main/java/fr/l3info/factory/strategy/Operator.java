package fr.l3info.factory.strategy;

public interface Operator {
    String symbol();

    double apply(double value1, double value2);
}
