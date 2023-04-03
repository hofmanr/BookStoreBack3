#!groovy

// @Library('common-java')

def appPom = "pom.xml"
def ejbPom = "bsb-ejb/pom.xml"
def webPom = "bsb-web/pom.xml"

def gitUse = "jenkins"

pipeline {
    agent {
        docker {
            image 'rhofman14/maven-agent:3.8.6-jdk11'
/*            image 'maven:3.9.0-eclipse-temurin-11-alpine' */
            args '-v $HOME/.m2:/root/.m2'
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
                sh  '''
                    mvn --version
                    java -version
                    '''
                sh 'ls -l "$WORKSPACE"'
            }
        }

        stage('InitBuild') {
            steps {
                script {
                    if (GIT_AUTHOR.startsWith(gitUse)) {
                        currentBuild.result = 'ABORTED'
                        error('Skipping build triggered by version bump')
                        // return instead of throwing an error to keep the build 'green'
                        return
                    }
                    initBuild pomLocation: appPom
                }
                sh 'printenv'
            }
        }
    }
}

void initBuild(Map<String, Object> params = [:]) {
    def defaultParams = [
            pomLocation: 'pom.xml',
            deleteBuildOnBump: false,
            renameJenkinsBuild: false
    ]
    Map<String, Object> resolvedParams = [:] << defaultParams << params

    env.GIT_BRANCH_NAME = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD 2> /dev/null').trim()

    def authors = currentBuild.changeSets.collectMany {
        it.toList().collect {
            it.getAuthor().getId()
        }
    }
    sh "logger authors='${authors}'"

    // Remove the Jenkins user (commits by jenkins, e.g. for a new release, don't count)
    // This prevents that builds get into a loop
    authors -= 'jenkins'
    env.IS_AUTOMATED_BUILD = true
    def causedByUser = 'Jenkins_Scheduled_TriggeredByGit' // e.g. scheduled or triggered by Git via a hook
    // Check if the build is initiated by a user
    if (currentBuild.rawBuild.getCause(Cause.UserIdCause)) {
        // The user who has started the job:
        causedByUser = currentBuild.rawBuild.getCause(Cause.UserIdCause).getUserId().toString()
        env.IS_AUTOMATED_BUILD = false
    }
    // if authors is empty (no commit by user) and the job is not started by a user, then abort the job
    if (authors.empty && Boolean.valueOf(env.IS_AUTOMATED_BUILD) && (Integer.valueOf(env.BUILD_NUMBER) > 1)) {
        sh "Abort pipeline job"
        if (resolvedParams.deleteOnBump) {
            currentBuild.rawBuild.delete()
        }
        currentBuild.result = Result.ABORTED
        currentBuild.displayName = "#${env.BUILD_NUMBER} aborted. No new commits"
        error("Last commit bumped the version, aborting the build to prevent looping")
    }

    env.LAST_COMMITTER = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%an'").trim()
    sh "logger fullProjectName='${currentBuild.fullProjectName}', build='${currentBuild.displayName}', lastCommitter='${env.LAST_COMMITTER}', authors='${authors.unique()}'"
}