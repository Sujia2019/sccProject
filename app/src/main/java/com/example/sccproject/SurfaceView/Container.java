package com.example.sccproject.SurfaceView;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container {
    //用一个集合来保存组合图形
    private List<Container> childrenList;
    //设置平移的坐标
    private float x=0, y = 0;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Container() {
        childrenList = new ArrayList<Container>();

    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getX(),getY());
        //遍历集合中的所有成员，并将其绘制
        childrenListView(canvas);
        for (Container c : childrenList) {
            c.draw(canvas);
        }
        canvas.restore();
    }

    public void childrenListView(Canvas canvas) {

    }

    public void addChild(Container childern) {
        childrenList.add(childern);
    }
    public void addChild(Container[] childerns) {
        childrenList.addAll(Arrays.asList(childerns));
    }

    public void removeChild(Container children) {
        childrenList.remove(children);
    }
}