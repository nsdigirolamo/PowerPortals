package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.block.BlockFace;

import java.io.IOException;

/**
 * Adapts BlockFaces for conversion to and from json files by Gson
 */
public class BlockFaceAdapter extends TypeAdapter<BlockFace> {

    /**
     * Writes a BlockFace into a format that can be stored in a json file
     * @param writer the json writer
     * @param blockface the Java object to write. May be null.
     * @throws IOException if there is a problem with the writer
     */
    @Override
    public void write(JsonWriter writer, BlockFace blockface) throws IOException {
        if (blockface == null) {
            writer.nullValue();
        } else {
            writer.value(blockface.toString());
        }
    }

    /**
     * Reads a BlockFace from json into a BlockFace in the game world
     * @param reader the json reader
     * @return the BlockFace stored in json format
     * @throws IOException if there is a problem with the reader
     */
    @Override
    public BlockFace read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            return null;
        } else {
            return BlockFace.valueOf(reader.nextString());
        }
    }
}
