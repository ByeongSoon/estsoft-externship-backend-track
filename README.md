# 🦁멋쟁이사자처럼X이스트소프트 백엔드 개발자 익스턴십 1기

멋쟁이 사자처럼과 이스트소프트가 함께 진행한 미션 중심형 온라인 인턴십

 - 백엔드 개발자 익스턴십 1기에서 🏅최우수 수료생🏅 선발

실제 익스턴십 프로잭트는 fork 한 뒤 진행했었는데 원본 저장소가 private로 지정되어 있어 fork한 나의 Repository도 public으로 변경할 수 없었기에 별도로 Repository를 만들었다.

익스턴십 진행중 커밋과 PR 내용은 아래의 History에 스크린샷으로 정리해두었다.

## 🤔 회고

- 전체적으로 정말 만족스러웠고 많이 배웠던 시간이었습니다. 실무에 계신 강사님께서 해주시는 1:1 코드 리뷰와 각 조원들끼리 진행하는 그룹 피어리뷰 덕에 나쁜 코딩 습관이나 무심코 지나칠 수 있었던 에러를 수정할 수 있었습니다. 개인적으로 조금 아쉽다고 생각되는 부분은 직업이 장교였다보니 해당 Externship을 진행할 때 군부대라는 외부망과 단절되고 보안이 철저한 곳에서 근무했기 때문에 실질적으로 작업을 할 수 있는 시간과 환경에서 아쉬운 부분이 있었습니다.
- 프로젝트를 시작하고는 주어진 프로젝트의 패키지 등의 구조를 살펴보며, 이후에는 주어진 미션의 요구사항을 순차적으로 하나씩 확인해가며 개발을 진행했습니다. 주어진 미션 요구사항을 모두 만족한 후에는 코드를 리펙토링 하면서 코드의 중복은 없는지, 필드의 네이밍은 괜찮은지, 예외 처리는 되어있는지 확인하며 코드를 수정하려고 노력했습니다. 또한 도메인 계층과 웹 계층, API 계층 간의 책임 분리에 신경썼습니다. 해설 강의 중에 강사님이 말씀해주신 내용으로 'web', ‘api’ 하위 모든 파일을 삭제하고 나서도 코드 컴파일이 정상적으로 된다면 Dto나 web, api 쪽에 의존하고 있지 않다는 내용에 부합하려고 의존하지 않도록 노력했습니다.
- Spring Security를 처음 사용해보기 때문에 기초부터 천천히 공부하기 위해 노력했습니다. 어떻게보면 간단한 로그인/로그아웃 이지만 WebSecurityConfig 클래스의 각 설정들이 무엇을 의미하는지 체크하면서 구현했습니다.
- 평소 Spring Boot를 이용한 API 서버 개발만 경험을 해왔었는데 View 부분을 함께 구현하기 위해서 Thymeleaf의 기본 사용법이나 Controller의 반환값인 경로가 어떻게 찾아가는지를 찾아봤습니다. 또한 Thymeleaf Layout Dialect, Thymeleaf fragment를 이용한 공통 레이아웃 생성으로 .html 코드의 중복을 줄일 수 있었습니다.
- API 서버 개발을 위한 새로운 프로젝트로 시작하는 주차에서는 웹 서비스와는 다른 패키지 구조 파악과 기존에 사용한 클래스를 어떻게 재사용 할 수 있을지에 초점을 뒀습니다. 그리고 무엇보다 소셜 서비스와의 로그인 연동을 처음해보는거라 OAUTH와 JWT에 대한 지식을 쌓는 것을 주력으로 진행 했습니다. 처음 카카오 측으로 API 요청을 보낼 때 RestTemplate를 사용해서 구현 했었는데 추후에 FeignClient를 알게되서 해당 부분 로직을 리팩토링 해보았습니다. RestTemplate를 사용할 때보다 비교적 코드가 단순해지고 깔끔하게 사용할 수 있었습니다.
- 이번 미션에서는 카카오만 로그인을 하지만 우리가 시중에서 사용하는 서비스들은 구글, 네이버, 페이스북 등 다른 SNS와도 사용자 인증 연동이 되어있기에 해당 부분을 추상화하여 다형성을 만족시키려고 하였으나 시간이 조금 부족했던 것 같습니다.
- 5주차 미션에서 API 구현을 제외한 부분들은 경험이 없던 부분들이 었어서 굉장히 흥미가 있었습니다. API 문서 자동화는 Swagger를 사용했고, AWS 사용 방법에도 굉장히 관심이 많았는데 사용 해보는 것 뿐만 아니라 GitAction과 함께 사용하여 push 만으로 자동으로 CI/CD 자동화 Pipeline을 만들어 놓은 것이 아주 좋았던 부분이었습니다.

## 프로젝트 빌드 방법

## History

### 1주차

<img src="/images/week1_1.png"  width="700" height="550">
<img src="/images/week1_2.png"  width="700" height="500">

### 2주차

<img src="/images/week2_1.png"  width="700" height="300">
<img src="/images/week2_2.png"  width="700" height="600">
<img src="/images/week2_3.png"  width="700" height="900">

### 3주차

<img src="/images/week3_1.png"  width="700" height="550">
<img src="/images/week3_2.png"  width="700" height="800">
<img src="/images/week3_3.png"  width="700" height="900">

### 4주차

<img src="/images/week4_1.png"  width="700" height="600">
<img src="/images/week4_2.png"  width="700" height="900">

### 5주차

<img src="/images/week5_1.png"  width="700" height="600">
<img src="/images/week5_2.png"  width="700" height="800">

### etc

<img src="/images/projects_1.png"  width="700" height="300">
<img src="/images/projects_2.png"  width="700" height="300">
