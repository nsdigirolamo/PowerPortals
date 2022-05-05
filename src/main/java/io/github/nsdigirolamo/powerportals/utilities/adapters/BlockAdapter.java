package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.IOException;
import java.util.UUID;

/**
 * Adapts Blocks for conversion to and from json files by Gson
 */
public class BlockAdapter extends TypeAdapter<Block> {

    /**
     * Writes a Block into a format that can be stored in a json file
     * @param writer the json writer
     * @param block the Java object to write. May be null.
     * @throws IOException if there is a problem with the writer
     */
    @Override
    public void write(JsonWriter writer, Block block) throws IOException {
        if (block == null) {
            writer.nullValue();
        } else {
            String world = block.getWorld().getUID().toString();
            String x = "" + block.getX();
            String y = "" + block.getY();
            String z = "" + block.getZ();
            writer.value(world + "," + x + "," + y + "," + z);
        }
    }

    /**
     * Reads a Block from json into a Block in the game world
     * @param reader the json reader
     * @return the Block stored in json format
     * @throws IOException if there is a problem with the reader
     */
    @Override
    public Block read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        } else {
            String[] parts = reader.nextString().split(",");
            World world = Bukkit.getWorld(UUID.fromString(parts[0]));
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            if (world != null) {
                return world.getBlockAt(x, y, z);
            } else {
                return null;
            }
        }
    }
}
