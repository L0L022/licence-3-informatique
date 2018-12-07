package fr.l3info.factory.templatemethod;


import fr.l3info.factory.Formula;

public class Product extends VariadicOperator {
    Formula left,right;

    public Product(Formula left, Formula right) {
        super(left, right);
    }

    @Override
    protected String symbol() {
        return "*";
    }

    @Override
    protected double operation(double value1, double value2) {
        return value1*value2;
    }


}
