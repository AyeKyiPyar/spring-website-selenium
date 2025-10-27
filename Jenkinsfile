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
		            docker ps -a --format "{{.Names}}" | findstr /C:"mysql_db" >nul
		            if %errorlevel%==0 (
		                docker inspect -f "{{.State.Running}}" mysql_db | findstr /C:"true" >nul
		                if %errorlevel%==0 (
		                    echo ▶️ MySQL container already running
		                ) else (
		                    echo ▶️ Starting existing MySQL container...
		                    docker start mysql_db >nul
		                )
		            ) else (
		                echo 🆕 Creating and starting MySQL container...
		                docker run -d --name mysql_db --network akpsnetwork -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=springonetomany -p 3306:3306 mysql:latest >nul
		            )
		        '''
		
		        echo '⚙️ Ensuring Spring Boot container is running...'
		        bat '''
		            docker ps -a --format "{{.Names}}" | findstr /C:"spring-website-selenium-app" >nul
		            if %errorlevel%==0 (
		                docker inspect -f "{{.State.Running}}" spring-website-selenium-app | findstr /C:"true" >nul
		                if %errorlevel%==0 (
		                    echo ▶️ Spring Boot container already running
		                ) else (
		                    echo ▶️ Starting existing Spring Boot container...
		                    docker start spring-website-selenium-app >nul
		                )
		            ) else (
		                echo 🆕 Creating Spring Boot container...
		                docker run -d --name spring-website-selenium-app --network akpsnetwork -p 7075:8080 spring-website-selenium:latest >nul
		            )
		            exit /b 0
		        '''
		        bat 'powershell -Command "Start-Sleep -Seconds 20"'
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
