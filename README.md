# find-you
소개팅 애플리케이션을 모방
## 1. 개요
### 1.1. 고민사항
1. 기획이 어떻게 수익으로 전환될지, 한 줄 기획을 어떻게 코드로 구현할지, 한 줄 코드에 어떻게 기초지식을 녹여낼지 고민
2. 해당 Service에서는 사용자는 같은 성별의 User와 접할 필요가 없다.
3. 사용자가 입력하고 행동한 Data에 기반하여 이상형에 가까운 추천 목록을 `어떻게 구성하고`, `어떻게 전달할지` 고민

### 1.2. 사용기술
`Java21`, `Spring Boot`, `JPA`, `Query-dsl`, `RDBMS(MySQL)`, `docker`, `kafka`


## 2. 서비스 요구사항




# API
[Notion](https://www.notion.so/gyeomfka/find-you-API-1d466fd5fe9e807cac27c42909ccfe88)

# 서비스 요구사항
[Github Wiki](https://github.com/f-lab-edu/find-you/wiki/Service-Requirements)

# 고도화 리스트
1. 분산 DB환경 구축
1. 중복체크 시 분산캐시 및 레디스 활용
1. 위치기반 상대와 나와의 거리 계산
2. 채팅
3. 결제
4. 첨부파일 개발
5. 카드추천 알고리즘 개발
6. 코인 시스템(시간당 지급, 특정 이벤트 지급)
