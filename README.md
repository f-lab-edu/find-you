# find-you
소개팅 애플리케이션

# API
[Notion](https://www.notion.so/gyeomfka/find-you-API-1d466fd5fe9e807cac27c42909ccfe88)

### 멀티모듈 구조로 가지고 올 것, 나누지 않아도 된다
- 어떤 기준으로 나눠야 하는거야 ?

### gradle 정리 pr 하나 만들기

### api spec request, response 작성
- controller까지 작성 -> api spec -> DTO -> 통합 테스트
- API spec에 대한 통합 테스트
- 테스트의 대상은 API spec이다.

### response body 통일
{
code : 
message : 
data : 
}


### TODO
1. valid exception 과 custom exception 통일
2. test code 작성
3. dummy data 생성 쿼리 작성
4. 닉네임 중복제거

### like table에 관하여
사실상 log 테이블도 생성이 될텐데, log테이블도 데이터가 무수히 쌓일텐데 그것과의 차이는 ?

### response dto는 뭘 반환하는게 좋을까
조회 api가 아닌 api에서 response는 무엇을 반환하는게 좋을까 ?

### mysql
date type vs datetime(6) type

### filter는 '상관없음'값이 들어가야한다.