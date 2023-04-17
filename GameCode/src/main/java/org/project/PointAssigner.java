package org.project;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Integer.valueOf;

//whoever creates PointAssigner will have to use the constructor then call the initialize function
//passing number of players and number of common goals
public class PointAssigner {
    @Expose

    private List<Stack<Integer>> stackList;

    public PointAssigner(){
        stackList = new ArrayList<>();
    }
    public List<Stack<Integer>> getStackList(){
        return stackList;
    }
    public void initialize(int numPlayers, int numCGoals) throws IllegalArgumentException{

        Stack<Integer> stack;

        if(numPlayers < 2 || numPlayers > 4){
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }

        switch(numPlayers) {
            case 4:
                for (int i = 0; i < numCGoals; i++) {
                     stack = new Stack<>();
                     stack.push(2);
                     stack.push(4);
                     stack.push(6);
                     stack.push(8);
                     stackList.add(stack);
                }
                break;
            case 3:
                for (int i = 0; i < numCGoals; i++) {
                    stack = new Stack<>();
                    stack.push(4);
                    stack.push(6);
                    stack.push(8);
                    stackList.add(stack);
                }
                break;
            case 2:
                for (int i = 0; i < numCGoals; i++) {
                   stack = new Stack<>();
                   stack.push(4);
                   stack.push(8);
                   stackList.add(stack);
                }
                break;
        }
        return;
    }
    //assignPoints receives an int which corresponds to the position of the commonGoal in the
    //orchestrator/game selectedCGoals. The stack list will follow the same order.
    //Stack should never be empty because it is initialized based on the number of players and players
    //can't get points for completing a goal more than once.
    public int assignPoints(int position) throws IllegalArgumentException{
        if(position > stackList.size())
            throw new IllegalArgumentException("Passed position is bigger than selectedCGoal size");
        return stackList.get(position).pop();
    }
}
