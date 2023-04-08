package org.Project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Persistencer {
    static Gson gson_parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //* This method takes a GameOrchestrator object and returns a JSON string and then takes the JSON sting and writes it to a file*/
    public static void saveGame(GameOrchestrator gameOrchestrator, String fileName) {
        String json = gson_parser.toJson(gameOrchestrator);
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            out.println(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
