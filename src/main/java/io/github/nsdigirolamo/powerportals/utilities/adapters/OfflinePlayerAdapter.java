package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class OfflinePlayerAdapter extends TypeAdapter<OfflinePlayer> {

    @Override
    public void write(JsonWriter writer, OfflinePlayer player) throws IOException {
        if (player == null) {
            writer.nullValue();
        } else {
            writer.value(player.getUniqueId().toString());
        }
    }

    @Override
    public OfflinePlayer read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            return null;
        } else {
            return Bukkit.getOfflinePlayer(reader.nextString());
        }
    }
}
