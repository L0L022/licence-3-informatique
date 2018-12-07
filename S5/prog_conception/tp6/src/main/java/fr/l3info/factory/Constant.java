package fr.l3info.factory;


public class Constant implements Formula {
    double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value+"";
    }

    @Override
    public double asValue() {
        return value;
    }
}
