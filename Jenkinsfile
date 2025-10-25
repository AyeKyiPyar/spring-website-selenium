pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/AyeKyiPyar/spring-website-selenium.git'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        APP_CONTAINER = spring-website-selenium-app'
        APP_JAR = 'target/Spring-chpt-01-Ex-0.0.1-SNAPSHOT.jar'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building Maven project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Start Test Environment') {
            steps {
                echo '⚙️ Starting MySQL container for Selenium tests...'
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d mysql_db"
                bat 'powershell -Command "Start-Sleep -Seconds 10"' // wait for MySQL
            }
        }

        stage('Run Selenium Tests') {
            steps {
                echo '🧪 Running Selenium UI tests...'
                // Selenium tests connect to the running app at http://localhost:8080
                bat 'mvn test -Pselenium-tests'
            }
        }

        
        stage('Build & Deploy App') {
            steps {
                echo '🚀 Deploying Spring Boot app with MySQL using Docker Compose...'
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                bat 'powershell -Command "Start-Sleep -Seconds 20"' // wait for services
            }
        }
    }

    post {
        always {
            echo '✅ Pipeline finished. Current Docker containers:'
            bat 'docker ps -a'
        }
        success {
            echo "🎉 Pipeline succeeded! App running at http://localhost:7074/"
        }
        failure {
            echo '❌ Pipeline failed. Check logs above.'
        }
    }
}