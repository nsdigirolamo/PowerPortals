package io.github.nsdigirolamo.powerportals.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;
import java.util.UUID;

/**
 * WorldAdapter adapts bukkit World interfaces so Gson can read/write them.
 */
public class WorldAdapter extends TypeAdapter<World> {

    @Override
    public void write(JsonWriter writer, World world) throws IOException {
        if (world == null) {
            writer.nullValue();
            return;
        }
        writer.value(world.getUID().toString());
    }

    @Override
    public World read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return Bukkit.getWorld(UUID.fromString(reader.nextString()));
    }
}
