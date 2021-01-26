package com.goreckia.game.utils;

import java.util.Objects;

public class Pair<L, R> {
    private L left;
    private R right;

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) return false;
        Pair pairObj = (Pair) obj;
        return this.left.equals(pairObj.getLeft()) && this.right.equals(pairObj.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + "#" + right.toString();
    }
}