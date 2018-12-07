package fr.l3info.factory.templatemethod;


import fr.l3info.factory.Formula;

public abstract class VariadicOperator implements Formula {
    Formula left,right;

    public VariadicOperator(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String asString() {
        return left.asString()+symbol()+right.asString();
    }

    protected abstract String symbol();

    @Override
    public double asValue() {
        return operation(left.asValue(),right.asValue());
    }


    protected abstract double operation(double value1, double value2);

}
