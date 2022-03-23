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

public class LocationAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter writer, Location location) throws IOException {
        if (location == null) {
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
