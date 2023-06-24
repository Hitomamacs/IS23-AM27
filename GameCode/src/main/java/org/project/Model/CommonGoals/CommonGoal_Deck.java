package org.project.Model.CommonGoals;


import org.project.Model.CommonGoals.*;

import java.util.ArrayList;
import java.util.Random;

public class CommonGoal_Deck {
    ArrayList<CommonGoal> deck=new ArrayList<>();

    public CommonGoal_Deck() {
        fillDeck();
    }
    public ArrayList<CommonGoal> fillDeck(){
        CommonGoal co1=new CommonGoal_1();
        deck.add(co1);
        CommonGoal co2=new CommonGoal_2();
        deck.add(co2);
        CommonGoal co3=new CommonGoal_3();
        deck.add(co3);
        CommonGoal co4=new CommonGoal_4();
        deck.add(co4);
        CommonGoal co5=new CommonGoal_5();
        deck.add(co5);
        CommonGoal co6=new CommonGoal_6();
        deck.add(co6);
        CommonGoal co7=new CommonGoal_7();
        deck.add(co7);
        CommonGoal co8=new CommonGoal_8();
        deck.add(co8);
        CommonGoal co9=new CommonGoal_9();
        deck.add(co9);
        CommonGoal co10=new CommonGoal_10();
        deck.add(co10);
        CommonGoal co11=new CommonGoal_11();
        deck.add(co11);
        CommonGoal co12=new CommonGoal_12();
        deck.add(co12);

        return deck;
    }

    public CommonGoal getRandom(){
        Random random= new Random();
        int randomIndex= random.nextInt(deck.size());
        CommonGoal randomGoal=deck.get(randomIndex);
        deck.remove(randomIndex);
        return randomGoal;
    }

    public ArrayList<CommonGoal> getDeck() {
        return deck;
    }
}
