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
                sh 'printenv'
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
            }
        }
    }
}

initBuild(Map<String, Object> params = [:]) {
    def authors = currentBuild.changeSets.collectMany {
        it.toList().collect {
            it.getAuthor().getId()
        }
    }
    env.lastCommitter = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%an'").trim()
    sh "logger fullProjectName='${currentBuild.fullProjectName}', build='${currentBuild.displayName}', lastCommitter='${env.lastCommitter}', authors='${authors.unique()}'"
}