package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.Coordinates;

import java.util.List;

public class VerifyBoardableState implements GameState {
    private final int stateID = 8;
    private transient GameOrchestrator gameOrchestrator;

    public VerifyBoardableState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public boolean verifyBoardable(){
        List<Coordinates> pickedCoordinates = gameOrchestrator.getPickedCoordinates();
        if(!checkAligned(pickedCoordinates)) {
            return false;
        }
        for(Coordinates c : pickedCoordinates){
            if(!gameOrchestrator.getGameBoard().verifyPickable(c)){
                return false;
            }
        }
        return true;
    }
    public boolean checkAligned(List<Coordinates> coordinates){
        boolean same_X = true;
        boolean same_Y = true;
        int x;
        int y;
        int max_X = coordinates.get(0).getX();
        int min_X = coordinates.get(0).getX();
        int max_Y = coordinates.get(0).getY();
        int min_Y = coordinates.get(0).getY();
        for(int i = 0; i < coordinates.size() - 1; i++){
            x = coordinates.get(i).getX();
            y = coordinates.get(i).getY();
            if(!(x == coordinates.get(i + 1).getX()))
                same_X = false;
            if(!(y == coordinates.get(i + 1).getY()))
                same_Y = false;
        }
        for(int i = 0; i < coordinates.size(); i++){
            x = coordinates.get(i).getX();
            y = coordinates.get(i).getY();
            if(x < min_X)
                min_X = x;
            if(x > max_X)
                max_X = x;
            if(y < min_Y)
                min_Y = y;
            if(y > max_Y)
                max_Y = y;
        }
        switch(coordinates.size()){
            case(2):
                if(max_Y - min_Y > 1 || max_X - min_X > 1)
                    return false;
                break;
            case(3):
                if(max_Y - min_Y > 2 || max_X - min_X > 2)
                    return false;
                break;
            default:
                break;
        }
        return(same_X || same_Y);
    }
    @Override
    public void changeState() throws InvalidMoveException {

        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        System.out.println("Boardable test");
        if(verifyBoardable()) {
            System.out.println("Boardable passed");
            gameOrchestrator.changeState(new PickState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(4);
            gameOrchestrator.executeState();
        }

        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(10);
            throw new InvalidMoveException("Tiles must be adjacent, aligned and have at least on free side", 2);
        }

    }

    @Override
    public void execute() throws InvalidMoveException {
        changeState();
    }

}
