package net.tvoid.lib.neoforge.data.gen.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class LangGen {
    private LangGen() {
    }

    public static String prettify(String path) {
        StringBuilder sb = new StringBuilder();
        for (String word : path.split("[_/]")) {
            if (word.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return sb.toString();
    }

    public static void gen(String modId, Map<String, String> existing,
                           BiConsumer<String, String> out) {
        Map<String, String> entries = new TreeMap<>();

        for (Identifier id : BuiltInRegistries.ITEM.keySet()) {
            if (!id.getNamespace().equals(modId)) continue;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            entries.put(item.getDescriptionId(), prettify(id.getPath()));
        }
        for (Identifier id : BuiltInRegistries.BLOCK.keySet()) {
            if (!id.getNamespace().equals(modId)) continue;
            Block block = BuiltInRegistries.BLOCK.getValue(id);
            entries.put(block.getDescriptionId(), prettify(id.getPath()));
        }
        for (Identifier id : BuiltInRegistries.CREATIVE_MODE_TAB.keySet()) {
            if (!id.getNamespace().equals(modId)) continue;
            entries.put("category." + modId + "." + id.getPath(), prettify(id.getPath()));
        }

        entries.putAll(existing);
        entries.forEach(out);
    }

    public static Map<String, String> load(Path file) {
        if (!Files.exists(file)) return Map.of();
        try (Reader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            JsonObject obj = JsonParser.parseReader(r).getAsJsonObject();
            Map<String, String> map = new TreeMap<>();
            obj.entrySet().forEach(e -> map.put(e.getKey(), e.getValue().getAsString()));
            return map;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
