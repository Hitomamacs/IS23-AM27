package org.example;

public class TopUpState implements GameState{
    private GameOrchestrator gameOrchestrator;

    private boolean done = false;
    @Override
    public void changeState() {
        if(done)
            gameOrchestrator.changeState(new VerifyCommonGoalState());
        else
            gameOrchestrator.changeState(new TopUpState());

    }

    @Override
    public void execute() {
        if(!disconnectChange())
            topUp();//TODO change state not in top-up


    }

    public boolean disconnectChange(){
        if (!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState());
            return true;
        }
        return false;


    }

    public void topUp(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();
        int column = gameOrchestrator.getCurrentPlayer().getSelectedColumn();
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().spaceCheck(column, n_picked_tiles)){
            for(int i = 0; i < n_picked_tiles; i++){
               gameOrchestrator.getCurrentPlayer().getPlayerGrid().topUp(column, gameOrchestrator.getCurrentPlayer().selectTile(i));
               done = true;
               changeState();

            }
        }
        else{
            changeState();
        }

    }
}
