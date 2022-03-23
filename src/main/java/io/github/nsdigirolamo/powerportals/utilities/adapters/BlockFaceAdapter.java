package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.block.BlockFace;

import java.io.IOException;

public class BlockFaceAdapter extends TypeAdapter<BlockFace> {

    @Override
    public void write(JsonWriter writer, BlockFace blockface) throws IOException {
        if (blockface == null) {
            writer.nullValue();
        } else {
            writer.value(blockface.toString());
        }
    }

    @Override
    public BlockFace read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            return null;
        } else {
            return BlockFace.valueOf(reader.nextString());
        }
    }
}
