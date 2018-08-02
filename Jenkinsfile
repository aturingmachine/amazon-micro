pipeline {
    agent any
    
    environment {
      CFAPI = 'https://api.run.pivotal.io'
      CFUSERNAME = credentials('PCFUSER')
      CFPASS = credentials('PCFPASS')
    }
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
                echo 'Logging in to CF...'
                sh 'cf login -a $CFAPI -u $CFUSERNAME -p $CFPASS -o solstice-org -s vblom-cnt'
                echo 'Deploying....'
                sh 'cf push accounts -t 180 -p applications/accounts/build/libs/applications/accounts-0.0.1-SNAPSHOT.jar'
                sh 'cf push orders -t 180 -p applications/orders/build/libs/applications/orders-0.0.1-SNAPSHOT.jar'
                sh 'cf push products -t 180 -p applications/products/build/libs/applications/products-0.0.1-SNAPSHOT.jar'
                sh 'cf push shipments -t 180 -p applications/shipments/build/libs/applications/shipments-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
