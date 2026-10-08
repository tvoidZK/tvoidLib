package net.tvoid.lib.neoforge.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.tvoid.lib.data.gen.LangGen;

import java.nio.file.Path;

public class ModLangProvider extends LanguageProvider {
    private final PackOutput output;
    private final String modId;
    private final String locale;

    public ModLangProvider(PackOutput output, String modId, String locale) {
        super(output, modId, locale);
        this.output = output;
        this.modId = modId;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        Path file = output.getOutputFolder()
                .resolve("assets/" + modId + "/lang/" + locale + ".json");
        LangGen.gen(modId, LangGen.load(file), this::add);
    }
}