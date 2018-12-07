package fr.l3info.factory.templatemethod;

import fr.l3info.factory.AbstractFormulaFactory;
import fr.l3info.factory.Constant;
import fr.l3info.factory.Formula;
import fr.l3info.factory.templatemethod.*;

public class TemplateMethodFormulaFactory implements AbstractFormulaFactory {

    @Override
    public Formula createSum(Formula left, Formula right){
        return new Sum (left, right);
    }

    @Override
    public Formula createProduct(Formula left, Formula right) {
        return new Product(left, right);
    }

    @Override
    public Formula createConstant(double value) {
        return new Constant(value);
    }
}

