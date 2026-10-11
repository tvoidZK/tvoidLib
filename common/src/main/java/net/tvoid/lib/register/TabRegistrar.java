package net.tvoid.lib.register;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.*;
import java.util.function.Supplier;

public class TabRegistrar {
    private final String modId;
    private final DeferredRegister<CreativeModeTab> tabs;
    private final Map<RegistrySupplier<CreativeModeTab>, List<Supplier<? extends ItemLike>>> entries = new HashMap<>();
    private final Map<RegistrySupplier<CreativeModeTab>, List<Supplier<? extends ItemStack>>> stacks = new HashMap<>();

    public TabRegistrar(String modId) {
        this.modId = modId;
        this.tabs = DeferredRegister.create(modId, Registries.CREATIVE_MODE_TAB);
    }

    public void register() {
        tabs.register();
    }

    public RegistrySupplier<CreativeModeTab> create(String name, Supplier<ItemStack> icon) {
        List<Supplier<? extends ItemLike>> list = new ArrayList<>();
        List<Supplier<? extends ItemStack>> extra = new ArrayList<>();
        RegistrySupplier<CreativeModeTab> tab = tabs.register(name, () ->
                CreativeTabRegistry.create(b -> b
                        .title(Component.translatable("category." + modId + "." + name))
                        .icon(icon)
                        .displayItems((params, output) -> {
                                list.forEach(s -> output.accept(s.get()));
                                extra.forEach(s -> output.accept(s.get()));})));
        entries.put(tab, list);
        stacks.put(tab, extra);
        return tab;
    }

    public <T extends ItemLike> Supplier<T> add(RegistrySupplier<CreativeModeTab> tab, Supplier<T> item) {
        entries.get(tab).add(item);
        return item;
    }

    public void addStack(RegistrySupplier<CreativeModeTab> tab, Supplier<ItemStack> stack) {
        stacks.get(tab).add(stack);
    }
}
