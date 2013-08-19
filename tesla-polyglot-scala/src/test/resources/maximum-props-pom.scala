import org.sonatype.maven.polyglot.scala.model._

Model(
  "io.tesla.polyglot" % "tesla-polyglot" % "0.0.1-SNAPSHOT",
  packaging = "war",
  name = "somename",
  description = "somedesc",
  url = "someurl",
  prerequisites = Prerequisites(
    maven = "3.0"
  ),
  issueManagement = IssueManagement(
    system = "someSystem",
    url = "someUrl"
  ),
  ciManagement = CiManagement(
    notifiers = Seq(
      Notifier(
        address = "someAddress",
        configuration = Map(
          "someKey" -> "someValue"
        ),
        sendOnError = false,
        sendOnFailure = false,
        sendOnSuccess = false,
        sendOnWarning = false,
        `type` = "someType"
      )
    ),
    system = "someSystem",
    url = "someUrl"
  ),
  inceptionYear = "1988",
  mailingLists = Seq(
    MailingList(
      name = "someName",
      subscribe = "someSubscribe",
      unsubscribe = "someUnsubscribe",
      post = "somePost",
      archive = "someArchive",
      otherArchives = Seq(
        "someOtherArchive"
      )
    )
  ),
  developers = Seq(
    Developer(
      id = "someId",
      email = "someEmail",
      name = "someName",
      organization = "someOrganization",
      organizationUrl = "someOrganizationUrl",
      roles = Seq(
        "someRole"
      ),
      timezone = "someTimezone",
      url = "someUrl"
    )
  ),
  contributors = Seq(
    Contributor(
      email = "someEmail",
      name = "someName",
      organization = "someOrganization",
      organizationUrl = "someOrganizationUrl",
      roles = Seq(
        "someRole"
      ),
      timezone = "someTimezone",
      url = "someUrl"
    )
  ),
  licenses = Seq(
    License(
      name = "someName",
      url = "someUrl",
      distribution = "someDistribution",
      comments = "someComments"
    )
  ),
  organization = Organization(
    name = "someName",
    url = "someUrl"
  ),
  parent = Parent(
    gav = "someGroupId" % "someArtifactId" % "someVersion",
    relativePath = "someRelativePath"
  ),
  modules = Seq(
    "someModule"
  ),
  dependencies = Seq(
    Dependency(
      "someGroupId" % "someArtifactId" % "someVersion",
      `type` = "someType",
      classifier = "someClassifier",
      scope = "someScope",
      systemPath = "someSystemPath",
      exclusions = Seq(
        "someGroupId" % "someArtifactId"
      ),
      optional = true
    )
  ),
  dependencyManagement = DependencyManagement(
    dependencies = Seq(
      Dependency(
        "someGroupId" % "someArtifactId" % "someVersion",
        `type` = "someType",
        classifier = "someClassifier",
        scope = "someScope",
        systemPath = "someSystemPath",
        exclusions = Seq(
          "someGroupId" % "someArtifactId"
        ),
        optional = true
      )
    )
  ),
  distributionManagement = DistributionManagement(
    repository = DeploymentRepository(
      uniqueVersion = false,
      releases = RepositoryPolicy(
        enabled = false,
        updatePolicy = "someUpdatePolicy",
        checksumPolicy = "someChecksumPolicy"
      ),
      snapshots = RepositoryPolicy(
        enabled = false,
        updatePolicy = "someUpdatePolicy",
        checksumPolicy = "someChecksumPolicy"
      ),
      id = "someId",
      name = "someName",
      url = "someUrl",
      layout = "someLayout"
    ),
    snapshotRepository = DeploymentRepository(
      uniqueVersion = false,
      releases = RepositoryPolicy(
        enabled = false,
        updatePolicy = "someUpdatePolicy",
        checksumPolicy = "someChecksumPolicy"
      ),
      snapshots = RepositoryPolicy(
        enabled = false,
        updatePolicy = "someUpdatePolicy",
        checksumPolicy = "someChecksumPolicy"
      ),
      id = "someId",
      name = "someName",
      url = "someUrl",
      layout = "someLayout"
    ),
    site = Site(
      id = "someId",
      name = "someName",
      url = "someUrl"
    ),
    downloadUrl = "someDownloadUrl",
    relocation = Relocation(
      groupId = "someGroupId",
      artifactId = "someArtifactId",
      version = "someVersion",
      message = "someMessage"
    ),
    status = "someStatus"
  ),
  build = Build(
    sourceDirectory = "someSourceDirectory",
    scriptSourceDirectory = "someScriptSourceDirectory",
    testSourceDirectory = "someTestSourceDirectory",
    outputDirectory = "someOutputDirectory",
    testOutputDirectory = "someTestOutputDirectory",
    extensions = Seq(
      Extension(
        "someGroupId" % "someArtifactId" % "someVersion"
      )
    ),
    defaultGoal = "someDefaultGoal",
    resources = Seq(
      Resource(
        targetPath = "someTargetPath",
        filtering = true,
        directory = "someDirectory",
        includes = Seq(
          "someInclude"
        ),
        excludes = Seq(
          "someExclude"
        )
      )
    ),
    testResources = Seq(
      Resource(
        targetPath = "someTargetPath",
        filtering = true,
        directory = "someDirectory",
        includes = Seq(
          "someInclude"
        ),
        excludes = Seq(
          "someExclude"
        )
      )
    ),
    directory = "someDirectory",
    finalName = "someFinalName",
    filters = Seq(
      "someFilter"
    ),
    pluginManagement = PluginManagement(
      plugins = Seq(
        Plugin(
          "someGroupId" % "someArtifactId" % "someVersion",
          extensions = true,
          executions = Seq(
            Execution(
              id = "someId",
              phase = "somePhase",
              goals = Seq(
                "someGoal"
              ),
              inherited = false,
              configuration =
                <someconfiguration>value</someconfiguration>
            )
          ),
          dependencies = Seq(
            Dependency(
              "someGroupId" % "someArtifactId" % "someVersion",
              `type` = "someType",
              classifier = "someClassifier",
              scope = "someScope",
              systemPath = "someSystemPath",
              exclusions = Seq(
                "someGroupId" % "someArtifactId"
              ),
              optional = true
            )
          ),
          inherited = false,
          configuration =
            <someconfiguration>value</someconfiguration>
        )
      )
    ),
    plugins = Seq(
      Plugin(
        "someGroupId" % "someArtifactId" % "someVersion",
        extensions = true,
        executions = Seq(
          Execution(
            id = "someId",
            phase = "somePhase",
            goals = Seq(
              "someGoal"
            ),
            inherited = false,
            configuration =
              <someconfiguration>value</someconfiguration>
          )
        ),
        dependencies = Seq(
          Dependency(
            "someGroupId" % "someArtifactId" % "someVersion",
            `type` = "someType",
            classifier = "someClassifier",
            scope = "someScope",
            systemPath = "someSystemPath",
            exclusions = Seq(
              "someGroupId" % "someArtifactId"
            ),
            optional = true
          )
        ),
        inherited = false,
        configuration =
          <someconfiguration>value</someconfiguration>
      )
    )
  ),
  modelEncoding = "UTF-16",
  modelVersion = "4.0.1"
)