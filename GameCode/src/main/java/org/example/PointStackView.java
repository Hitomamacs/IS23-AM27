package org.example;

import java.util.List;

public class PointStackView {

    List<Integer> pointList;

    public List<Integer> getPointList(){
        return pointList;
    }

    public void updatePointStackView(int newValue, int position){
        pointList.set(position, newValue);
    }
}
