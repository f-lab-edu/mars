pipeline {
    agent any
    tools {
        gradle 'Gradle9.0'
    }

    stages {
        stage("github clone") {
            steps {
                git branch: 'develop', url: 'https://github.com/f-lab-edu/mars.git'
            }
        }

        stage("build") {
            steps {
                sh '''
                    echo build start
                    chmod +x gradlew
                    ./gradlew clean build -x test
                '''
            }
        }

        stage("deploy") {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'mars-server',  // Jenkins에 등록한 SSH 서버 이름 , 젠킨스관리 -> System -> SSH Servers
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'build/libs/mars-0.0.1-SNAPSHOT.jar',
                                    removePrefix: 'build/libs',     // 파일명 앞에서 이 경로 제거됨
                                    remoteDirectory: '.',           // 상대 경로.
                                    execCommand: ''                 // 예: java -jar mars-0.0.1-SNAPSHOT.jar
                                ),
                                // deploy.sh 스크립트를 서버에 전송
                                sshTransfer(
                                    sourceFiles: 'scripts/deploy.sh',  // deploy.sh 파일 경로 (Jenkins 내 위치)
                                    removePrefix: 'scripts',  // scripts 폴더 제거
                                    remoteDirectory: '.',  // 서버 내 저장 경로
                                    execCommand: 'chmod +x /project/deploy.sh && /project/deploy.sh'  // 원격 서버에서 실행할 명령어
                                )
                            ],
                            verbose: true // 복사 과정 로그 자세히 출력
                        )
                    ]
                )
            }
        }
    }

     post {
        success {
            echo "Build and Deploy completed successfully!"
        }
        failure {
            echo "Build or Deploy failed."
        }
    }
}