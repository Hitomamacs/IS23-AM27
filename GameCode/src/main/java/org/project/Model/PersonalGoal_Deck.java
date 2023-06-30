package org.project.Model;

import com.google.gson.annotations.Expose;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.project.Controller.Control.Persistencer.gson_parser;

/**
 * Class that represents the entire deck of personal goal cards
 */

public class PersonalGoal_Deck {
    @Expose
    List<PersonalGoal> deck;

    /**
     * Structure containing a list of personal objective goal cards
     */
    private POb_2Js pOb_2Js = new POb_2Js();
    public PersonalGoal_Deck() {
            deck = new ArrayList<>();
        }

    /**
     * The method is used to fill the deck with personal objective cards from a JSON file
     * @param filename JSON file to read personal goal cards from
     */
    public void fillDeck(String filename){
        Reader reader = null;

        ClassLoader classLoader=this.getClass().getClassLoader();
        String file= "PGoals.json";
        InputStream inputStream= classLoader.getResourceAsStream(file);
        BufferedReader read= new BufferedReader(new InputStreamReader(inputStream));

        pOb_2Js = gson_parser.fromJson(read, POb_2Js.class);
        for(int i = 0; i< pOb_2Js.getPersonalGoals_list().size(); i++) {
            deck.add((PersonalGoal) pOb_2Js.getPersonalGoals_list().get(i));
        }
        System.out.println("Test");
    }

    /**
     * The method is used to randomly draw a personal goal card from the deck
     * @return random goal card drawn from the deck
     */
    public PersonalGoal getRandom(){
        Random random = new Random();
        int randomIndex= random.nextInt(deck.size());
        PersonalGoal randomGoal = deck.get(randomIndex);
        deck.remove(randomGoal);
        return randomGoal;
    }
}

