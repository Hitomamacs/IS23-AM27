package org.project.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * auxiliary class used to create the json file for the personal goals
 */

public class POb_2Js {

    public static void to_json(POb_2Js pOb_2Js){
        Gson gson_parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json =  gson_parser.toJson(pOb_2Js);
        try {
            PrintWriter out = new PrintWriter(new FileWriter("PGoals" + ".json"));
            out.println(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PersonalGoal> getPersonalGoals_list() {
        return personalGoals_list;
    }

    @Expose
    private List<PersonalGoal> personalGoals_list;



    public POb_2Js(){
        List<Goal> goals;
        PersonalGoal personalGoal;
        personalGoals_list = new ArrayList<PersonalGoal>();

        // PersonalGoal 1
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(0, 0), Color.PINK));
        goals.add(new Goal(new Coordinates(0, 2), Color.BLUE));
        goals.add(new Goal(new Coordinates(1, 4), Color.GREEN));
        goals.add(new Goal(new Coordinates(2, 3), Color.WHITE));
        goals.add(new Goal(new Coordinates(3, 1), Color.YELLOW));
        goals.add(new Goal(new Coordinates(5, 2), Color.AZURE));
        personalGoal = new PersonalGoal(0, goals);
        personalGoals_list.add(personalGoal);

        // PersonalGoal 2
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(1, 1), Color.PINK));
        goals.add(new Goal(new Coordinates(5, 4), Color.BLUE));
        goals.add(new Goal(new Coordinates(2, 0), Color.GREEN));
        goals.add(new Goal(new Coordinates(3, 4), Color.WHITE));
        goals.add(new Goal(new Coordinates(2, 2), Color.YELLOW));
        goals.add(new Goal(new Coordinates(4, 3), Color.AZURE));
        personalGoal = new PersonalGoal(1, goals);
        personalGoals_list.add(personalGoal);

        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(2, 2), Color.PINK));
        goals.add(new Goal(new Coordinates(1, 0), Color.BLUE));
        goals.add(new Goal(new Coordinates(3, 1), Color.GREEN));
        goals.add(new Goal(new Coordinates(5, 0), Color.WHITE));
        goals.add(new Goal(new Coordinates(1, 3), Color.YELLOW));
        goals.add(new Goal(new Coordinates(3, 4), Color.AZURE));
        personalGoal = new PersonalGoal(2, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 4
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(3, 3), Color.PINK));
        goals.add(new Goal(new Coordinates(2, 2), Color.BLUE));
        goals.add(new Goal(new Coordinates(4, 2), Color.GREEN));
        goals.add(new Goal(new Coordinates(4, 1), Color.WHITE));
        goals.add(new Goal(new Coordinates(0, 4), Color.YELLOW));
        goals.add(new Goal(new Coordinates(2, 0), Color.AZURE));
        personalGoal = new PersonalGoal(3, goals);
        personalGoals_list.add(personalGoal);

        // PersonalGoal 5
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(4, 4), Color.PINK));
        goals.add(new Goal(new Coordinates(3, 1), Color.BLUE));
        goals.add(new Goal(new Coordinates(5, 3), Color.GREEN));
        goals.add(new Goal(new Coordinates(3, 2), Color.WHITE));
        goals.add(new Goal(new Coordinates(5, 0), Color.YELLOW));
        goals.add(new Goal(new Coordinates(1, 1), Color.AZURE));
        personalGoal = new PersonalGoal(4, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 6
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(5, 0), Color.PINK));
        goals.add(new Goal(new Coordinates(4, 3), Color.BLUE));
        goals.add(new Goal(new Coordinates(0, 4), Color.GREEN));
        goals.add(new Goal(new Coordinates(2, 3), Color.WHITE));
        goals.add(new Goal(new Coordinates(4, 1), Color.YELLOW));
        goals.add(new Goal(new Coordinates(0, 2), Color.AZURE));
        personalGoal = new PersonalGoal(5, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 7
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(2, 1), Color.PINK));
        goals.add(new Goal(new Coordinates(1, 3), Color.BLUE));
        goals.add(new Goal(new Coordinates(0, 0), Color.GREEN));
        goals.add(new Goal(new Coordinates(5, 2), Color.WHITE));
        goals.add(new Goal(new Coordinates(4, 4), Color.YELLOW));
        goals.add(new Goal(new Coordinates(3, 0), Color.AZURE));
        personalGoal = new PersonalGoal(6, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 8
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(3, 0), Color.PINK));
        goals.add(new Goal(new Coordinates(0, 4), Color.BLUE));
        goals.add(new Goal(new Coordinates(1, 1), Color.GREEN));
        goals.add(new Goal(new Coordinates(4, 3), Color.WHITE));
        goals.add(new Goal(new Coordinates(5, 3), Color.YELLOW));
        goals.add(new Goal(new Coordinates(2, 2), Color.AZURE));
        personalGoal = new PersonalGoal(7, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 9
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(4, 4), Color.PINK));
        goals.add(new Goal(new Coordinates(5, 0), Color.BLUE));
        goals.add(new Goal(new Coordinates(2, 2), Color.GREEN));
        goals.add(new Goal(new Coordinates(3, 4), Color.WHITE));
        goals.add(new Goal(new Coordinates(0, 2), Color.YELLOW));
        goals.add(new Goal(new Coordinates(4, 1), Color.AZURE));
        personalGoal = new PersonalGoal(8, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 10
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(5, 3), Color.PINK));
        goals.add(new Goal(new Coordinates(4, 1), Color.BLUE));
        goals.add(new Goal(new Coordinates(3, 3), Color.GREEN));
        goals.add(new Goal(new Coordinates(2, 0), Color.WHITE));
        goals.add(new Goal(new Coordinates(1, 1), Color.YELLOW));
        goals.add(new Goal(new Coordinates(0, 4), Color.AZURE));
        personalGoal = new PersonalGoal(9, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 11
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(0, 2), Color.PINK));
        goals.add(new Goal(new Coordinates(3, 2), Color.BLUE));
        goals.add(new Goal(new Coordinates(4, 4), Color.GREEN));
        goals.add(new Goal(new Coordinates(1, 1), Color.WHITE));
        goals.add(new Goal(new Coordinates(2, 0), Color.YELLOW));
        goals.add(new Goal(new Coordinates(5, 3), Color.AZURE));
        personalGoal = new PersonalGoal(10, goals);
        personalGoals_list.add(personalGoal);

// PersonalGoal 12
        goals = new ArrayList<>();
        goals.add(new Goal(new Coordinates(1, 1), Color.PINK));
        goals.add(new Goal(new Coordinates(2, 2), Color.BLUE));
        goals.add(new Goal(new Coordinates(5, 0), Color.GREEN));
        goals.add(new Goal(new Coordinates(0, 2), Color.WHITE));
        goals.add(new Goal(new Coordinates(4, 4), Color.YELLOW));
        goals.add(new Goal(new Coordinates(3, 3), Color.AZURE));
        personalGoal = new PersonalGoal(11, goals);
        personalGoals_list.add(personalGoal);

        // PersonalGoal 12

    }

    }









