# 💵 FundFun

> 초보 투자자와 펀드매니저를 위한 크라우드 펀딩 펀드 시스템

KB국민은행 IT아카데미 IT's Your Life 3기 2반 2조 FundFun<br>개발 기간 : 2023.05.08 ~ 2023.05.31

## 💡 Introduction
펀드매니저가 되기 위해서는 운용 담당 업무에 종사할 기회를 가져야 합니다. 그런데 펀드매니저는 대부분 경력직 위주로 선발하기 때문에, 경험이 없는 초보 펀드매니저들에게는 기회가 쉽사리 주어지지 않습니다.

또한 투자자들이 직접 포트폴리오를 구성하고 투자하는 서비스인 다이렉트 인덱싱 솔루션이 투자자들 사이에서 트렌드가 되고 있습니다. 이러한 트렌드에 발맞춰 저희 FundFun은 개인이 스스로 상품을 구성하고 투자하는 형태의 서비스를 개발하고자 했습니다.

FundFun을 통해 투자자 누구나 자신의 전문 지식을 살려 투자 상품에 대한 아이디어를 제시할 수 있고, 경력이 없는 초보 펀드매니저는 포트폴리오 구성 및 운용 경험을 쌓을 수 있습니다. 또한 투표에서 당선된 포트폴리오는 실제 펀드 상품이 되어 투자자들의 투자를 받을 수 있습니다.



## 📚 Stacks
### Environment
<img  src="https://img.shields.io/badge/windows-0078D6?style=for-the-badge&logo=windows&logoColor=white"> <img  src="https://img.shields.io/badge/macOS-000000?style=for-the-badge&logo=macos&logoColor=white"> <img src="https://img.shields.io/badge/intellijIdea-000000?style=for-the-badge&logo=intellijIdea&logoColor=white"> <img src="https://img.shields.io/badge/sqlDeveloper-666666?style=for-the-badge&logo=sqlDeveloper&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">


### Development
<img  src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img  src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">

### Communication
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> <img src="https://img.shields.io/badge/gooleDrive-4285F4?style=for-the-badge&logo=googleDrive&logoColor=white"> <img src="https://img.shields.io/badge/googleMeet-00897B?style=for-the-badge&logo=googleMeet&logoColor=white">

## ✨ Main Function
#### 📃 아이디어의 가상품화
회원가입이 된 사용자라면 누구나 아이디어 게시판에 투자 아이디어를 자유롭게 올릴 수 있고, 회원들이 누른 좋아요 수가 10개 이상이 되면 해당 글의 상태가 상품이 될 수 있는 가능성이 있는 글이라는 의미의 가상품(preproduct)으로 변경됩니다.
![아이디어게시판](https://github.com/fundfun/fund_fund/assets/103356049/a06dd8f0-dede-4d38-bc0b-b3234a8fe05b)
![아이디어조회](https://github.com/fundfun/fund_fund/assets/103356049/e067d338-11e1-465b-aa31-47b8496d4fcc)

#### 📃 포트폴리오 투표
아이디어가 가상품으로 변경되면 포트폴리오 목록 버튼이 활성화됩니다. 펀드매니저들은 게시물의 내용을 기반으로 투자 포트폴리오를 구성해 올릴 수 있고, 투자자들은 포트폴리오를 읽어보고 가장 투자하고 싶은 포트폴리오에 투표할 수 있습니다. 기간 내에 가장 많은 표를 받은 포트폴리오가 실제 펀드 상품으로 출시됩니다.
![투표](https://github.com/fundfun/fund_fund/assets/103356049/96591adc-6e6e-483b-8b16-5c7545b42b27)

#### 📃 펀드 상품에 투자
펀딩 진행중인 펀드 상품에 원하는 금액만큼 투자할 수 있습니다.
![상품상세](https://github.com/fundfun/fund_fund/assets/103356049/616f35a0-e436-41e4-87d2-cd0bac8c8087)
![상품](https://github.com/fundfun/fund_fund/assets/103356049/7b74bf78-1aa9-44f9-ad0b-43421c4932e8)
![투자](https://github.com/fundfun/fund_fund/assets/103356049/d11c7bc8-94aa-41da-9d59-481920c55a59)

#### 📃 충전금 충전
충전하기 페이지에서 원하는 금액만큼 충전금을 충전할 수 있습니다.
![충전금결제](https://github.com/fundfun/fund_fund/assets/103356049/a4e0f4fb-282a-46d2-88de-b7b1fe391e81)

#### 📃 회원 관리
일반 회원과 소셜 로그인 회원 모두 FundFun을 사용할 수 있습니다.

![로그인](https://github.com/fundfun/fund_fund/assets/103356049/a957cad9-d009-45ed-b143-692705390bf4)


## 👪 Team FundFun
| 이경원(PM)                              | 김혜인                                              | 이우엽                                        | 이채림                                    | 전상희                                              |
|--------------------------------------|--------------------------------------------------|--------------------------------------------|----------------------------------------|--------------------------------------------------|
| img                                  | img                                              | img                                        | img                                    | img                                              |
| - User <br> - Authority <br> - Alarm | - Post <br> - Portfolio <br> - Vote <br> - Reply | - Product <br> - Order <br> - Payment      | - Product <br> - Order <br> - Payment  | - Post <br> - Portfolio <br> - Vote <br> - Reply |
| [@yeoooo](https://github.com/yeoooo) | [@hen-ni](https://github.com/hen-ni)             | [@leewooyup](https://github.com/leewooyup) | [@lee5917](https://github.com/lee5917) | [@ybwi0912](https://github.com/ybwi0912)         |
