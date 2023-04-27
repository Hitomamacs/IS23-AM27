package org.project;

import java.util.HashMap;
import java.util.List;

public interface IClient {

    public void PrintMessageChat(String nickname, String message);
    public void UpdateInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView);
    public void UpdatePick(String[][] board,String[] tilesView);
    public void UpdateTopUp(String[][] grid,String[] tiles,String playername);
    public void UpdateScoreBoard (HashMap<String, Integer> score);
    public void UpdatePopUpView (String text);
}
