// actividad 3
pipeline {
     agent {
        label 'docker'
    }
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/ajunquit/unir-cicd-jenkins.git'

            }
        }
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        stage('API tests') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        //stage('e2e tests') {
            //steps {
                //sh 'make test-e2e'
                //archiveArtifacts artifacts: 'results/*.xml'
            //}
        //}
    }
    post {
        always {
            junit 'results/*_result.xml'
            cleanWs()
        }
        success {
            emailext body:"trabajo exitoso" subject: "Pipeline successful", to: "ajunquit@gmail.com"
            cleanWs()
        }
        // unstable {
        //     emailext subject: "Pipeline tests not successful", to: "ajunquit@gmail.com"
        //     cleanWs()
        // }
        //failure {
        //    emailext body: "trabajo con errores"subject: "Pipeline error", to: "ajunquit@gmail.com"
        //    cleanWs()
        //}
    }
}
