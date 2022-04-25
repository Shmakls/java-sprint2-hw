package ru.andrianov.common;

import com.google.gson.*;
import ru.andrianov.server.HttpTaskServer;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class MyGsonBuilder {

    public static Gson build() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
                .registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeDeserializer());
        return gsonBuilder.create();
    }

    private static class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

        @Override
        public JsonElement serialize(ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(zonedDateTime.toString());
        }
    }

    private static class ZoneDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

        @Override
        public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String zdt = jsonElement.getAsString();
            return ZonedDateTime.parse(zdt);
        }
    }

}
