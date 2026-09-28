pipeline {
    agent any

    tools {
        maven 'M3'
        jdk 'JDK17'
    }

    options {
        timeout(time: 20, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        disableConcurrentBuilds()
    }

    parameters {
        choice(
            name: 'BUILD_PROFILE',
            choices: ['dev', 'prod'],
            description: 'Maven profile to activate'
        )

        booleanParam(
            name: 'SKIP_TESTS',
            defaultValue: false,
            description: 'DANGEROUS: skip unit tests'
        )
    }

    environment {
        APP_NAME = 'myapp'
    }

    triggers {
        githubPush()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                sh '''
                    echo "Branch : ${GIT_BRANCH}"
                    echo "Commit : ${GIT_COMMIT}"
                    echo "Build : ${BUILD_NUMBER}"
                '''
            }
        }

        stage('Validate') {
            steps {
                sh 'mvn validate'
            }
        }

        stage('Compile') {
            steps {
                sh "mvn compile -P${params.BUILD_PROFILE}"
            }
        }

        stage('Unit Test') {
            when {
                expression {
                    return !params.SKIP_TESTS
                }
            }

            steps {
                sh "mvn test -P${params.BUILD_PROFILE}"
            }

            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    def skipFlag = params.SKIP_TESTS ? '-DskipTests' : ''
                    sh "mvn package -P${params
