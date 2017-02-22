package org.sonatype.maven.polyglot.toml;

import com.moandjiezana.toml.Toml;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;

import java.util.ArrayList;
import java.util.List;

public class TomlBuildParser {

    public Build parseBuild(final Toml pom) {
        Build build = new Build();
        build.setPlugins(parseBuildPlugins(pom.getTables("plugins")));
        return build;
    }

    private List<Plugin> parseBuildPlugins(final List<Toml> pluginTomls) {
        List<Plugin> plugins = new ArrayList<>();
//    for (Toml pluginToml : pluginTomls) { FIXME
//      plugins.add(parsePlugin(pluginToml));
//    }
        return plugins;
    }

    private Plugin parsePlugin(final Toml pluginToml) {
        Plugin plugin = new Plugin();
        // FIXME: 21/02/17 implement
        return plugin;
    }

    private PluginExecution parseExecution(final Toml executionTable) {
        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.setId(executionTable.getString("id"));
        pluginExecution.setPhase(executionTable.getString("phase"));
        pluginExecution.setInherited(executionTable.getString("inherited"));
        //pluginExecution.setGoals((List<String>) executionTable.getList()); FIXME
        pluginExecution.setConfiguration(executionTable.getTable("configuration"));//FIXME this probably doesn't work
        return pluginExecution;
    }
}
