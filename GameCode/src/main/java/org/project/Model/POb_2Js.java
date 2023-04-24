package org.project.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

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

    public HashMap<Coordinates, Color>[] getPersonalGoals_list() {
        return personalGoals_list;
    }

    @Expose
    private HashMap<Coordinates, Color>[] personalGoals_list = new HashMap[12];



    public POb_2Js(){
        HashMap<Coordinates, Color> coloredGoal1 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal2 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal3 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal4 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal5 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal6 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal7 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal8 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal9 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal10 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal11 = new HashMap<>();
        HashMap<Coordinates, Color> coloredGoal12 = new HashMap<>();
        coloredGoal1.put(new Coordinates(0, 0), Color.PINK);
        coloredGoal1.put(new Coordinates(0, 2), Color.BLUE);
        coloredGoal1.put(new Coordinates(1, 4), Color.GREEN);
        coloredGoal1.put(new Coordinates(2, 3), Color.WHITE);
        coloredGoal1.put(new Coordinates(3, 1), Color.YELLOW);
        coloredGoal1.put(new Coordinates(5, 2), Color.AZURE);
        coloredGoal2.put(new Coordinates(1, 1), Color.PINK);
        coloredGoal2.put(new Coordinates(5, 4), Color.BLUE);
        coloredGoal2.put(new Coordinates(2, 0), Color.GREEN);
        coloredGoal2.put(new Coordinates(3, 4), Color.WHITE);
        coloredGoal2.put(new Coordinates(2, 2), Color.YELLOW);
        coloredGoal2.put(new Coordinates(4, 3), Color.AZURE);
        coloredGoal3.put(new Coordinates(2, 2), Color.PINK);
        coloredGoal3.put(new Coordinates(1, 0), Color.BLUE);
        coloredGoal3.put(new Coordinates(3, 1), Color.GREEN);
        coloredGoal3.put(new Coordinates(5, 0), Color.WHITE);
        coloredGoal3.put(new Coordinates(1, 3), Color.YELLOW);
        coloredGoal3.put(new Coordinates(3, 4), Color.AZURE);
        coloredGoal4.put(new Coordinates(3, 3), Color.PINK);
        coloredGoal4.put(new Coordinates(2, 2), Color.BLUE);
        coloredGoal4.put(new Coordinates(4, 2), Color.GREEN);
        coloredGoal4.put(new Coordinates(4, 1), Color.WHITE);
        coloredGoal4.put(new Coordinates(0, 4), Color.YELLOW);
        coloredGoal4.put(new Coordinates(2, 0), Color.AZURE);
        coloredGoal5.put(new Coordinates(4, 4), Color.PINK);
        coloredGoal5.put(new Coordinates(3, 1), Color.BLUE);
        coloredGoal5.put(new Coordinates(5, 3), Color.GREEN);
        coloredGoal5.put(new Coordinates(3, 2), Color.WHITE);
        coloredGoal5.put(new Coordinates(5, 0), Color.YELLOW);
        coloredGoal5.put(new Coordinates(1, 1), Color.AZURE);
        coloredGoal6.put(new Coordinates(5, 0), Color.PINK);
        coloredGoal6.put(new Coordinates(4, 3), Color.BLUE);
        coloredGoal6.put(new Coordinates(0, 4), Color.GREEN);
        coloredGoal6.put(new Coordinates(2, 3), Color.WHITE);
        coloredGoal6.put(new Coordinates(4, 1), Color.YELLOW);
        coloredGoal6.put(new Coordinates(0, 2), Color.AZURE);
        coloredGoal7.put(new Coordinates(2, 1), Color.PINK);
        coloredGoal7.put(new Coordinates(1, 3), Color.BLUE);
        coloredGoal7.put(new Coordinates(0, 0), Color.GREEN);
        coloredGoal7.put(new Coordinates(5, 2), Color.WHITE);
        coloredGoal7.put(new Coordinates(4, 4), Color.YELLOW);
        coloredGoal7.put(new Coordinates(3, 0), Color.AZURE);
        coloredGoal8.put(new Coordinates(3, 0), Color.PINK);
        coloredGoal8.put(new Coordinates(0, 4), Color.BLUE);
        coloredGoal8.put(new Coordinates(1, 1), Color.GREEN);
        coloredGoal8.put(new Coordinates(4, 3), Color.WHITE);
        coloredGoal8.put(new Coordinates(5, 3), Color.YELLOW);
        coloredGoal8.put(new Coordinates(2, 2), Color.AZURE);
        coloredGoal9.put(new Coordinates(4, 4), Color.PINK);
        coloredGoal9.put(new Coordinates(5, 0), Color.BLUE);
        coloredGoal9.put(new Coordinates(2, 2), Color.GREEN);
        coloredGoal9.put(new Coordinates(3, 4), Color.WHITE);
        coloredGoal9.put(new Coordinates(0, 2), Color.YELLOW);
        coloredGoal9.put(new Coordinates(4, 1), Color.AZURE);
        coloredGoal10.put(new Coordinates(5, 3), Color.PINK);
        coloredGoal10.put(new Coordinates(4, 1), Color.BLUE);
        coloredGoal10.put(new Coordinates(3, 3), Color.GREEN);
        coloredGoal10.put(new Coordinates(2, 0), Color.WHITE);
        coloredGoal10.put(new Coordinates(1, 1), Color.YELLOW);
        coloredGoal10.put(new Coordinates(0, 4), Color.AZURE);
        coloredGoal11.put(new Coordinates(0, 2), Color.PINK);
        coloredGoal11.put(new Coordinates(3, 2), Color.BLUE);
        coloredGoal11.put(new Coordinates(4, 4), Color.GREEN);
        coloredGoal11.put(new Coordinates(1, 1), Color.WHITE);
        coloredGoal11.put(new Coordinates(2, 0), Color.YELLOW);
        coloredGoal11.put(new Coordinates(5, 3), Color.AZURE);
        coloredGoal12.put(new Coordinates(1, 1), Color.PINK);
        coloredGoal12.put(new Coordinates(2, 2), Color.BLUE);
        coloredGoal12.put(new Coordinates(5, 0), Color.GREEN);
        coloredGoal12.put(new Coordinates(0, 2), Color.WHITE);
        coloredGoal12.put(new Coordinates(4, 4), Color.YELLOW);
        coloredGoal12.put(new Coordinates(3, 3), Color.AZURE);
        personalGoals_list[0] = coloredGoal1;
        personalGoals_list[1] = coloredGoal2;
        personalGoals_list[2] = coloredGoal3;
        personalGoals_list[3] = coloredGoal4;
        personalGoals_list[4] = coloredGoal5;
        personalGoals_list[5] = coloredGoal6;
        personalGoals_list[6] = coloredGoal7;
        personalGoals_list[7] = coloredGoal8;
        personalGoals_list[8] = coloredGoal9;
        personalGoals_list[9] = coloredGoal10;
        personalGoals_list[10] = coloredGoal11;
        personalGoals_list[11] = coloredGoal12;

    }


    }






