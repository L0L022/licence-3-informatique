package fr.l3info.factory;

public class Variable implements Formula{
    String name;
    double value;

    public Variable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public double asValue() {
        return value;
    }

    public void set(double value){
        this.value = value;
    }
}
