package io.github.nsdigirolamo.powerportals.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.io.IOException;
import java.util.UUID;

/**
 * BlockAdapter adapts bukkit Block interfaces so Gson can read/write them.
 */
public class BlockAdapter extends TypeAdapter<Block> {

    @Override
    public void write(JsonWriter writer, Block block) throws IOException {
        if (block == null) {
            writer.nullValue();
            return;
        }
        String world = block.getWorld().getUID().toString();
        String x = "" + block.getX();
        String y = "" + block.getY();
        String z = "" + block.getZ();
        writer.value(world + "," + x + "," + y + "," + z);
    }

    @Override
    public Block read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String[] parts = reader.nextString().split(",");
        return Bukkit.getWorld(UUID.fromString(parts[0])).
                getBlockAt(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
    }
}
