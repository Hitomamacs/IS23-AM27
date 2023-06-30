package org.project.Model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PlayerDeserializer implements JsonDeserializer<Player> {
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Player player = gson.fromJson(json, Player.class);
        player.initializeSupport();  // Metodo per inizializzare support
        return player;
    }
}