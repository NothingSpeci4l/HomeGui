package fr.gregwll.homeGui.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwll.homeGui.obj.User;

public class UserSerializationManager {
    private Gson gson;

    public UserSerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(User user) {
        return this.gson.toJson(user);
    }

    public User deserialize(String json) {
        return this.gson.fromJson(json, User.class);
    }
}
