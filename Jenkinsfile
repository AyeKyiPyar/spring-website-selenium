pipeline {
    agent any

    environment {
        APP_IMAGE = 'spring-website-selenium:latest'
        APP_CONTAINER = 'spring-website-selenium-app'
        APP_PORT = '7075'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                git branch: 'main', url: 'https://github.com/AyeKyiPyar/spring-website-selenium.git'
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building Maven project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Start App Container') {
		    steps {
		        echo '⚙️ Ensuring MySQL container is running...'
		        bat '''
		            REM Check if mysql_db container exists
		            docker ps -a --format "{{.Names}}" | findstr /C:"mysql_db" >nul
		            if %errorlevel%==0 (
		                REM Container exists, check if running
		                docker inspect -f "{{.State.Running}}" mysql_db | findstr /C:"true" >nul
		                if %errorlevel%==0 (
		                    echo ▶️ MySQL container is already running
		                ) else (
		                    echo ▶️ Starting existing MySQL container...
		                    docker start mysql_db
		                )
		            ) else (
		                REM Container does not exist, create and run
		                echo 🆕 Creating and starting MySQL container...
		                docker run -d --name mysql_db --network akpsnetwork -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=springonetomany -p 3306:3306 mysql:latest
		            )
		        '''
		
		        echo '⚙️ Starting Spring Boot container...'
		        bat '''
		            REM Stop existing Spring Boot container if running
		            docker ps -a --format "{{.Names}}" | findstr /C:"%APP_CONTAINER%" >nul
		            if %errorlevel%==0 (
		                echo ▶️ Stopping existing container...
		                docker stop %APP_CONTAINER%
		                docker rm %APP_CONTAINER%
		            )
		            REM Start Spring Boot container
		            docker run -d --name %APP_CONTAINER% --network akpsnetwork -p %APP_PORT%:8080 %APP_IMAGE%
		        '''
		        
		        bat 'powershell -Command "Start-Sleep -Seconds 20"' // wait for container to initialize
		    }
		}

        stage('Run Selenium Tests') {
            steps {
                echo '🧪 Running Selenium UI tests...'
                bat 'mvn test -Pselenium-tests'
            }
        }
    }

    post {
        always {
            echo '✅ Pipeline finished. Current Docker containers:'
            bat 'docker ps -a'
        }
        success {
            echo "🎉 Pipeline succeeded! App running at http://localhost:%APP_PORT%/"
        }
        failure {
            echo '❌ Pipeline failed. Check logs above.'
        }
    }
}
