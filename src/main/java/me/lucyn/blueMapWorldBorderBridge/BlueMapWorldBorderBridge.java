package me.lucyn.blueMapWorldBorderBridge;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.ShapeMarker;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public final class BlueMapWorldBorderBridge extends JavaPlugin {


    private Border border;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Optional<BlueMapAPI> api = BlueMapAPI.getInstance();
        Plugin worldBorder = getServer().getPluginManager().getPlugin("WorldBorder");

        if (!api.isPresent()) {
            getServer().getLogger().info("BlueMap API not detected. Disabling bridge plugin.");
            this.setEnabled(false);
            return;
        }

        if (!worldBorder.isEnabled()) {
            getServer().getLogger().info("WorldBorder not detected. Disabling bridge plugin.");
            this.setEnabled(false);
            return;
        }

        FileConfiguration wbConfig = worldBorder.getConfig();


        Properties props = new Properties();
        try {
            props.load(new FileInputStream("server.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String name = props.getProperty("level-name");




        Shape borderShape = Shape.createRect(border.x1, border.z1, border.x2, border.z2);
        ShapeMarker borderMarker = ShapeMarker.builder()
                .shape(borderShape, 0)
                .lineColor(new Color(255, 0, 0))
                .depthTestEnabled(false)
                .build();

        MarkerSet markerSet = MarkerSet.builder().label("World Border").build();
        markerSet.getMarkers().put("world-border", borderMarker);
        api.get().getWorld(getServer().getWorld(name)).ifPresent(world ->{
            for(BlueMapMap map : world.getMaps()) {
                map.getMarkerSets().put("world-border-bridge", markerSet);

            }


        });





    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }






}
