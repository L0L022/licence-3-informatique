package fr.l3info.factory.strategy;

import fr.l3info.factory.AbstractFormulaFactory;
import fr.l3info.factory.Constant;
import fr.l3info.factory.Formula;
import fr.l3info.factory.strategy.*;

public class StrategyFormulaFactory implements AbstractFormulaFactory {
    @Override
    public Formula createSum(Formula left, Formula right) {
        return new VariadicOperator(left, right, new Addition());
    }

    @Override
    public Formula createProduct(Formula left, Formula right) {
        return new VariadicOperator(left, right, new Multiplication());
    }

    @Override
    public Formula createConstant(double value) {
        return new Constant(value);
    }
}
