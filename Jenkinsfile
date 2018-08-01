pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building Application: accounts...'
                sh './gradlew applications/accounts:build'
                echo 'Building Application: orders...'
                sh './gradlew applications/orders:build'
                echo 'Building Application: products...'
                sh './gradlew applications/products:build'
                echo 'Building Application: shipments...'
                sh './gradlew applications/shipments:build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing Application: accounts...'
                sh './gradlew applications/accounts:test'
                echo 'Testing Application: orders...'
                sh './gradlew applications/orders:test'
                echo 'Testing Application: products...'
                sh './gradlew applications/products:test'
                echo 'Testing Application: shipments...'
                sh './gradlew applications/shipments:test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
