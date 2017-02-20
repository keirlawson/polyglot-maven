package org.sonatype.maven.polyglot.toml;

import com.moandjiezana.toml.Toml;
import org.apache.maven.model.*;
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

    Toml toml = new Toml().read(input);
    return parseProject(toml);
  }

  private Model parseProject(Toml projectToml) {
    Model m = parseBasicInfo(projectToml);
    parseProperties(projectToml, m);
    parseDevelopers(projectToml, m);
    parseDependencies(projectToml, m);
    parseContributors(projectToml, m);
    parseModules(projectToml, m);
    parseBuild(projectToml, m);
    return m;
  }

  /*
parent
url
inceptionYear
organization
licenses/license*
developers/developer*
contributors/contributor*
mailingLists/mailingList*
prerequisites
modules/module*
scm
issueManagement
ciManagement
distributionManagement
properties/key=value*
dependencyManagement
dependencies/dependency*
repositories/repository*
pluginRepositories/pluginRepository*
build
reports
reporting
profiles/profile*
   */

  private Model parseDependencies(final Toml pom, final Model model) {
    Toml dependenciesTable = pom.getTable("dependencies");
    for (Entry<String, Object> entry: dependenciesTable.entrySet()) {
      Dependency dependency = parseDependencyKey(entry.getKey());
      if (dependenciesTable.containsTable(entry.getKey())) {
      } else {
        dependency.setVersion(dependenciesTable.getString(entry.getKey()));
      }
      model.addDependency(dependency);
    }
    return model;
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

  // FIXME: 2/20/17 neither this nor contributors has properties support
  private Model parseDevelopers(final Toml pom, final Model model) {
    final List<Developer> developers = parseTableToList(pom, "developers", Developer.class);
    model.setDevelopers(developers);
    return model;
  }

  private Model parseContributors(final Toml pom, final Model model) {
    final List<Contributor> contributors = parseTableToList(pom, "contributors", Contributor.class);
    model.setContributors(contributors);
    return model;
  }

  private <T> List<T> parseTableToList(final Toml pom, final String key, final Class<T> clazz) {
    final List<T> list = new ArrayList<>();
    List<Toml> developerTables = pom.getTables(key);
    for (Toml developerTable : developerTables) {
      list.add(developerTable.to(clazz));
    }
    return list;
  }

  private Model parseModules(final Toml pom, final Model model) {
    List<String> modules = pom.getList("modules");
    model.setModules(modules);
    return model;
  }

  private Model parseProperties(final Toml pom, final Model model) {
    final Properties properties = new Properties();
    final Toml propertiesTable = pom.getTable("properties");
    for (Map.Entry<String, Object> entry : propertiesTable.entrySet()) {
      properties.setProperty(entry.getKey(), propertiesTable.getString(entry.getKey()));
    }
    model.setProperties(properties);
    return model;
  }

  private Model parseBuild(final Toml pom, final Model model) {
    parseBuildPlugins(pom.getTables("plugins"), model);
    return model;
  }

  private Model parseBuildPlugins(final List<Toml> pluginTomls, final Model model) {
    
    return model;
  }

  private PluginExecution parseExecution(final Toml executionTable) {
    PluginExecution pluginExecution = new PluginExecution();
    pluginExecution.setId(executionTable.getString("id"));
    pluginExecution.setPhase(executionTable.getString("phase"));
    pluginExecution.setInherited(executionTable.getString("inherited"));
    pluginExecution.setGoals((List<String>) executionTable.getList());
    pluginExecution.setConfiguration(executionTable.getTable("configuration"));//FIXME this probably doesn't work
    return pluginExecution;
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
