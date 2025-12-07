pipeline {
	agent any

    tools {
		maven 'Maven-3.9.7'
        jdk 'JDK-17'
    }

    stages {
		stage('Checkout') {
			steps {
				echo 'Obteniendo código desde GitHub...'
                checkout scm
            }
        }

        stage('Build') {
			steps {
				echo 'Compilando el proyecto...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
			steps {
				echo '🧪 Ejecutando pruebas unitarias...'
                bat 'mvn test'
            }
            post {
				always {
					junit '**/target/surefire-reports/*.xml'
                    echo 'Resultados de tests publicados'
                }
            }
        }

        stage('Package') {
			steps {
				echo 'Empaquetando aplicación...'
                bat 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
			steps {
				echo 'Desplegando aplicación...'
                bat '''
                    echo Deteniendo aplicacion si esta corriendo...
                    taskkill /F /IM java.exe /FI "WINDOWTITLE eq ProyectoIntegrador*" || exit 0
                    echo Iniciando aplicacion en segundo plano...
                    start /B java -jar target/ProyectoIntegrador-0.0.1-SNAPSHOT.jar
                    echo Aplicacion desplegada en http://localhost:8083
                '''
            }
        }
    }

    post {
		success {
			echo 'Pipeline ejecutado exitosamente!'
            emailext(
                subject: "Build Exitoso - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Completado Exitosamente</h2>
                    <p><b>Proyecto:</b> ${env.JOB_NAME}</p>
                    <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                    <p><b>Estado:</b> SUCCESS</p>
                    <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'brunoooogallegos@hotmail.com',
                mimeType: 'text/html'
            )
        }
        failure {
			echo 'Pipeline falló. Revisa los logs.'
            emailext(
                subject: "Build Fallido - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Falló</h2>
                    <p><b>Proyecto:</b> ${env.JOB_NAME}</p>
                    <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                    <p><b>Estado:</b> FAILURE</p>
                    <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'brunoooogallegos@hotmail.com',
                mimeType: 'text/html'
            )
        }
    }
}