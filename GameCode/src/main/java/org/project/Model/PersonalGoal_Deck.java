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

public class PersonalGoal_Deck {
        @Expose
        List<PersonalGoal> deck;

        private POb_2Js pOb_2Js = new POb_2Js();

        public PersonalGoal_Deck() {
            deck = new ArrayList<>();
        }
        public void fillDeck(String filename){

            Reader reader = null;

                /*URL url = this.getClass()
                        .getClassLoader()
                        .getResource("test_1.json");
                File file = new File(url.getPath());
                //open file as buffer reader
                reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));*/
            ClassLoader classLoader=this.getClass().getClassLoader();
            String file= "test_1.json";
            InputStream inputStream= classLoader.getResourceAsStream(file);
            BufferedReader read= new BufferedReader(new InputStreamReader(inputStream));

            List<HashMap<Coordinates, Color>> personalGoals_list = gson_parser.fromJson(read, List.class);
            for(int i = 0; i< personalGoals_list.size(); i++){
                deck.add(new PersonalGoal(i, personalGoals_list.get(i)));
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

