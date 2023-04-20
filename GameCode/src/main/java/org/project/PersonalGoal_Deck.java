package org.project;

import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.project.Persistencer.gson_parser;

public class PersonalGoal_Deck {
        @Expose
        List<PersonalGoal> deck;

        private POb_2Js pOb_2Js = new POb_2Js();

        public PersonalGoal_Deck() {
            deck = new ArrayList<>();
        }
        public void fillDeck(String filename){

            Reader reader = null;
            try {
                reader = Files.newBufferedReader(Paths.get(filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HashMap<Coordinates, Color> personalGoals_list = gson_parser.fromJson(reader, HashMap.class);
            for(int i = 0; i< personalGoals_list.; i++){
                //deck.add(new PersonalGoal(i, personalGoals_list.get(i)));
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

