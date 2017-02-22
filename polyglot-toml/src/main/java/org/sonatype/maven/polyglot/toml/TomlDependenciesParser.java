package org.sonatype.maven.polyglot.toml;

import com.moandjiezana.toml.Toml;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.sonatype.maven.polyglot.toml.TomlReaderUtils.parseTablesToList;

class TomlDependenciesParser {
    List<Dependency> parseDependencies(final Toml dependenciesTable) {
        List<Dependency> dependencies = new ArrayList<>();

        for (Map.Entry<String, Object> entry: dependenciesTable.entrySet()) {
            Dependency dependency = parseDependencyKey(entry.getKey());
            if (dependenciesTable.containsTable(entry.getKey())) {
                dependencies.add(parseDependencyTable(dependenciesTable.getTable(entry.getKey()), dependency));
            } else {
                dependency.setVersion(dependenciesTable.getString(entry.getKey()));
            }
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private Dependency parseDependencyKey(final String dependencyKey) {
        //FIXME what if its not a TOML string?
        String[] splitKey = stripQuotes(dependencyKey).split(":");
        Dependency dependency = new Dependency();
        dependency.setGroupId(splitKey[0]);
        dependency.setArtifactId(splitKey[1]);
        return dependency;
    }

    private String stripQuotes(final String value) {
        return value.substring(value.indexOf('"') + 1, value.lastIndexOf('"'));
    }

    private Dependency parseDependencyTable(final Toml dependencyTable, final Dependency dependency) {
        dependency.setVersion(dependencyTable.getString("version"));
        dependency.setType(dependencyTable.getString("type"));
        dependency.setClassifier(dependencyTable.getString("classifier"));
        dependency.setScope(dependencyTable.getString("scope"));
        dependency.setSystemPath(dependencyTable.getString("systemPath"));
        dependency.setExclusions(parseExclusions(dependencyTable.getTables("exclusions")));
        dependency.setOptional(dependencyTable.getString("optional"));
        return dependency;
    }

    private List<Exclusion> parseExclusions(final List<Toml> exclusionTables) {
        return parseTablesToList(exclusionTables, Exclusion.class);
    }
}
