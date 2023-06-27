package org.project.Model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Integer.valueOf;

public class PointAssigner {
    @Expose

    private List<Stack<Integer>> stackList;

    public PointAssigner(){
        stackList = new ArrayList<>();
    }
    public List<Stack<Integer>> getStackList(){
        return stackList;
    }

    /**
     * The method is used to initialize a stack list (stackList) based on the number of players (numPlayers)
     * and the number of common goals (numCGoals) specified.
     * @param numPlayers that are playing
     * @param numCGoals that the players have to complete
     * @throws IllegalArgumentException if the number of players is incorrect
     */
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
    }

    /**
     * @param position of the commonGoal in the selectedCGoals
     * @return points that the player has obtained
     * @throws IllegalArgumentException if the passed position is bigger than selectedCGoal size
     */
    public int assignPoints(int position) throws IllegalArgumentException{
        if(position > stackList.size())
            throw new IllegalArgumentException("Passed position is bigger than selectedCGoal size");
        return stackList.get(position).pop();
    }
}
