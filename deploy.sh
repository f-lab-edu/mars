#!/bin/bash

# > mars pid 확인: 현재 실행 중인 'mars' 애플리케이션의 프로세스 ID(PID)를 확인합니다.
echo "> mars pid 확인"
CURRENT_PID=$(ps -ef | grep 'mars' | awk '{print $2}')  # 'mars' 애플리케이션의 PID를 가져옵니다.
echo "$CURRENT_PID"

# 현재 애플리케이션이 실행 중인지 확인
if [ -z ${CURRENT_PID} ]; then
  # 현재 'mars' 애플리케이션이 실행되지 않으면 종료하지 않음
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  # 애플리케이션이 실행 중일 경우, 종료를 시도합니다.
  echo "> 정상 종료 시도"
  sudo kill -15 $CURRENT_PID  # SIGTERM (정상 종료) 신호를 보냅니다.
  sleep 5  # 종료 후 5초 대기

  # 종료가 되지 않은 경우 강제 종료를 시도합니다.
  if [ ! -z $(ps -p $CURRENT_PID) ]; then  # 아직 프로세스가 살아 있으면
    echo "> 강제 종료"
    sudo kill -9 $CURRENT_PID  # SIGKILL (강제 종료) 신호를 보냅니다.
  fi
  sleep 10  # 강제 종료 후 10초 대기
fi

# > 서버 배포: 최신 JAR 파일을 배포합니다.
echo "> 서버 배포"
JAR_PATH=$(ls -t /project/*.jar | head -1)  # 최신 JAR 파일 경로를 찾습니다.
sudo nohup java -jar -DServer.port=80 ${JAR_PATH} >> /project/logs/test.log 2>&1 &  # 애플리케이션을 백그라운드로 실행합니다.

# > 서버가 정상적으로 구동 중인지 확인: 애플리케이션이 정상적으로 실행되는지 확인합니다.
echo "> 서버가 정상적으로 구동 중인지 확인"
if ps -ef | grep '[m]ars' > /dev/null; then
  echo "> 배포 완료 및 애플리케이션 구동 중"  # 애플리케이션이 실행 중이면 배포 완료 메시지 출력
else
  echo "> 애플리케이션 구동 실패"  # 애플리케이션이 실행되지 않으면 실패 메시지 출력
  exit 1  # 배포 실패 시 스크립트를 종료하고 오류 코드 1을 반환합니다.
fi