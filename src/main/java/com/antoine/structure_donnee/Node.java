package com.antoine.structure_donnee;

public class Node<T> {

    private T item;
    private int weight;
    private boolean used;

    private Node<T> parentNode;

    public Node(T item){
        this.item = item;
        weight = -1;
        used = false;
    }

    public T getItem(){
        return item;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void used(){
        used = true;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isUsed() {
        return used;
    }

    public void setParentNode(Node<T> parentNode) {
        this.parentNode = parentNode;
    }
    public Node<T> getParent() {
        return parentNode;
    }
}
