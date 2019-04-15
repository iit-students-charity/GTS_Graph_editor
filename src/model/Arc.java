package model;

import java.util.Objects;


public class Arc {
    public static final int WEIGHT = 1;

    private Node begin;
    private Node end;
    private boolean isDirected;


    public Arc(Node begin, Node end) {
        this.begin = begin;
        this.end = end;
        isDirected = true;
    }

    public Node getBegin() {
        return begin;
    }

    public Node getEnd() {
        return end;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    @Override
    public String toString() {
        return isDirected ?
                begin.getName() + " -> " + end.getName() :
                begin.getName() + " - " + end.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arc arc = (Arc) o;
        return  Objects.equals(begin, arc.begin) &&
                Objects.equals(end, arc.end);
    }
}
