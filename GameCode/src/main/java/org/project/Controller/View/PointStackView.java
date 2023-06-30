package org.project.Controller.View;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents the point stack view
 */
public class PointStackView {
    List<Integer> pointList;

    /**
     * Constructor
     */
    public PointStackView(){
        pointList = new ArrayList<>();
    }
    public List<Integer> getPointList(){
        return pointList;
    }

    /**
     * The method update the view of the point stack
     * @param newValue of the stack
     * @param position
     */
    public void updatePointStackView(int newValue, int position){
        pointList.set(position, newValue);
    }
}
