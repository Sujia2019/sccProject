package com.example.sccproject;

/**
 * Created by alienware on 2020/3/18.
 */

public class Lattice {
    public static final int INTREE = 1;
    public static final int NOTINTREE = 0;
    private int x = -1;
    private int y = -1;
    private int flag = NOTINTREE;
    private Lattice father = null;
    public Lattice(int xx, int yy) {
        x = xx;
        y = yy;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getFlag() {
        return flag;
    }
    public Lattice getFather() {
        return father;
    }
    public void setFather(Lattice f) {
        father = f;
    }
    public void setFlag(int f) {
        flag = f;
    }
    public int gerFlag(){
        return flag;
    }
}