package com.example.sccproject.util;

import android.widget.ImageView;

public class FlashRunnable implements Runnable{
    private int[] res;
    private int index;
    private ImageView imageView;
    private int length ;
    private static final int ONCE = 1;
    private static final int LOOP = 2;

    @Override
    public void run() {
        imageView.setImageResource(res[index]);
        changeIndex();
    }
    private void changeIndex(){
        index = (index+1)%length;
    }
    public FlashRunnable(int[] res,int index,ImageView view){
        this.res = res;
        this.index = index;
        imageView = view;
        length = res.length;
    }

    public void setRes(int[] res) {
        this.res = res;
    }

    public int getIndex() {
        return index;
    }

    public int[] getRes() {
        return res;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
