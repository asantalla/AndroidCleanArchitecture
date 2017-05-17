package co.develoop.androidcleanarchitecturesample.client.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;


public class RandomUserListDeserializer implements JsonDeserializer<List<RandomUser>> {

    @Override
    public List<RandomUser> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<RandomUser> randomUsers = null;

        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject != null && !json.isJsonNull()) {
            JsonArray resultsJsonArray = jsonObject.get("results").getAsJsonArray();

            if (resultsJsonArray != null && !resultsJsonArray.isJsonNull()) {
                randomUsers = new ArrayList<>();

                Gson gson = new Gson();

                for (int i = 0; i < resultsJsonArray.size(); i++) {
                    JsonElement jsonElement = resultsJsonArray.get(i);

                    RandomUser randomUser = gson.fromJson(jsonElement, RandomUser.class);
                    if (!randomUsers.contains(randomUser)) {
                        String registeredDate = jsonElement.getAsJsonObject().get("registered").getAsString();
                        randomUser.setRegisteredDate(DateTime.parse(registeredDate, newDateTimeFormatterInstance()));
                        randomUsers.add(randomUser);
                    }
                }
            }
        }

        return randomUsers;
    }

    private DateTimeFormatter newDateTimeFormatterInstance() {
        return new DateTimeFormatterBuilder()
                .appendYear(4, 4)
                .appendLiteral('-')
                .appendMonthOfYear(2)
                .appendLiteral('-')
                .appendDayOfMonth(2)
                .appendLiteral(' ')
                .appendHourOfDay(2)
                .appendLiteral(':')
                .appendMinuteOfHour(2)
                .appendLiteral(':')
                .appendSecondOfMinute(2)
                .toFormatter();
    }
}