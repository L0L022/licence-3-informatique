package fr.l3info.factory.templatemethod;


import fr.l3info.factory.Formula;

public class Sum extends VariadicOperator{
    Formula left,right;

    public Sum(Formula left, Formula right) {
        super(left, right);
    }

    @Override
    protected String symbol() {
        return "+";
    }



    @Override
    protected double operation(double value1, double value2) {
        return value1+value2;
    }
}
