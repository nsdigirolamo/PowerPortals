package io.github.nsdigirolamo.powerportals.utilities.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.UUID;

/**
 * Adapts Locations for conversion to and from json files by Gson
 */
public class LocationAdapter extends TypeAdapter<Location> {

    /**
     * Writes a Location into a format that can be stored in a json file
     * @param writer the json writer
     * @param location the Java object to write. May be null.
     * @throws IOException if there is a problem with the writer
     */
    @Override
    public void write(JsonWriter writer, Location location) throws IOException {
        if (location == null || location.getWorld() == null) {
            writer.nullValue();
        } else {
            String world = location.getWorld().getUID().toString();
            String x = "" + location.getX();
            String y = "" + location.getY();
            String z = "" + location.getZ();
            String yaw = "" + location.getYaw();
            String pitch = "" + location.getPitch();
            writer.value(world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);
        }
    }

    /**
     * Reads a Location from json into a Location in the game world
     * @param reader the json reader
     * @return the Location stored in json format
     * @throws IOException if there is a problem with the reader
     */
    @Override
    public Location read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        } else {
            String[] parts = reader.nextString().split(",");
            World world = Bukkit.getWorld(UUID.fromString(parts[0]));
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            if (world != null) {
                return new Location(world, x, y, z, yaw, pitch);
            } else {
                return null;
            }
        }
    }
}
