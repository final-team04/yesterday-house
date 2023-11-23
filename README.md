# pjt_daejeon_10th_class8_final_team04 (김태희, 오상훈)

### 아파트 부동산 거래 내역 조회 서비스 : 어제의 집

<br>

## 설계서

### 목차

1. 요구사항 정의서(Usecase Diagram)
2. 테이블 구조도(ERD)
3. 클래스 다이어그램
   - Back-End
   - Front-End
4. 화면 설계서 등

<br><br><br>

## 1. 요구사항 정의서(Usecase Diagram)

![Alt text](image/UsecaseDiagram/UsecaseDiagram.png)

<hr>
<br><br><br>

## 2. 테이블 구조도(ERD)

![Alt text](image/db/MySqlERD.png)

<hr>
<br><br><br>

## 3. 클래스 다이어그램

### Back-End

![Alt text](image/back/back.png)

- UserLogic : 로그인<br>
  <img align="center" src="image/back/UserLogic.png" width="60%" ><p>
- UserModel : 로그인 관련 DTO<br>
  <img align="center" src="image/back/UserModel.png" width="60%" ><p>
- HouseLogic : 아파트 거래 정보<br>
  <img align="center" src="image/back/HouseLogic.png" width="60%" ><p>
- HouseModel : 아파트 거래 정보 관련 DTO<br>
  <img align="center" src="image/back/HouseModel.png" width="60%" ><p>
- BoardLogic : 게시판<br>
  <img align="center" src="image/back/BoardLogic.png" width="60%" ><p>
- BoardModel : 게시판 관련 DTO<br>
  <img align="center" src="image/back/BoardModel.png" width="60%" ><p>
- Exception and Inteceptor : 예외 처리 및 인터셉터<br>
  <img align="center" src="image/back/ExceptionAndInterceptor.png" width="60%" ><p>

<hr>
<br><br><br>

### Front-End

![Alt text](image/front/front.png)

<hr>
<br><br><br>

## 4. 화면 설계서

### 사이트 맵

![Alt text](image/web/사이트맵.png)

<hr><br><br><br>

### 메인화면<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C1.PNG)<br>

1. 로그인 페이지로 이동
2. 회원가입 페이지로 이동
3. 검색 날짜 범위 설정
4. 법정동(동이름) 검색어 추천
5. 아파트 거래내역 조회
6. 공지사항
7. 뉴스 크롤링 제공<br><br><br>
검색 날짜 범위 설정
![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C11.PNG)<br>
<hr><br><br><br>

### 로그인<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C2.PNG)<br>

1. 아이디 / 비밀번호를 통해 로그인
2. 회원가입 페이지 이동
<br><br>
<hr><br><br><br>

### 회원가입<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C3.PNG)<br>

1. 아이디 / 닉네임 / 비밀번호/ 비밀번호 확인을 통해 회원가입(비밀번호 확인 기능)
2. 로그인 페이지로 이동
<br><br>
<hr><br><br><br>

### 아파트 매매정보<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C4.PNG)<br>

1. 드랍 메뉴
2. 아파트 이름으로 검색
3. 아파트 거래 내역 리스트
4. 아파트 거래 내역의 아파트 위치
<br>
<hr><br><br><br>

### 아파트 이름으로 검색<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C5.PNG)<br>

1. 드롭 박스
2. 정보 수정하기로 이동하기
3. 로그아웃
4. 회원 탈퇴
<br><br><br>
![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C13.PNG)
회원 정보 수정(현재 비밀번호 및 새 비밀펀호 확인 기능)
<br>
<hr><br><br><br>

### 아파트 거래 상세 내역<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C6.PNG)<br>

1.  아파트 이름, 가격, 거래일 제공 및 클릭 이벤트 작동시 아파트 주변 로드뷰 제공
2.  아파트 주변 1km 범위 도식 및 마커 클릭시 오버레이 제공
3.  주변 아파트 거래내역의 평균과 현재 아파트 거래내역의 점수 비교 및 주변 시설 종류 및 수에 대한 정보 제공
4.  아파트 거래 상세 내역으로 준공연도, 거래일, 도로명주소, 정보
    <br><br>
    로드뷰<br>
    ![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C7.PNG)

    <br><br>
    주변 매물 평균 과 아파트 거래내역 비교 및 주변 시설 개수를 종합한 총합점수 제공<br>
    ![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C8.PNG)

<br>
<hr><br><br><br>

### 매물 이모저모 및 근처 다른 매물<br>

![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C9.PNG)<br>

1. 매물 이모저모(게시판) : 아파트별 게시판 커뮤니티 기능 제공
2. 게시판 수정으로 이동
3. 게시판 삭제
4. 현재 아파트 주변 1km 반경 근처 다른 매물 정보 제공<br><br><br>
   ![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C12.PNG)
   게시판 수정 기능 제공
   <br><br><br>
   ![Alt text](image/web/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C10.PNG)
   근처 다른 매물과 현재 매물의 정보비교 제공 및 다른 매물의 거래 내역들 제공
