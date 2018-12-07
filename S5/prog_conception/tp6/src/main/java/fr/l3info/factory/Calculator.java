package fr.l3info.factory;

import java.util.Stack;

public class Calculator {

    private Stack<Formula> stack;

    private AbstractFormulaFactory factory;

    public Calculator (AbstractFormulaFactory factory) {
        stack = new Stack<Formula>();
        this.factory = factory;
    }

    public Formula parseAllArgs(String[] args) throws Exception {
        for (String arg : args)
            parseArg(arg);
        if (stack.size() != 1)
            throw new Exception("Syntax error");
        return stack.pop();
    }

    private void parseArg (String arg) throws Exception {
        switch (arg){
            case "+" : parseSum(); break;
            case "*" : parseProduct(); break;
            default:parseConstant(arg); break;
        }
    }

    private void parseProduct() throws Exception {
        if (stack.size() < 2)
            throw new Exception("* requires 2 args");
        Formula right = stack.pop();
        Formula left = stack.pop();
        Formula product = factory.createProduct(left, right);
        stack.push(product);
    }

    private void parseConstant (String arg) throws Exception {
        try {
            double value = Double.parseDouble(arg);
            Formula constant = factory.createConstant(value);
            stack.push(constant);
        } catch (NumberFormatException e) {
            throw new Exception("unknown token");
        }
    }

    private void parseSum() throws Exception {
        if (stack.size() < 2)
            throw new Exception("+ requires 2 args");
        Formula right = stack.pop();
        Formula left = stack.pop();
        Formula sum = factory.createSum(left, right);
        stack.push(sum);
    }


}
