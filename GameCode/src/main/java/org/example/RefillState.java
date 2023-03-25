package org.example;

public class RefillState implements GameState{
    private GameOrchestrator gameOrchestrator;

    public RefillState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }


    @Override
    public void changeState() {
        gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));


    }

    @Override
    public void execute() {
        if(gameOrchestrator.getGameBoard().checkBoard()){
            try {
                int needed_tiles = gameOrchestrator.getGameBoard().boardCheckNum();
                gameOrchestrator.getGameBoard().fillBoard(gameOrchestrator.getTileBag().randomPick(needed_tiles));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        changeState();
    }
}
