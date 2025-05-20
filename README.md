# 🚀 **프로젝트 목표**

- **📈 실시간 주식 트레이닝과 주식 매매를 도와주는 서비스**
- 성능과 유지보수성을 고려하여 **⚡ 효율적이고 확장 가능한 구조**로 개발
- **📚 이펙티브 자바**의 내용을 반영하여 코드 설계 및 품질 향상

---
## **구성도**
![image](https://github.com/user-attachments/assets/8ec6fa67-af62-4f67-898d-1a9d42d8a789)




## **시스템 아키텍처**

- mars 프로젝트는 **계층형 아키텍처 (Layered Architecture)** 를 기반으로 설계되었습니다.
- 설계 목적은 외부 API 스펙 변경이나 서버 API 변경 시 **영향 범위를 최소화**하고, **유지보수성**과 **확장성**을 확보하는 것입니다.
![시스템 아키텍처 다이어그램](https://github.com/user-attachments/assets/14bbffa1-fd1f-4a7f-a03c-2e859a1bd9cd)
---

### **모듈 간 의존성 규칙**

| 모듈       | 참조 가능 대상             | 참조 불가 대상          |
|------------|---------------------------|--------------------------|
| `api`      | `domain`                  | `db`, `client`, 자기 외 |
| `domain`   | `client`, `storage`, 자기 | `api`                    |
| `db`       | 자기 자신만                | `domain`, `api`, `client` |
| `client`   | 자기 자신만                | `domain`, `api`, `db`     |

- **api → db, client** 참조 금지
- **domain → api** 참조 금지
- **db, client → domain, api** 참조 금지

이러한 의존성 규칙을 통해 **계층 간 결합도 최소화**, **단일 책임 원칙(SRP)** 및 **의존성 역전 원칙(DIP)** 을 따르는 구조로 설계되었습니다.

## 💻 **Development Environment**

### **Frontend** 

| 🛠️ Tool         | ⚙️ Version |
| --------------- | ---------- |
| **Thymeleaf**   | 3.3.5      |

### **Backend** 🔧

| 🛠️ Tool          | ⚙️ Version/Name         |
| ---------------- | ----------------------- |
| **JDK**         | 21                     |
| **IDE**         | IntelliJ IDEA          |
| **Framework**   | Spring Boot 3.4.0  |
| **Database**    | H2           |
| **ORM**         | Hibernate 6.5.3 Final  |

---

## 🌟 Project Features

1. **🔍 실시간 시세 조회**
   - 주요 증권사 API(Kiwoom, Korea Investment 등)를 활용하여 실시간 주가 정보를 제공
2. **💰 주식 매매 기능**
   - 사용자 친화적인 인터페이스와 안정적인 매매 거래 지원
3. **📬 알림 기능**
   - 설정한 목표가에 도달 시 사용자에게 실시간 알림 제공
4. **⚙️ 성능 최적화 및 유지보수**
   - 확장 가능한 구조와 효율적인 트랜잭션 관리를 통해 고성능 환경 지원

##  CI/CD

- GitHub Actions를 활용해 CI/CD 환경을 구성
- 코드를 푸시하면 자동으로 빌드하고, 테스트 후 바로 운영 서버에 배포되도록 설정해 개발 편의성을 높힘

###  전체 흐름

1. `main` 브랜치에 코드가 푸시되면 워크플로우가 자동 실행됩니다.
2. Gradle로 프로젝트를 빌드합니다.
3. 빌드 성공 시, 운영 서버에 SSH로 접속해 기존 애플리케이션을 종료하고 새 버전을 실행합니다.

### ⚙️ 사용 도구
- **GitHub Actions**: CI/CD 파이프라인 자동화
- **Gradle**: 빌드 및 테스트 도구
- **JDK 21**: 개발 및 배포 환경에서 사용
- **SSH**: 운영 서버 배포에 사용
