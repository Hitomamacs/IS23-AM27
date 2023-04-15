package org.example;

import java.util.ArrayList;
import java.util.List;

public class PointStackView {

    List<Integer> pointList;

    public PointStackView(){
        pointList = new ArrayList<>();
    }
    public List<Integer> getPointList(){
        return pointList;
    }

    public void updatePointStackView(int newValue, int position){
        pointList.set(position, newValue);
    }
}
