package org.project.ClientPack;

import org.project.Controller.Messages.ChatMessage;

import java.util.*;

/**
 * Class representing the client side view
 */
public class
ClientView extends ObservableObject {

    private String[][] board;
    private List<Integer> pointStack;
    private HashMap<String, String[][] > gridsview;
    private HashMap<String,String[]> tilesview;
    private HashMap<String, List<ChatMessage>> privateChats;
    private String popUpErrorMessage;
    private int popUpIdentifier; //TODO change and put all in a popup message instead of having separate message and identifier
    private HashMap<String, Integer> scoreBoard;
    private HashMap<String, Integer> personalGoalViews;
    private List<Integer> commonGoalView;

    private String current_chat;

    private List<ChatMessage> chat = new ArrayList<>();
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BROWN = "\u001B[0;33m";
    private int num_tiles;

    private static final Map<String, String> colorMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put("R", "\u001B[31m");
        colorMap.put("G", "\u001B[32m");
        colorMap.put("B", "\u001B[34m");
        colorMap.put("P", "\u001B[35m");
        colorMap.put("A", "\u001B[36m");
        colorMap.put("W", "\u001B[37m");
        colorMap.put("Y", "\u001B[33m");
    }

    private static final Map<String, String> backgroundColorMap;
    static {
        backgroundColorMap = new HashMap<>();
        backgroundColorMap.put("I", "\u001b[40m");  //black dove non puoi prendere le tiles
        backgroundColorMap.put("G", "\u001b[42m");  //green
        backgroundColorMap.put("B", "\u001b[44m");  //blue
        backgroundColorMap.put("P", "\u001b[45m");  //pink
        backgroundColorMap.put("A", "\u001b[46m");  //azure
        backgroundColorMap.put("W", "\u001b[47m");  //white
        backgroundColorMap.put("Y", "\u001b[43m");  //yellow
        backgroundColorMap.put("N", "\u001b[40m");  //black quando hai già pescato la tile e il posto diventa vuoto
    }

    /**
     * Constructor
     */
    public ClientView() {
       pointStack=new ArrayList<>();
       gridsview= new HashMap<>();
       tilesview= new HashMap<>();
       scoreBoard = new HashMap<>();
        privateChats = new HashMap<>();
        current_chat = "broadcast";
    }

    public int getNum_tiles() {
        return num_tiles;
    }

    public void setNum_tiles(int num_tiles) {
        this.num_tiles = num_tiles;
    }

    public List<ChatMessage> getChat(){
        return chat;
    }

    public String getCurrentChat(){
        return current_chat;
    }
    public void setCurrent_Chat(String chat){
        current_chat = chat;
    }

    public HashMap<String, List<ChatMessage>> getPrivateChats() {
        return privateChats;
    }

    public void updateGridsView(String playerName, String[][] grid) {
        gridsview.put(playerName, grid);
    }

    public void updateTilesView(String playerName, String[] tiles) {
        tilesview.put(playerName, tiles);
    }

    public String[][] getBoard() {
        return board;
    }

    public void setPopUpIdentifier(int identifier){
        this.popUpIdentifier =identifier;
    }

    public HashMap<String, String[][]> getGridsview() {
        return gridsview;
    }

    public List<Integer> getPointStack() {
        return pointStack;
    }

    public HashMap<String, String[]> getTilesview() {
        return tilesview;
    }

    public String getPopUpErrorMessage() {
        return popUpErrorMessage;
    }

    public int getPopUpIdentifier(){
        return popUpIdentifier;
    }

    public HashMap<String, Integer> getScoreBoard() {
        return scoreBoard;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public void setPointStack(List<Integer> pointStack) {
        this.pointStack = pointStack;
    }

    public void setGridsview(HashMap<String, String[][]> gridsview) {
        this.gridsview = gridsview;
    }

    public void setTilesview(HashMap<String, String[]> tilesview) {
        this.tilesview = tilesview;
    }

    public void setErrorMessage(String errorMessage) {
        this.popUpErrorMessage = errorMessage;
    }

    public void setScoreBoard(HashMap<String, Integer> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void printGrid(String username) {
        String[][] grid = this.getGridsview().get(username);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == null) {
                    System.out.print(ANSI_BROWN + "   " + ANSI_RESET);
                } else {
                    String backgroundColor = backgroundColorMap.get(grid[i][j]);
                    if (backgroundColor == null) backgroundColor = ANSI_BROWN; // Default to brown if color not found, usless check
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println(" 0  1  2  3  4");
    }

    public void printBoard() {
        System.out.println("\nPrinting updated board:");
        System.out.println("   0  1  2  3  4  5  6  7  8");
        for(int i = 0; i < board.length; i++){
            System.out.print(i + " ");
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != null){
                    //System.out.println(j);
                    String backgroundColor = backgroundColorMap.get(board[i][j]);
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public void printTiles(String playerName) {
        int count = 0;
        System.out.println("\nPrinting " + playerName + " updated tiles");
        String[] tiles = tilesview.get(playerName);
        for (String tile : tiles) {
            String backgroundColor = backgroundColorMap.get(tile);
            System.out.println(count + " " + backgroundColor + "   " + ANSI_RESET);
            count++;
        }
    }

    public void printScore(){
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scoreBoard.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map<String, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : sortedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * The method prints the personal goal card of the player
     * @param username player's name
     */
    public void printPersonalGoalCard(String username){
        int idCard = personalGoalViews.get(username).intValue();
        switch (idCard){
            case 0:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 0 && j == 0){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);
                        }
                        else if(i == 0 && j == 2){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);
                        }
                        else if(i == 1 && j == 4){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);
                        }
                        else if(i == 2 && j == 3){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);
                        }
                        else if(i == 3 && j == 1){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);
                        }
                        else if(i == 5 && j == 2){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 1:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 2 && j == 0){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 1 && j == 1){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 5 && j == 4){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 3 && j == 4){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 2 && j == 2){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 4 && j == 3){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 2:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 3 && j == 1){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 2 && j == 2){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 1 && j == 0){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 5 && j == 0){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 1 && j == 3){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 3 && j == 4){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 3:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 4 && j == 2){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 3 && j == 3){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 2 && j == 2){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 4 && j == 1){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 0 && j == 4){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 2 && j == 0){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 4:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 5 && j == 3){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 4 && j == 4){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 3 && j == 1){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 3 && j == 2){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 5 && j == 0){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 1 && j == 1){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 5:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 0 && j == 4){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 5 && j == 0){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 4 && j == 3){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 2 && j == 3){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 4 && j == 1){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 0 && j == 2){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 6:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 0 && j == 0){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 2 && j == 1){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 1 && j == 3){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 5 && j == 2){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 4 && j == 4){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 3 && j == 0){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 7:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 1 && j == 1){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 3 && j == 0){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 0 && j == 4){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 4 && j == 3){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 5 && j == 3){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 2 && j == 2){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 8:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 2 && j == 2){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 4 && j == 4){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 5 && j == 0){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 3 && j == 4){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 0 && j == 2){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 4 && j == 1){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 9:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 3 && j == 3){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 5 && j == 3){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 4 && j == 1){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 2 && j == 0){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 1 && j == 1){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 0 && j == 4){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 10:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 4 && j == 4){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 0 && j == 2){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 3 && j == 2){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 1 && j == 1){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 2 && j == 0){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 5 && j == 3){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;

            case 11:
                System.out.println("La tua Personal Goal Card è la seguente: \n");
                System.out.println("   0  1  2  3  4");
                for(int i = 0; i < 6; i++){
                    System.out.print(i + " ");
                    for(int j = 0; j < 5; j++){
                        if(i == 5 && j == 0){
                            System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                        }
                        else if(i == 1 && j == 1){
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        else if(i == 2 && j == 2){
                            System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                        }
                        else if(i == 0 && j == 2){
                            System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                        }
                        else if(i == 4 && j == 4){
                            System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                        }
                        else if(i == 3 && j == 3){
                            System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                        }
                        else {
                            System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("Caselle completate:  1   2   3   4   5   6");
                System.out.println("Punteggio ottenuto:  1   2   4   6   9   12");
                break;
        }

    }

    public void printPersonalGoal(String playerName){
        System.out.println("\nPrinting " + playerName + " personal Goal : "+ personalGoalViews.get(playerName));
    }

    /**
     * The method prints the common goal card of the player
     */
    public void printCommonGoal(){
        System.out.println("\nPrinting Common Goals Card");
        for(Integer id: commonGoalView){
            System.out.println(id + ": ");
            System.out.println();
            switch (id) {
                case 1 -> {
                    //
                    System.out.println("   0  1");
                    for (int i = 0; i < 2; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 2; j++) {
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);
                        }
                        System.out.println();
                    }
                    System.out.println("    x2   ");
                    System.out.println();
                    System.out.println("Due gruppi separati di 4 tessere dello stesso colore che formano un quadrato 2x2 in qualsiasi punto all'interno della griglia giocatore.\nLe tessere di due gruppi devono essere dello stesso colore.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 2 -> {
                    //2
                    for (int i = 0; i < 6; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 1; j++) {
                            if (i == 0) {
                                System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                            } else if (i == 1) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 2) {
                                System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                            } else if (i == 3) {
                                System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                            } else if (i == 4) {
                                System.out.print("\u001b[43m" + "   " + ANSI_RESET);  //yellow
                            } else if (i == 5) {
                                System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("   x2   ");
                    System.out.println();
                    System.out.println("Due colonne formate ciascuna da 6 tessere diverse.\n" +
                            "(L'ordine con cui sono posizionate le tessere in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 3 -> {
                    //3
                    for (int i = 0; i < 4; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 1; j++) {
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        System.out.println();
                    }
                    System.out.println("   x4   ");
                    System.out.println();
                    System.out.println("Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso colore, ma non necessariamente come mostrato in figura.\n" +
                            "Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 4 -> {
                    //4
                    for (int i = 0; i < 2; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 1; j++) {
                            System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                        }
                        System.out.println();
                    }
                    System.out.println("   x6   ");
                    System.out.println();
                    System.out.println("Sei gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso colore, ma non necessariamente come mostrato in figura.\n" +
                            "Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 5 -> {
                    //5
                    for (int i = 0; i < 6; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 1; j++) {
                            System.out.print("\u001b[47;1m" + "   " + ANSI_RESET);  //BrightWhite (è uguale al bianco normale)
                        }
                        System.out.println();
                    }
                    System.out.println("   x3   ");
                    System.out.println();
                    System.out.println("MAX 3 COLORI DIVERSI!");
                    System.out.println("Tre colonne formate ciascuna da 6 tessere di uno, due o tre colori differenti.\n" +
                            "Colonne diverse possono avere combinazioni diverse di colori di tessere.");
                    System.out.println();
                }
                case 6 -> {
                    //6
                    System.out.println("   0  1  2  3  4");
                    for (int i = 0; i < 1; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 5; j++) {
                            if (j == 0) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (j == 1) {
                                System.out.print("\u001b[42m" + "   " + ANSI_RESET);  //green
                            } else if (j == 2) {
                                System.out.print("\u001b[44m" + "   " + ANSI_RESET);  //blue
                            } else if (j == 3) {
                                System.out.print("\u001b[47m" + "   " + ANSI_RESET);  //white
                            } else if (j == 4) {
                                System.out.print("\u001b[46m" + "   " + ANSI_RESET);  //azure
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("         x2   ");
                    System.out.println();
                    System.out.println("Due righe formate ciascuna da 5 diversi colori di tessere.\n" +
                            "(L'ordine con cui sono posizionate le tessere in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 7 -> {
                    //7
                    System.out.println("   0  1  2  3  4");
                    for (int i = 0; i < 1; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 5; j++) {
                            System.out.print("\u001b[47;1m" + "   " + ANSI_RESET);  //BrightWhite (è uguale al bianco normale)
                        }
                        System.out.println();
                    }
                    System.out.println("         x4   ");
                    System.out.println();
                    System.out.println("MAX 3 COLORI DIVERSI!");
                    System.out.println("Quattro righe formate ciascuna da 5 tessere di uno, due o tre colori differenti.\n" +
                            "Righe diverse possono avere combinazioni diverse di colori di tessere.");
                    System.out.println();
                }
                case 8 -> {
                    //8
                    System.out.println("   0  1  2  3  4");
                    for (int i = 0; i < 6; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 5; j++) {
                            if (i == 0 && j == 0) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 0 && j == 4) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 5 && j == 0) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 5 && j == 4) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else {
                                System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("Quattro tessere dello stesso colore ai quattro angoli della libreria.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 9 -> {
                    //9
                    System.out.println("Otto tessere dello stesso colore.\n" +
                            "Non ci sono restrizioni sulla posizione di queste tessere.");
                    System.out.println();
                }
                case 10 -> {
                    //10
                    System.out.println("   0  1  2");
                    for (int i = 0; i < 3; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 3; j++) {
                            if (i == 0 && j == 0) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 0 && j == 2) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 1 && j == 1) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 2 && j == 0) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else if (i == 2 && j == 2) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else {
                                System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("Cinque tessere dello stesso colore che formano una X.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 11 -> {
                    //11
                    System.out.println("   0  1  2  3  4");
                    for (int i = 0; i < 5; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 5; j++) {
                            if (i == j) {
                                System.out.print("\u001b[45m" + "   " + ANSI_RESET);  //pink
                            } else {
                                System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("Cinque tessere dello stesso colore che formano una diagonale.\n" +
                            "(Il colore usato in questa grafica è solo un esempio).");
                    System.out.println();
                }
                case 12 -> {
                    //12
                    System.out.println("   0  1  2  3  4");
                    for (int i = 0; i < 5; i++) {
                        System.out.print(i + " ");
                        for (int j = 0; j < 5; j++) {
                            if (i >= j) {
                                System.out.print("\u001b[47;1m" + "   " + ANSI_RESET);  //BrightWhite (è uguale al bianco normale)
                            } else {
                                System.out.print("\u001b[40m" + "   " + ANSI_RESET);
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna a sinistra o a destra, " +
                            "ogni colonna successiva deve essere formata da una tessera in più.\n" +
                            "Le tessere possono essere di qualsiasi colore.");
                    System.out.println();
                }
            }
        }
    }

    public HashMap<String, Integer> getPersonalGoalViews() {
        return personalGoalViews;
    }

    public void setPersonalGoalViews(HashMap<String, Integer> personalGoalViews) {
        this.personalGoalViews = personalGoalViews;
    }

    public List<Integer> getCommonGoalView() {
        return commonGoalView;
    }

    public void setCommonGoalView(List<Integer> commonGoalView) {
        this.commonGoalView = commonGoalView;
    }

    /**
     * The class is used to print the player's stuff
     * @param username player's name
     */
    public void printPlayerStuff(String username) {

        int count_grid = 0;
        String[][] grid = this.getGridsview().get(username);
        String[] tiles = tilesview.get(username);
        //Printing board
        System.out.println("\nPrinting updated board:");
        System.out.println("   0  1  2  3  4  5  6  7  8        0  1  2  3  4");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    //System.out.println(j);
                    String backgroundColor = backgroundColorMap.get(board[i][j]);
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
            }
            if (count_grid < 6) {
                System.out.print("      ");
                for (int k = 0; k < grid[i].length; k++) {
                    if (grid[i][k] == null) {
                        System.out.print(ANSI_BROWN + "   " + ANSI_RESET);
                    } else {
                        String backgroundColor = backgroundColorMap.get(grid[i][k]);
                        if (backgroundColor == null)
                            backgroundColor = ANSI_BROWN; // Default to brown if color not found, usless check
                        System.out.print(backgroundColor + "   " + ANSI_RESET);
                    }
                }
                System.out.println();
                count_grid++;
            }
            if(i == 6){
                System.out.println();
            }
            if (i == 7) {
                System.out.print("        ");
                for (String tile : tiles) {
                    String backgroundColor = backgroundColorMap.get(tile);
                    if (backgroundColor == null) {
                        backgroundColor = ANSI_BROWN;
                    }
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
                System.out.println();
            }
            if (i == 8) {
                System.out.print("         0  1  2");
            }
        }
        System.out.println();
        System.out.println("To visualize instruction set: help");
    }

    /**
     * The method is used to print the grids of the pther players
     * @param username player's name
     */
    public void printOtherGrids(String username){
        ArrayList<String[][]> gridList = new ArrayList<>();
        for(Map.Entry<String, String[][]> entry : gridsview.entrySet()) {
            if(!(entry.getKey().equals(username))) {
                gridList.add(entry.getValue());
                String name = entry.getKey();
                int length = name.length();
                if(length <= 15)
                    System.out.print(entry.getKey());
                else {
                    System.out.print(name.substring(0, 14));
                    System.out.print("...");
                    length = 18;
                }
                for(int i = 0; i < 23 - length; i++){
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < gridList.size(); j++){
                for(int k = 0; k < 5; k++){
                    if (gridList.get(j)[i][k] == null) {
                        System.out.print(ANSI_BROWN + "   " + ANSI_RESET);
                    } else {
                        String backgroundColor = backgroundColorMap.get(gridList.get(j)[i][k]);
                        if (backgroundColor == null) backgroundColor = ANSI_BROWN; // Default to brown if color not found, usless check
                        System.out.print(backgroundColor + "   " + ANSI_RESET);
                    }
                }
                System.out.print("        ");
            }
            System.out.println();
        }
    }

    /**
     * The method prints the entire chat, displaying all the messages present.
     * Each message is printed with the username of the sender.
     * If the username matches the specified one, the displayed name will be "You".
     * @param username the username for which you want to print the chat
     */
    public void printChat(String username){
        for(int i = 0; i < chat.size(); i++){
            String sender;
            ChatMessage message = chat.get(i);
            if(message.getUsername().equals(username)){
                sender = "You";
            }else{
                sender = message.getUsername();
            }
            System.out.println(sender + ": " + message.getText());
        }
    }

    /**
     * The method prints the last message of the chat.
     * The message is printed with the username of the sender.
     * If the username matches the specified one, the displayed name will be "You".
     * @param username the username for which you want to print the last message
     */
    public void printChatLast(String username){
        String sender;
        ChatMessage message = chat.get(chat.size() - 1);
        if(message.getUsername().equals(username)){
            sender = "You";
        }else{
            sender = message.getUsername();
        }
        System.out.println(sender + ": " + message.getText());
    }
    public void printPrivateChat(String username, String sender){
        List<ChatMessage> messages = privateChats.get(sender);
        if(!findChat(sender)){
            return;
        }
        if(messages.isEmpty())
            return;
        for(int i = 0; i < messages.size(); i++){
            ChatMessage message = messages.get(i);
            if(message.getUsername().equals(username)){
                System.out.println( "You: " + message.getText());
            }else{
                System.out.println(sender + ": " + message.getText());
            }
        }
    }
    public void printPrivateChatLast(String username, String sender){
        List<ChatMessage> messages = privateChats.get(sender);
        if(!findChat(sender)){
            return;
        }
        if(messages.isEmpty())
            return;
        ChatMessage message = messages.get(messages.size() - 1);
        if(message.getUsername().equals(username)){
            System.out.println("You: " + message.getText());
        }else{
            System.out.println(sender + ": " + message.getText());
        }
    }
    public boolean findChat(String name){
        boolean check = false;
        for(Map.Entry<String, List<ChatMessage>> entry : privateChats.entrySet()){
            String user = entry.getKey();
            if(user.equalsIgnoreCase(name)){
                check = true;
                break;
            }
        }
        return check;
    }

}
