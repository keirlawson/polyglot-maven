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

import static org.sonatype.maven.polyglot.toml.TomlReaderUtils.parseTablesToList;

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
    TomlDependenciesParser tomlDependenciesParser = new TomlDependenciesParser();
    TomlBuildParser tomlBuildParser = new TomlBuildParser();

    Model m = parseBasicInfo(projectToml);

    m.setOrganization(parseOrganization(projectToml.getTable("organization")));
    m.setLicenses(parseLicenses(projectToml.getTables("licenses")));
    m.setProperties(parseProperties(projectToml.getTable("properties")));
    parseDevelopers(projectToml, m);
    parseContributors(projectToml, m);
    parseModules(projectToml, m);
    m.setScm(parseScm(projectToml.getTable("scm")));
    m.setIssueManagement(parseIssueManagement(projectToml.getTable("issueManagement")));

    List<Dependency> dependencies = tomlDependenciesParser.parseDependencies(projectToml.getTable("dependencies"));
    m.setDependencies(dependencies);

    m.setBuild(tomlBuildParser.parseBuild(projectToml.getTable("build")));
    return m;
  }

  /*
parent
developers/developer*
contributors/contributor*
mailingLists/mailingList*
prerequisites
modules/module*
ciManagement
distributionManagement
properties/key=value*
dependencyManagement
dependencies/dependency*
repositories/repository*
pluginRepositories/pluginRepository*
build
reporting
profiles/profile*
   */

  private Model parseBasicInfo(final Toml pom) {
    Model model = new Model();
    model.setGroupId(pom.getString("groupId"));
    model.setArtifactId(pom.getString("artifactId"));
    model.setVersion(pom.getString("version"));
    model.setPackaging(pom.getString("packaging"));
    model.setName(pom.getString("name"));
    model.setDescription(pom.getString("description"));
    model.setUrl(pom.getString("url"));
    model.setInceptionYear(pom.getString("inceptionYear"));
    return model;
  }

  private Organization parseOrganization(final Toml organisationTable) {
    if (organisationTable == null)
      return null;

    Organization organization = new Organization();
    organization.setName(organisationTable.getString("name"));
    organization.setUrl(organisationTable.getString("url"));
    return organization;
  }

  private List<License> parseLicenses(final List<Toml> licenceTables) {
    return parseTablesToList(licenceTables, License.class);
  }

  private Scm parseScm(final Toml scmTable) {
    return scmTable != null ? scmTable.to(Scm.class) : null;
  }

  private IssueManagement parseIssueManagement(final Toml issueManagementTable) {
    return issueManagementTable.to(IssueManagement.class);
  }

  // FIXME: 2/20/17 neither this nor contributors has properties support
  private Model parseDevelopers(final Toml pom, final Model model) {
    final List<Developer> developers = parseTablesToList(pom.getTables("developers"), Developer.class);
    model.setDevelopers(developers);
    return model;
  }

  private Model parseContributors(final Toml pom, final Model model) {
    final List<Contributor> contributors = parseTablesToList(pom.getTables("contributors"), Contributor.class);
    model.setContributors(contributors);
    return model;
  }

  private Model parseModules(final Toml pom, final Model model) {
    List<String> modules = pom.getList("modules");

    model.setModules(modules);
    return model;
  }

  private Properties parseProperties(final Toml propertiesTable) {
    final Properties properties = new Properties();

    if (propertiesTable == null)
      return properties;

    for (Map.Entry<String, Object> entry : propertiesTable.entrySet()) {
      properties.setProperty(entry.getKey(), propertiesTable.getString(entry.getKey()));
    }

    return properties;
  }
}
