package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.UUID;

/**
 * Adapts OfflinePlayers for conversion to and from json files by Gson
 */
public class OfflinePlayerAdapter extends TypeAdapter<OfflinePlayer> {

    /**
     * Writes an OfflinePlayer into a format that can be stored in a json file
     * @param writer the json writer
     * @param player the Java object to write. May be null.
     * @throws IOException if there is a problem with the writer
     */
    @Override
    public void write(JsonWriter writer, OfflinePlayer player) throws IOException {
        if (player == null) {
            writer.nullValue();
        } else {
            writer.value(player.getUniqueId().toString());
        }
    }

    /**
     * Reads an OfflinePlayer from json into a OfflinePlayer in the game world
     * @param reader the json reader
     * @return the OfflinePlayer stored in json format
     * @throws IOException if there is a problem with the reader
     */
    @Override
    public OfflinePlayer read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            return null;
        } else {
            return Bukkit.getOfflinePlayer(UUID.fromString(reader.nextString()));
        }
    }
}
