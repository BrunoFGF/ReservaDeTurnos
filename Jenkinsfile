pipeline {
	agent any

    tools {
		maven 'Maven-3.9.7'
        jdk 'JDK-17'
    }

    stages {
		stage('Checkout') {
			steps {
				echo '🔄 Obteniendo código desde GitHub...'
                checkout scm
            }
        }

        stage('Build') {
			steps {
				echo '🔨 Compilando el proyecto...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
			steps {
				echo '🧪 Ejecutando pruebas unitarias...'
                sh 'mvn test'
            }
            post {
				always {
					junit '**/target/surefire-reports/*.xml'
                    echo '📊 Resultados de tests publicados'
                }
            }
        }

        stage('Package') {
			steps {
				echo '📦 Empaquetando aplicación...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
			steps {
				echo '🚀 Desplegando aplicación...'
                sh '''
                    echo "Deteniendo aplicacion si esta corriendo..."
                    pkill -f "ProyectoIntegrador" || true
                    echo "Iniciando aplicacion en segundo plano..."
                    nohup java -jar target/ProyectoIntegrador-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
                    sleep 5
                    echo "Aplicacion desplegada en http://localhost:8083"
                    echo "Verificando que la aplicacion este corriendo..."
                    ps aux | grep ProyectoIntegrador || true
                '''
            }
        }
    }

    post {
		success {
			echo '✅ Pipeline ejecutado exitosamente!'
        }
        failure {
			echo '❌ Pipeline falló. Revisa los logs.'
        }
    }
}