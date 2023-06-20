#!groovy

@Library('common-lib')
import nl.rhofman.jenkins.utils.domain.Destination

def appPrefix = "BSB"
def appPom = "pom.xml"
def earPom = "bsb-ear/pom.xml"
def jenkinsSettings = "bsb-maven-settings" // mavens settings file with Nexus URL and credentials

pipeline {
    agent {
        docker {
            image 'rhofman14/maven-agent:3.8.6-jdk11'
            args '-v $HOME/.m2:/home/jenkins/.m2'
/*            image 'maven:3.9.0-eclipse-temurin-11-alpine' */
/*            args '-v $HOME/.m2:/root/.m2' */
        }
    }

    environment {
        GIT_REVISION = sh(returnStdout: true, script: 'git rev-parse --short HEAD')
        GIT_AUTHOR = sh(returnStdout: true, script: 'git log -1 --pretty=%cn')
//        POM = readMavenPom file: appPom
//        VERSION = POM.getVersion().toLowerCase().replaceAll('-snapshot', '-' + GIT_REVISION)
//        VERSION = getVersion("GitFlow", appPrefix)
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Init') {
            steps {
                script {
                    auditTools message: "BookStore Backend"
                    initBuild pomLocation: appPom, versionStrategy: 'GitFlow', prefix: 'bsb', mavenSettingsFile: jenkinsSettings
                }
                sh '''
                    printenv
                    ls -l "$WORKSPACE"
                '''
            }
        }

        stage('Build') {
            when {
                anyOf {
                    branch 'release/*'
                    branch 'hotfix/*'
                    branch 'feature/*'
                    branch 'PR-*'
                }
            }
            steps {
                mavenBuild(pomLocation: appPom, arguments: 'clean package', mavenSettingsFile: jenkinsSettings)
            }
            post {
                always {
                    dir('**/target/surefire-reports') {
                        junit allowEmptyResults: true, testResults: '*.xml'
                    }
                }
            }
        }

        stage('Build for Deploy') {
            when { anyOf { branch 'master'; branch 'develop' }}
            environment {
                pomfile = "${appPom}".toString()
            }
            steps {
//                sh '''
//                    mvn -f ${pomfile} versions:set -DprocessAllModules -DnewVersion=${VERSION}
//                    mvn versions:commit -DprocessAllModules
//                '''
                mavenBuild(pomLocation: appPom, arguments: 'clean package', mavenSettingsFile: jenkinsSettings)
            }
            post {
                always {
                    dir('**/target/surefire-reports') {
                        junit allowEmptyResults: true, testResults: '*.xml'
                    }
                }
            }
        }

        stage('Create Release') {
            when {
                anyOf { branch 'release/*'; branch 'hotfix/*' }
            }
            steps {
                script {
                    releaseVersion = mavenRelease(pomlocation: appPom)
                    echo 'Release ${releaseVersion}'
                }
            }
        }

        stage('Delay for Release Deployment') {
            when { anyOf { branch 'release/*'}}
            steps {
                script {
                    def delay = load "jenkins/delay.groovy"
                    def delayUntil = delay.getDateTime("23:00")
                    echo 'Delay for deployment'
                    delay.sleepUntil(delayUntil, this)
                }
            }
        }

        stage('Publish') {
            steps {
                archiveArtifacts 'bsb-ear/target/*.ear'
                // Save artifact(s) in Nexus
                mavenPublish(
                        pomLocation: earPom,
                        packaging: 'ear',
                        repositoryId: 'nexus-local',
                        mavenSettingsFile: jenkinsSettings
                )
                /* or use 'mvn deploy' with withMaven or with configFileProvider (alternative for withMaven) */
//                configFileProvider(
//                        [configFile(fileId: jenkinsSettings, variable: 'MAVEN_GLOBAL_SETTINGS')]) {
//                    sh 'mvn -gs $MAVEN_GLOBAL_SETTINGS deploy'
//                }
            }
        }

        stage('Deploy') {
            when { anyOf { branch 'master'; branch 'develop' } }
            parallel {
                stage('Database') {
                    steps {
                        script {
                            deployToDb(
                                    artifactId: 'package-db-bsb',
                                    destination: new Destination(name: 'BSB-DB', stage: 'Develop'),
                                    credentials: 'BSB_USER_DB'
                            )
                        }
                    }
                }
                stage('Application') {
                    steps {
                        echo 'Echo Application'
                    }
                }
            }
            post {
                success {
                    notifyBuildResult(deployEnv: 'Develop', isSuccess: true)
                }
                failure {
                    notifyBuildResult(deployEnv: 'Develop', isSuccess: false)
                }
            }

        }

        stage('Deploy Release') {
            when { anyOf { branch 'release/*'}}
            parallel {
                stage('Database') {
                    steps {
                        script {
                            deployToDb(
                                    artifactId: 'package-db-bsb',
                                    destination: new Destination(name: 'BSB-DB', stage: 'Test'),
                                    releaseVersion: releaseVersion,
                                    credentials: 'BSB_USER_DB'
                            )
                        }
                    }
                }
                stage('Application') {
                    steps {
                        script {
                            deployToAppServer(
                                    artifactId: 'bsb-ear',
                                    destination: new Destination(name: 'BSB-APP', stage: 'Test'),
                                    releaseVersion: releaseVersion,
                                    credentials: 'BSB_USER_LIBERTY'
                            )
                        }
                    }
                }
            }
            post {
                success {
                    notifyBuildResult(deployEnv: 'Ontwikkel', isSuccess: true)
                }
                failure {
                    notifyBuildResult(deployEnv: 'Ontwikkel', isSuccess: false)
                }
            }

        }
    }
}

// VersionStrategy: GitFlow or FeatureBranch
//String getVersion(String versionStrategy = "GitFlow", String prefix, String pomLocation = "pom.xml") {
//    String strategy = versionStrategy.toUpperCase()
//    String branch = "${env.BRANCH_NAME}"  // e.g. feature/BSB-3454 (BSB-3454 could be a ticket in Jira) or develop or master or release/1.2.0
//
//    // Always use the tag if it's set
//    if (branch.contains('master') || branch.contains('main') || branch.contains('develop')) {
//        String lastTag = sh(returnStdout: true, script: "git describe --tags --abbrev=0").trim()
//        if (!lastTag.toUpperCase().contains("NO NAMES FOUND") && !lastTag.toUpperCase().contains("FATAL")) {
//            return lastTag // use last tag
//        }
//    }
//
//    // Use feature branching strategy
//    if (strategy == "FEATUREBRANCH") {
//        // Get version from pom.xml and remove '-SNAPSHOT'
//        String pomVersion = getPomVersion(pomLocation).replaceAll('-SNAPSHOT', "")
//        String prefixSep = "$prefix-"  // e.g. BSB-
//        // Get total commit count
//        if (branch.contains('master') || branch.contains('main') || branch.contains('develop')) {
//            String commitCount = sh(returnStdout: true, script: "git rev-list --all --count").trim()
//            return "${pomVersion}-${commitCount}"
//        }
//        if (branch.contains(prefixSep)) {
//            def ticketNo = branch.split(prefixSep)[1]
//            return "${pomVersion}-${ticketNo}-SNAPSHOT"
//        }
//        return "${pomVersion}-${branch}-SNAPSHOT"
//    }
//
//    if (strategy == "GITFLOW") {
//        if (branch.contains('release')) {
//            // release; e.g. release/1.2.0
//            return branch.replace('release/', '')
//        }
//
//        // Get version from pom.xml and remove '-SNAPSHOT'
//        String pomVersion = getPomVersion(pomLocation).replaceAll('-SNAPSHOT', "")
//        String prefixSep = "$prefix-"  // e.g. BSB-
//        if (branch.contains("feature") || branch.contains("hotfix")) {
//            if (branch.contains(prefixSep)) {
//                // Ticket for feature branches e.g. feature/BSB-4536
//                def ticketNo = branch.split(prefixSep)[1]
//                return "${pomVersion}-${ticketNo}-SNAPSHOT"
//            }
//            String branchName = branch
//            if (branch.contains("feature")) {
//                branchName = branch.replace('feature/', '')
//            }
//            if (branch.contains("hotfix")) {
//                branchName = branch.replace('hotfix/', '')
//            }
//            return "${pomVersion}-${branchName}-SNAPSHOT"
//        }
//
//        if (branch.contains("develop")) {
//            return "${pomVersion}-SNAPSHOT"
//        }
//        // Get total commit count
//        String commitCount = sh(returnStdout: true, script: "git rev-list --all --count").trim()
//        return "${pomVersion}-${commitCount}"
//    }
//
//    return getPomVersion(pomLocation).replaceAll('-SNAPSHOT', "")
//}
//
//String getPomVersion(String pomLocation) {
//    def pom = readMavenPom file: pomLocation
//    return pom.version
//}



//void initBuild(Map<String, Object> params = [:]) {
//    Map<String, Object> resolvedParams = [
//            pomLocation: 'pom.xml',
//            deleteBuildOnBump: false,
//            renameJenkinsBuild: false
//    ] << params
//
//    // Multibranch pipeline jobs possesses the environment variable BRANCH_NAME; normal pipeline jobs not
//    if (!env.BRANCH_NAME) {
//        //env.BRANCH_NAME = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD 2> /dev/null').trim()
//        env.BRANCH_NAME = "${GIT_BRANCH.split("/")[1]}"  // e.g. origin/main
//    }
//
//    def authors = currentBuild.changeSets.collectMany {
//        it.toList().collect {
//            it.getAuthor().getId()
//        }
//    }
//
//    // Remove the Jenkins user (commits by jenkins, e.g. for a new release, don't count)
//    // This prevents that builds get into a loop
//    authors -= 'jenkins'
//    env.IS_AUTOMATED_BUILD = true
//    def causedByUser = 'Jenkins_Scheduled_TriggeredByGit' // e.g. scheduled or triggered by Git via a hook
//    // Check if the build is initiated by a user
//    if (currentBuild.rawBuild.getCause(Cause.UserIdCause)) {
//        // The user who has started the job:
//        causedByUser = currentBuild.rawBuild.getCause(Cause.UserIdCause).getUserId().toString()
//        env.IS_AUTOMATED_BUILD = false
//    }
//    // if authors is empty (no GIT-commit by user) and the job is not started by a user, then abort the job
//    if (authors.empty && Boolean.valueOf(env.IS_AUTOMATED_BUILD) && (Integer.valueOf(env.BUILD_NUMBER) > 1)) {
//        sh "Abort pipeline job"
//        if (resolvedParams.deleteOnBump) {
//            currentBuild.rawBuild.delete()
//        }
//        currentBuild.result = Result.ABORTED
//        currentBuild.displayName = "#${env.BUILD_NUMBER} aborted. No new commits"
//        error("Last commit bumped the version, aborting build to prevent looping")
//    }
//
//    env.LAST_COMMITTER = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%an'").trim()
//    sh "logger fullProjectName='${currentBuild.fullProjectName}', build='${currentBuild.displayName}', lastCommitter='${env.LAST_COMMITTER}', authors='${authors.unique()}'"
//}

//void mavenBuild(Map<String, Object> params = [:]) {
//    Map<String, Object> resolvedParams = [
//            pomLocation: 'pom.xml',
//            mavenSettingsFile: 'default-maven-settings',
//            arguments: 'clean package'
//    ] << params
//
//    // withMaven(mavenSettingsConfig: resolvedParams.mavenSettingsFile) {sh "mvn clean package"}
//    sh "mvn -f ${resolvedParams.pomLocation} ${resolvedParams.arguments}"
//}

String mavenRelease(Map params = [:]) {
    Map<String, Object> resolvedParams = [
            pomLocation: 'pom.xml',
            mavenSettingsFile: 'default-maven-settings',
            arguments: ''
    ] << params

    if (env.gitSemVersion) {
        releaseWithVersionStrategy(resolvedParams)
        return env.gitSemVersion
    } else {
        return releaseWithoutVersionStrategy(resolvedParams)
    }
}

private void releaseWithVersionStrategy(Map resolvedParams) {
//    withMaven(mavenSettingsConfig: resolvedParams.mavenSettingsFile) {
    sh "mvn -f ${resolvedParams.pomLocation} -DskipTests deploy"
//    }

    // Cleanup local modifications
    sh "git reset --hard"
}

private String releaseWithoutVersionStrategy(Map resolvedParams) {
    def version = null
//    withMaven(mavenSettingsConfig: resolvedParams.mavenSettingsFile) {
        version = getReleaseVersion(resolvedParams.pomLocation)
        sh "mvn -f ${resolvedParams.pomLocation} release:prepare -Darguments='-DskipTests ${resolvedParams.arguments}'"
        sh "mvn -f ${resolvedParams.pomLocation} release:perform -Darguments='-DskipTests ${resolvedParams.arguments}'"
//    }

    sh "git status"
    sh "git pull --rebase origin ${env.BRANCH_NAME}"
    sh "git push --tags origin ${env.BRANCH_NAME}"

    return version
}

private String getReleaseVersion(pomLocation) {
    def pom = readMavenPom file: pomLocation
    def version = pom.version
    if (version == null || version.toString().trim() == '') {
        version = pom.parent.version
    }
    return version?.replace('-SNAPSHOT', '')
}


