package com.antoine.structure_donnee;

public class Heuristic<T> {

    private T value;

    public Heuristic() {

    }

    public Heuristic(T value) {
        this.value = value;
    }


    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
