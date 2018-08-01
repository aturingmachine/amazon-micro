pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building Application: accounts...'
                ./gradlew applications/accounts:build
                echo 'Building Application: orders...'
                ./gradlew applications/orders:build
                echo 'Building Application: products...'
                ./gradlew applications/products:build
                echo 'Building Application: shipments...'
                ./gradlew applications/shipments:build
            }
        }
        stage('Test') {
            steps {
                echo 'Testing Application: accounts...'
                ./gradlew applications/accounts:test
                echo 'Testing Application: orders...'
                ./gradlew applications/orders:test
                echo 'Testing Application: products...'
                ./gradlew applications/products:test
                echo 'Testing Application: shipments...'
                ./gradlew applications/shipments:test
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
