package org.sonatype.maven.polyglot.toml;

import com.moandjiezana.toml.Toml;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.sonatype.maven.polyglot.io.ModelReaderSupport;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

@Component(role = ModelReader.class, hint = "toml")
public class TomlModelReader extends ModelReaderSupport {

  public TomlModelReader() {
  }

  public Model read(Reader input, Map<String, ?> options) throws IOException, ModelParseException {
    if (input == null) {
      throw new IllegalArgumentException("TOML Reader is null.");
    }

    //FIXME do parse here
    //return (Model) yaml.load(input);
    Toml toml = new Toml().read(input);
    Model m = parseBasicInfo(toml);
    parseProperties(toml, m);
    parseDevelopers(toml, m);
    Toml dependenciesTable = toml.getTable("dependencies");
    for (Entry<String, Object> entry: dependenciesTable.entrySet()) {
      System.out.println(entry.getKey());
      Dependency dependency = parseDependencyKey(entry.getKey());
      if (dependenciesTable.containsTable(entry.getKey())) {
        System.out.println(entry.getKey());
      } else {
        dependency.setVersion(dependenciesTable.getString(entry.getKey()));
      }
      m.addDependency(dependency);
    }

    return m;
  }

  private Model parseBasicInfo(final Toml pom) {
    Model model = new Model();
    model.setGroupId(pom.getString("groupId"));
    model.setArtifactId(pom.getString("artifactId"));
    model.setVersion(pom.getString("version"));
    model.setPackaging(pom.getString("packaging"));
    model.setName(pom.getString("name"));
    model.setDescription(pom.getString("description"));
    return model;
  }

  private Model parseDevelopers(final Toml pom, final Model model) {
    final List<Developer> developers = new ArrayList<>();
    List<Object> developerTables = pom.getList("developers");
//    for (Toml developerTable : developerTables) {
//      developers.add(parseDeveloper(developerTable));
//    }
    model.setDevelopers(developerTables);
    return model;
  }

  private Developer parseDeveloper(final Toml developerToml) {
    Developer developer = new Developer();
    developer.setId(developerToml.getString("id"));
    developer.setName(developerToml.getString("name"));
    //FIXME fill in more here
    return developer;
  }

  private Model parseContributors(final Toml pom, final Model model) {
    return model;
  }

  private Model parseProperties(final Toml pom, final Model model) {
//    final Properties properties = new Properties();
//    final Toml propertiesTable = pom.getTable("properties");
//    for (Map.Entry<String, Object> entry : propertiesTable.entrySet()) {
//      properties.setProperty(entry.getKey(), propertiesTable.getString(entry.getKey()));
//    }
//    model.setProperties(properties);
    return model;
  }

  private Dependency parseDependencyKey(final String dependencyKey) {
    //FIXME what if its not a TOML string?
    String[] splitKey = stripQuotes(dependencyKey).split(":");
    Dependency dependency = new Dependency();
    dependency.setGroupId(splitKey[0]);
    dependency.setArtifactId(splitKey[1]);
    return dependency;
  }

  private boolean isQuotedKey(final String key) {
    return key.startsWith("\"") && key.endsWith("\"");
  }

  private String stripQuotes(final String value) {
    return value.substring(value.indexOf("\"") + 1, value.lastIndexOf("\""));
  }
}
