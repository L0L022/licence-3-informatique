package fr.l3info.factory.strategy;

import fr.l3info.factory.Formula;

public class VariadicOperator implements Formula {

    Operator operator;

    Formula left,right;

    public VariadicOperator(Formula left, Formula right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String asString() {
        return "("+left.asString()+operator.symbol()+right.asString()+")";
    }

    @Override
    public double asValue() {
        return operator.apply(left.asValue(),right.asValue());
    }
}
