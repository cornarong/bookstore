<div align="center">

# Cornarong BookStore
### 인터넷 서점

[![mainLogo](./src/main/resources/static/images/mainLogo.png)](http://3.37.80.216:8080/)

[![접속](https://img.shields.io/static/v1?label=링크&message=접속하기&color=59666C)](http://3.37.80.216:8080/)

</div>

### * 프로젝트 개발 이유

>최신 기술 트랜드에 따라 평소에 관심있던 기술들을 직접 학습하게 되면서 
학습한 내용을 바탕으로 프로젝트에 적용시켜 보고자 하여 만들게 되었습니다.

<br>
<br>

### * 사용된 기술 스택

- <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"/> <img src="https://img.shields.io/badge/BootStrap-7952B3?style=flat-square&logo=BootStrap&logoColor=white"/>
- <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/> <img src="https://img.shields.io/badge/Java11-007396?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
- <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=Thymeleaf&logoColor=white"/> <img src="https://img.shields.io/badge/CSS-1572B6?style=flat-square&logo=CSS3&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/> <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&logo=jQuery&logoColor=white"/>
- <img src="https://img.shields.io/badge/Spring MVC-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white"/>
- <img src="https://img.shields.io/badge/AWS ec2-232F3E?style=flat-square&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white"/>

<br>
<br>

### * 서비스 설계 구조

모든 리소스 자원은 `Spring Security`로 권한에 따라 관리하고 있습니다.
  
데이터베이스는 `MySQL`을 사용했으며 서비스와 연동 및 매핑은 `Spring Data Jpa`와 `Hibernate`를 사용하였습니다.  
테이블 및 컬럼은 `Hibernate`으로 자동 생성하였고 필요에 따라 수동으로 수정하였습니다.

`Controller`에서는 `DTO`를 사용하여 클라이언트로 응답하거나 `Service`의 인자로 사용하였고   
`Service`에서는 `DTO <-> ENTITY` 변환 처리 및 `Repository`를 사용하여 DB에 접근하고 가공된 데이터를 `DTO`로 변환하여  
`Controller`로 반환하도록 설계하여 `Controller`와 `Service`역할을 명확하게 분담하여 가독성과 유지보수성을 높였습니다.

`ENTITY`의 수정은 데이터의 보안성과 객체의 일관성을 유지하기 위해 `ENTITY`의 내부에서만 가능하도록 설계하였으며  
기본 생성자는 `AccessLevel.PROTECTED`으로 선언하여 무분별한 객체 생성을 방지하였습니다.  
`DTO`는 외부에서도 수정 또는 객체 생성이 가능하지만 유지보수성을 고려하여 `Service`에서 처리하였습니다.

또한 `Service`의 무분별한 비즈니스 로직으로 가독성이 떨어질 것을 고려하여 데이터 가공의 관련된 비즈니스 로직들은  
해당 `ENTITIY` 또는 해당 `DTO`내부에 선언하여 직접 호출하여 사용하도록 설계하였습니다.

서버는 `AWS`에서 지원하는 프리티어 서비스인 `ec2`를 사용하여 빌드 및 배포하였고 파일의 형상관리는 `Git`으로 하고있습니다.

<br>
<br>

### * 프로젝트 기능

- 회원가입과 로그인 기능이 있습니다.
- 국내도서, 외국도서, 신작도서 등 정렬된 도서 목록을 보여주며 도서를 검색할 수 있습니다.
- 도서를 장바구니에 담아서 주문하거나 바로 직접 주문할 수 있습니다.
- 장바구내 내역 또는 주문내역을 확인할 수 있습니다.
- 일반 게시판이 있으며 게시글을 검색할 수 있습니다.
- 도서를 수동으로 등록하거나 외부 웹사이트의 도서목록을 크롤링하며 자동 등록할 수 있습니다.

<br>
<br>

### * 권한별 기능

사용자, 매니저, 관리자 3개의 권한이 존재하며 `상위 권한은 하위 권한의 모든 기능을 포함합니다.`

- 사용자
  - 로그인 및 회원가입(KAKAO API)
  - 개인정보 수정
  - 장바구니 담기 및 구매하기
  - 게시글 등록하기

- 매니저
  - 수동 도서 등록
  - 등록 도서 관리

- 관리자
  - 크롤링 도서 등록(JSOUP)
  - 모든 도서 관리
  - 회원 관리

<br>
<br>

### * 프로젝트 화면







