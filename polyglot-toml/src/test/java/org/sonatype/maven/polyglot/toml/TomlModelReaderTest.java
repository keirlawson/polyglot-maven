package org.sonatype.maven.polyglot.toml;

import org.apache.maven.model.*;
import org.apache.maven.model.io.ModelWriter;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

public class TomlModelReaderTest {
//  @Test
//  public void testModelCloning() throws Exception {
//    getModel().clone();
//  }


  @Test
  public void testEmptyFileParsing() throws Exception {
    Model model = getModel("empty.toml");
    assertNotNull(model);
  }

  @Test
  public void testDependeciesParsing() throws Exception {
    Model model = getModel("dependencyWithExclusions.toml");
    assertNotNull(model);

    assertEquals(1, model.getDependencies().size());
    Dependency dependency = model.getDependencies().get(0);
    assertEquals("1.6.5", dependency.getVersion());
    assertEquals(4, dependency.getExclusions().size());
  }

  @Test
  public void testModelReader() throws Exception {
    Model model = getModel("test.toml");
    assertNotNull(model);

//    Parent parent = model.getParent();
//    assertEquals("org.sonatype.maven", parent.getGroupId());
//    assertEquals("maven-parent", parent.getArtifactId());
//    assertEquals("1.0", parent.getVersion());
//
    assertEquals("org.sonatype.raven", model.getGroupId());
    assertEquals("raven", model.getArtifactId());
    assertEquals("1.0-SNAPSHOT", model.getVersion());
    assertEquals("Raven", model.getName());

    // Developers
    List<Developer> developers = model.getDevelopers();
    Developer dev0 = developers.get(0);
    assertEquals("jvanzyl", dev0.getId());
    assertEquals("Jason van Zyl", dev0.getName());

    // Contributors
    List<Contributor> contributors = model.getContributors();
    Contributor con0 = contributors.get(0);
    assertEquals("Will Price", con0.getName());

    // Organization
    assertEquals("somename", model.getOrganization().getName());
    assertEquals("someurl", model.getOrganization().getUrl());

    // Licenses
    List<License> licenses = model.getLicenses();
    assertEquals(2, licenses.size());

    License lic0 = licenses.get(0);
    assertEquals("somelicense", lic0.getName());
    assertEquals("someurl", lic0.getUrl());

    License lic1 = licenses.get(1);
    assertEquals("anotherlicense", lic1.getName());

    // IssueManagement

    IssueManagement issueManagement = model.getIssueManagement();
    assertEquals("Bugzilla", issueManagement.getSystem());

    // SCM
    Scm scm = model.getScm();
    assertEquals("sometag", scm.getTag());

//    // DependencyManagement
//    List<Dependency> depManDependencies = model.getDependencyManagement().getDependencies();
//    assertEquals(2, depManDependencies.size());
//
//    Dependency dmd0 = depManDependencies.get(0);
//    assertEquals("org.apache.maven", dmd0.getGroupId());
//    assertEquals("maven-model-builder", dmd0.getArtifactId());
//    assertEquals("3.0", dmd0.getVersion());
//
//    Dependency dmd1 = depManDependencies.get(1);
//    assertEquals("org.toml", dmd1.getGroupId());
//    assertEquals("snakeyaml", dmd1.getArtifactId());
//    assertEquals("1.4", dmd1.getVersion());
//
    // Dependencies
    List<Dependency> dependencies = model.getDependencies();
    assertEquals(3, dependencies.size());//FIXME should be 3 with groovy

//    Dependency mavenDependecy= new Dependency();
//    mavenDependecy.setGroupId("org.apache.maven");
//    mavenDependecy.setArtifactId("maven-core");
//    mavenDependecy.setVersion("3.0");
//    assertThat(dependencies).contains(mavenDependecy);
//    assertThat(dependencies).contains()

    Dependency d0 = dependencies.get(0);
    assertEquals("org.apache.maven", d0.getGroupId());
    assertEquals("maven-core", d0.getArtifactId());
    assertEquals("3.0", d0.getVersion());

//    Dependency d1 = dependencies.get(1);
//    assertEquals("com.moandjiezana.toml", d1.getGroupId());
//    assertEquals("toml4j", d1.getArtifactId());
//    assertEquals("0.7.1", d1.getVersion());
//
//    Dependency d2 = dependencies.get(2);
//    assertEquals("org.codehaus.groovy", d2.getGroupId());
//    assertEquals("groovy", d2.getArtifactId());
//    assertEquals("1.6.5", d2.getVersion());

    // Modules
    List<String> modules = model.getModules();
    assertEquals("reader", modules.get(0));
    assertEquals("writer", modules.get(1));
    assertEquals("shell-integration", modules.get(2));

//    // Build
//    Build build = model.getBuild();
//    List<Plugin> plugins = build.getPlugins();
//    assertEquals(1, plugins.size());
//    Plugin p0 = plugins.get(0);
//    assertEquals("org.apache.maven.plugins", p0.getGroupId());
//    assertEquals("maven-compiler-plugin", p0.getArtifactId());
//    assertEquals("2.0.2", p0.getVersion());
//    Xpp3Dom configuration = (Xpp3Dom) p0.getConfiguration();
//    assertNotNull(configuration);
//    assertEquals(2, configuration.getChildCount());
//    assertEquals("1.6", configuration.getChild("source").getValue());
//    assertEquals("1.5", configuration.getChild("target").getValue());
//
//    // DistributionManagement
//    DistributionManagement distMan = model.getDistributionManagement();
//    Site site = distMan.getSite();
//    assertEquals("site", site.getId());
//    assertEquals("http://www.apache.org", site.getUrl());
//    DeploymentRepository releases = distMan.getRepository();
//    assertEquals("releases", releases.getId());
//    assertEquals("releases", releases.getName());
//    assertEquals("http://maven.sonatype.org/releases", releases.getUrl());
//    DeploymentRepository snapshots = distMan.getSnapshotRepository();
//    assertEquals("snapshots", snapshots.getId());
//    assertEquals("snapshots", snapshots.getName());
//    assertEquals("http://maven.sonatype.org/snapshots", snapshots.getUrl());
//
//    // SCM
//    Scm scm = model.getScm();
//    assertEquals("connection", scm.getConnection());
//    assertEquals("developerConnection", scm.getDeveloperConnection());
//    assertEquals("http://maven.sonatype.org/scm", scm.getUrl());
//
//    // IssueManagement
//    IssueManagement issueManagement = model.getIssueManagement();
//    assertEquals("jira", issueManagement.getSystem());
//    assertEquals("http://jira.codehaus.org/browse/MNG", issueManagement.getUrl());
//
//    // CiManagement
//    CiManagement ciManagement = model.getCiManagement();
//    assertEquals("hudson", ciManagement.getSystem());
//    assertEquals("http://grid.sonatype.org/ci", ciManagement.getUrl());

    // Profiles
  }

//  @Test
//  public void testModelWriter() throws Exception {
//    StringWriter sw = new StringWriter();
//    ModelWriter writer = new YamlModelWriter();
//    Model model = getModel();
//    Properties p = new Properties();
//    p.setProperty("FOO", "BAR");
//    model.setProperties(p);
//    writer.write(sw, null, model);
//    String output = sw.toString();
//    //System.out.println(output);
//    assertTrue(output, output.contains("\nproperties: {FOO: BAR}\n"));
//    assertFalse(output, output.contains("extensions: []"));
//    assertFalse(output, output.contains("properties: {}"));
//  }

  protected Model getModel(String filePath) throws Exception {
    TomlModelReader modelReader = new TomlModelReader();
    URL url = getClass().getResource(filePath);
    assertNotNull(url);
    InputStream reader = url.openStream();
    return modelReader.read(reader, null);
  }
}
