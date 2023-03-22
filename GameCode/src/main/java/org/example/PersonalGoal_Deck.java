package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonalGoal_Deck {
        List<PersonalGoal> deck;

        public PersonalGoal_Deck() {
            deck = new ArrayList<>();
            fillDeck();
        }
        public void fillDeck(){

           deck.add(new PersonalGoal_1());
           deck.add(new PersonalGoal_2());
           deck.add(new PersonalGoal_3());
           deck.add(new PersonalGoal_4());
           deck.add(new PersonalGoal_5());
           deck.add(new PersonalGoal_6());
           deck.add(new PersonalGoal_7());
           deck.add(new PersonalGoal_8());
           deck.add(new PersonalGoal_9());
           deck.add(new PersonalGoal_10());
           deck.add(new PersonalGoal_11());
           deck.add(new PersonalGoal_12());

           for(int i = 0; i < deck.size(); i++){
               deck.get(i).initialize();
           }

        }

        public PersonalGoal getRandom(){
            Random random = new Random();
            int randomIndex= random.nextInt(deck.size());
            PersonalGoal randomGoal = deck.get(randomIndex);
            deck.remove(randomGoal);
            return randomGoal;
        }
    }

