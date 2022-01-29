package io.github.nsdigirolamo.powerportals.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

/**
 * PlayerAdapter adapts bukkit Player interfaces so Gson can read/write them.
 */
public class PlayerAdapter extends TypeAdapter<Player> {

    @Override
    public void write(JsonWriter writer, Player player) throws IOException {
        if (player == null) {
            writer.nullValue();
            return;
        }
        writer.value(player.getUniqueId().toString());
    }

    @Override
    public Player read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return Bukkit.getPlayer(UUID.fromString(reader.nextString()));
    }
}
