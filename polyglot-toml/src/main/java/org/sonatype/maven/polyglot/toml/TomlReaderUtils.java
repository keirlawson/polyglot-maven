package org.sonatype.maven.polyglot.toml;

import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.List;

class TomlReaderUtils {
    static <T> List<T> parseTablesToList(final List<Toml> tables, final Class<T> clazz) {
        final List<T> list = new ArrayList<>();

        if (tables == null)
            return list;

        for (Toml table : tables) {
            list.add(table.to(clazz));
        }
        return list;
    }
}
