package hwr.oop.group4.todo.persistence.json.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import hwr.oop.group4.todo.core.Idea;

import java.lang.reflect.Type;

public class IdeaDeserializer implements JsonDeserializer<Idea> {

    @Override
    public Idea deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // TODO: implement
        System.out.println(jsonElement);
        System.out.println(type);
        System.out.println(jsonDeserializationContext);
        return new Idea("idea");
    }
}
