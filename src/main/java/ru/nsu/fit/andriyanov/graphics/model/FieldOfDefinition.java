package main.java.ru.nsu.fit.andriyanov.graphics.model;

public class FieldOfDefinition {

    private int a,b,c,d;

    public FieldOfDefinition(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int GetWidth(){
        return a-b;
    }

    public int GetHeight(){
        return c-d;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }
}
