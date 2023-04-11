package org.Project;

public class RefillState implements GameState{

    private final int stateID = 5;
    private transient GameOrchestrator gameOrchestrator;

    public RefillState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }


    @Override
    public void changeState() {
        gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(10);


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
