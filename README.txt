# maven
  The Maven zucred AG Repo

## Add the following for to the library
### For bitbucket
  apply from: 'https://raw.githubusercontent.com/zucred/maven/master/publish-bitbucket.gradle'
### For github
  apply from: 'https://raw.githubusercontent.com/zucred/maven/master/publish-github.gradle'

## Create a gradle.properties file within your library folder with the following parameters:
  ARTIFACT_VERSION=<version_here>
  ARTIFACT_NAME=<libraryname_here>
  ARTIFACT_PACKAGE=<packagename_here>
  ARTIFACT_PACKAGING=aar //You could also use jar

  COMPANY=<bitbucket_team_company_here> //Username if not part of team
  REPOSITORY_NAME=<git_reponame_here>
  REPOSITORY_URL=<git_url_here> // 'raw.github.com' or 'raw.git.sonova.com' or 'api.bitbucket.org/1.0/repositories'

## Create a gradle.properties file in the root of your project (or better in the global .gradle folder on your system) with the following parameters
  USERNAME=<username_here>
  PASSWORD=<password_here>

## Run 
  ./gradlew uploadArchives
