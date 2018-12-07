package fr.l3info.factory;

public interface AbstractFormulaFactory {
    public Formula createSum(Formula left, Formula right);
    public Formula createProduct (Formula left, Formula right);
    public Formula createConstant(double value);
}
