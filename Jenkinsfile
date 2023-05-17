#!groovy

@Library('common-lib')
import nl.rhofman.jenkins.utils.domain.Destination

def appPom = "pom.xml"
def ejbPom = "bsb-ejb/pom.xml"
def webPom = "bsb-web/pom.xml"
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
        POM = readMavenPom file: appPom
        GIT_REVISION = sh(returnStdout: true, script: 'git rev-parse --short HEAD')
        GIT_AUTHOR = sh(returnStdout: true, script: 'git log -1 --pretty=%cn')
        VERSION = POM.getVersion().toLowerCase().replaceAll('-snapshot', '-' + GIT_REVISION)
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Verify') {
            steps {
                auditTools()
                sh 'ls -l "$WORKSPACE"'
            }
        }

        stage('Init') {
            steps {
                script {
                    initBuild pomLocation: appPom
                }
                sh 'printenv'
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
                mavenBuild(pomLocation: appPom, arguments: 'clean package -DskipTests')
            }
        }

        stage('Build with Deploy') {
            when { anyOf { branch '*main'}} // or 'develop'
            environment {
                pomfile = "${appPom}".toString()
            }
            steps {
                sh '''
                    mvn -f ${pomfile} versions:set -DprocessAllModules -DnewVersion=${VERSION}
                    mvn versions:commit -DprocessAllModules
                '''
                // Should be clean deploy if Nexus is used
                mavenBuild(pomLocation: appPom, arguments: 'clean package -DskipTests')
            }
        }

        stage('Unit Test') {
            steps {
                mavenBuild(pomLocation: appPom, arguments: 'test')
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
            }
        }

        stage('Deploy') {
            when { anyOf { branch '*main'}} // or 'develop'
            parallel {
                stage('Database') {
                    script {
                        deployToDb(
                                artifactId: 'package-db-bsb',
                                destination: new Destination(name: 'BSB-DB', stage: 'Develop'),
                                releaseVersion: releaseVersion,
                                credentials: 'BSB_USER_DB'
                        )
                    }
                }
                stage('Application') {
                    script {
                        deployToAppServer(
                                artifactId: 'bsb-ear',
                                destination: new Destination(name: 'BSB-APPSERVER', stage: 'Develop'),
                                releaseVersion: releaseVersion,
                                credentials: 'BSB_USER_LIBERTY'
                        )
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
                    script {
                        deployDb(
                                artifactId: 'package-db-bsb',
                                destination: new Destination(name: 'BSB-DB', stage: 'Test'),
                                releaseVersion: releaseVersion,
                                credentials: 'BSB_USER_DB'
                        )
                    }
                }
                stage('Application') {
                    script {
                        deployApp(
                                artifactId: 'bsb-ear',
                                destination: new Destination(name: 'BSB-APP', stage: 'Test'),
                                releaseVersion: releaseVersion,
                                credentials: 'BSB_USER_LIBERTY'
                        )
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


