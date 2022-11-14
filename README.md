# Cafe Noctem Project

## ☕ Project

1. 카페 모바일 애플리케이션 주제의 프로젝트
2. 프로젝트 기간 : 2022.09.13 ~ 2022.11.11
3. Spring Boot 기반의 백엔드 서버
4. Java 11

## ☕ Demo

- User Service

  > User Service는 모바일로 접속하시는 것을 추천드립니다.
  > 
  > 위치 정보 이용에 동의해주시면 원활한 이용이 가능합니다.
  >
  > https://noctem-user1-fe.vercel.app/

  ![](https://github.com/arotein/noctem-store-BE/blob/main/img/Noctem_User_QR.jpg)

- Admin Service

  > Admin Service는 PC로 접속하시는 것을 추천드립니다.
  > 
  > https://noctem-admin-fe.vercel.app/
  >
  > ※ Admin Service Test Account  
  > ID : noctem1  
  > PW : noctem

## ☕ Repository URL

#### FrontEnd

<details>
 <summary>
  FrontEnd Repository 상세보기
 </summary>

| Service | Github Repository | Description |
|--- |--- |--- |
| user | [user](https://github.com/saiani1/noctem-user1-FE) | 유저 페이지입니다. |
| admin | [admin](https://github.com/saiani1/noctem-admin-FE) | 매장 페이지입니다. |

</details>  

#### BackEnd

<details>
 <summary>
  BackEnd Repository 상세보기
 </summary>

| Server | Github Repository | Description | Spring Boot | 
|--- |--- |--- |--- |
| eureka | [eureka](https://github.com/arotein/noctem-eureka-BE.git) | 각 서버들에 대해 Discovery, Registry 역할을 수행해주는 서버 | 2.7.4 |
| gateway | [gateway](https://github.com/arotein/noctem-gateway-BE.git) | 요청을 각 서버들로 라우팅시켜주는 게이트웨이 서버 | 2.7.4 |
| config | [config](https://github.com/arotein/noctem-config-server-BE.git) | 각 서버들의 설정 파일을 관리하고 적용해주는 config 서버 | 2.7.4 |
| batch | [batch](https://github.com/arotein/noctem-batch-BE.git) | 주기적으로 통계데이터를 처리하는 배치 서버 | 2.7.4 |
| alert | [alert](https://github.com/arotein/noctem-alert-BE.git) | SSE 알림기능 구현을 위한 알림 서버 | 2.7.5 |
| user | [user](https://github.com/arotein/noctem-user-BE.git) | 유저에 관한 서비스를 처리하는 서버 | 2.6.8 |
| store | [store](https://github.com/arotein/noctem-store-BE.git) | 매장에 관한 서비스를 처리하는 서버 | 2.6.8 |
| menu | [menu](https://github.com/arotein/noctem-menu-BE.git) | 메뉴에 관한 서비스를 처리하는 서버 | 2.7.4 |
| purchase | [purchase](https://github.com/arotein/noctem-purchase-BE.git) | 결제에 관한 서비스를 처리하는 서버 | 2.6.8 |
| admin | [admin](https://github.com/arotein/noctem-admin-BE.git) | 관리자에 관한 서비스를 처리하는 서버 | 2.6.8 |

</details>  

## ☕ Architecture

![](./img/architecture.png)

### 특징

- MicroService Architecture
- Spring WebFlux로 SSE 알림 구현
- Spring Batch로 통계데이터 API 응답 속도 개선
- Kafka로 서비스간 비동기 통신
- 각 서버는 Docker로 컨테이너화시켜 배포
- Prometheus, Grafana로 서버 모니터링

## ☕ CI/CD

![](./img/CICD.png)

### 특징

- Docker 및 Jenkins를 이용하여 빌드/배포 자동화
- Slack 연동을 통해 팀원간 빌드/배포 상태 공유

## ☕ 사용 기술

### Frontend

<img src="https://img.shields.io/badge/React.js-17b6e7?style=flat-square&logo=React&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Next.js-404040?style=flat-square&logo=Next.js&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=TypeScript&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/PWA-5A0FC8?style=flat-square&logo=PWA&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Recoil-17b6e7?style=flat-square&logo=data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FscXVlXzEiIGRhdGEtbmFtZT0iQ2FscXVlIDEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgdmlld0JveD0iMCAwIDI1NS4yMSA2MjMuOTEiPjxkZWZzPjxzdHlsZT4uY2xzLTF7ZmlsbDp3aGl0ZX08L3N0eWxlPjwvZGVmcz48cGF0aCBjbGFzcz0iY2xzLTEiIGQ9Im03NC42MiAyNzcuNDYgMS4yNC0uMTMgMzQuNzgtMy4yOC01My40Ny01OC42NkE5Ni40NyA5Ni40NyAwIDAgMSAzMiAxNTAuM0gzYTEyNS4zIDEyNS4zIDAgMCAwIDMyLjggODQuNTdaTTE3Ny4xMyAzNDdsLTM2IDMuNCA1My4zMiA1OC41MUE5Ni40MSA5Ni40MSAwIDAgMSAyMTkuNjMgNDc0aDI4LjkyYTEyNS4yOCAxMjUuMjggMCAwIDAtMzIuNzYtODQuNTdaIi8+PHBhdGggY2xhc3M9ImNscy0xIiBkPSJNMjUzLjY5IDIzMS42OGMtNi4zMy0zMS4zLTMwLjg5LTU0LjA5LTYyLjU3LTU4LjA3bC02LjM1LS43OWE0OS42MSA0OS42MSAwIDAgMS00My4zNS00OS4xM3YtMjBhNTIuNzUgNTIuNzUgMCAxIDAtMjguOTEtLjM2djIwLjM4YTc4LjU2IDc4LjU2IDAgMCAwIDY4LjY1IDc3LjgybDYuMzYuOGMyMy4yNCAyLjkyIDM0Ljc4IDIwIDM3LjgzIDM1LjFzLS45MyAzNS4zMi0yMS4yMiA0N2E3My44MSA3My44MSAwIDAgMS0zMC4wNiA5LjYybC05NS42NiA5YTEwMi40NSAxMDIuNDUgMCAwIDAtNDEuOCAxMy4zOEM5IDMzMi40NS00LjgxIDM2MyAxLjUyIDM5NC4yOXMzMC44OSA1NC4wOCA2Mi41NyA1OC4wNmw2LjM1LjhhNDkuNiA0OS42IDAgMCAxIDQzLjM1IDQ5LjEydjE4YTUyLjc1IDUyLjc1IDAgMSAwIDI4LjkxLjI2di0xOC4yNmE3OC41NSA3OC41NSAwIDAgMC02OC42NS03Ny44MWwtNi4zNi0uOGMtMjMuMjQtMi45Mi0zNC43OC0yMC4wNS0zNy44My0zNS4xMXMuOTMtMzUuMzIgMjEuMjItNDdhNzMuNjggNzMuNjggMCAwIDEgMzAuMDYtOS42M2w5NS42Ni05YTEwMi40NSAxMDIuNDUgMCAwIDAgNDEuOC0xMy4zOGMyNy42NS0xNi4wMiA0MS40LTQ2LjU0IDM1LjA5LTc3Ljg2WiIvPjwvc3ZnPg==&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/SASS-CC6699?style=flat-square&logo=SASS&logoColor=white"/></a>&nbsp;

### Backend

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Spring Batch-6DB33F?style=flat-square&logo=Spring Batch&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Spring WebFlux-6DB33F?style=flat-square&logo=Spring WebFlux&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/QueryDSL-0D86C1?style=flat-square&logo=QueryDSL&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/JPA-404040?style=flat-square&logo=JPA&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Kafka-231F20?style=flat-square&logo=Apache Kafka&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/></a>&nbsp;

### DB

<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=Amazon EC2&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Amazon EC2&logoColor=white"/></a>&nbsp;

### Deployment

<img src="https://img.shields.io/badge/Vercel-404040?style=flat-square&logo=Vercel&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white"/></a>&nbsp;

### Tools

<img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=Slack&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Github-000000?style=flat-square&logo=Github&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Notion-fafafa?style=flat-square&logo=Notion&logoColor=black"/></a>&nbsp;
<img src="https://img.shields.io/badge/Google Sheets-34A853?style=flat-square&logo=GoogleSheets&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Miro-FFCD00?style=flat-square&logo=Miro&logoColor=050038"/></a>&nbsp;

&nbsp;
