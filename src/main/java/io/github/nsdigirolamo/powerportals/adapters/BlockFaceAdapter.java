package io.github.nsdigirolamo.powerportals.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.block.BlockFace;

import java.io.IOException;

/**
 * BlockFaceAdapter adapts bukkit BlockFace enums so Gson can read/write them.
 */
public class BlockFaceAdapter extends TypeAdapter<BlockFace> {

    @Override
    public void write(JsonWriter writer, BlockFace blockFace) throws IOException {
        if (blockFace == null) {
            writer.nullValue();
            return;
        }
        writer.value(blockFace.toString());
    }

    @Override
    public BlockFace read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return BlockFace.valueOf(reader.nextString());
    }
}
