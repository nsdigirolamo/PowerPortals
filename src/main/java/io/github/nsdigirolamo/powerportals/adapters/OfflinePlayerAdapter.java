package io.github.nsdigirolamo.powerportals.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.UUID;

/**
 * OfflinePlayerAdapter adapts bukkit OfflinePlayer interfaces so Gson can read/write them.
 */
public class OfflinePlayerAdapter extends TypeAdapter<OfflinePlayer> {

    @Override
    public void write(JsonWriter writer, OfflinePlayer offlinePlayer) throws IOException {
        if (offlinePlayer == null) {
            writer.nullValue();
            return;
        }
        writer.value(offlinePlayer.getUniqueId().toString());
    }

    @Override
    public OfflinePlayer read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return Bukkit.getOfflinePlayer(UUID.fromString(reader.nextString()));
    }
}
