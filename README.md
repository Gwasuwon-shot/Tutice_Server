# Tutice_Server

32nd GoSopt WebJam 🌳Lesson 🔔Notification 🍎Service

</aside>
<hr>
</br>


<img width="450" height="400" src="https://github.com/Gwasuwon-shot/Tutice_Client/assets/100409061/d9ba9eb3-fbbc-4e11-9ebc-59ea2ec19f56"/>


🔔 <b> 쉬운 수업 관리로 열리는 정확한 나의 결실  🌳 <br/>
과외의 출결 체크부터 수업비 관리까지 한번의 클릭으로 쉽게 관리하는 서비스</b> <br />

</aside>
<hr>
</br>

#  🍊🍋 Team Gwasuwon BE

|![image](https://github.com/Gwasuwon-shot/Tutice_Server/assets/65851554/ccef89d8-487d-48ff-8614-a6285b3e7acd)|![image](https://github.com/Gwasuwon-shot/Tutice_Server/assets/65851554/cdc06d5a-b6b1-4d2a-bf37-556442dbc3ee)|
|:---|:---|
|🍊유수화🍊|🍋박소정🍋|



</aside>
<hr>
</br>

# 🧑‍🔧 Tech Stack
### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logoColor=white">  <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> 

### DB
<img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white"> <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">

### CI/CD
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/docker hub-2496ED?style=for-the-badge&logo=docker&logoColor=white"> 

### Deploy
<img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white"> 

### Develop Tool
<img src="https://img.shields.io/badge/intelliJ-000000?style=for-the-badge&logo=intellij idea&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> 

### Communicate Tool
<img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<br> 
<br>


</aside>
<hr>
</br>

# 🏠 server architecture


![image](https://github.com/Gwasuwon-shot/Tutice_Server/assets/65851554/b8587aef-7256-4778-8c72-0a700aa1d0bb)
</aside>
<hr>
</br>

# 📁 Folder 구조

```jsx
├── 📂 main
	├── 🗂️ resources
		├── 📕 application.yaml

	├── 📂 domain
		├── 📂 entity(엔티티)
		├── 📂 controller(컨트롤러 파일)
		├── 📂 repository(레포지토리 폴더)
		├── 📂 service(서비스 파일)
		├── 📂 exception(Exception class 파일)
			├─── 🗂️ advice

		├── 📂 dto(dto 파일)
			├── 🗂️ request
			├── 🗂️ response
			├── 🗂️ assembler

	├── 📂 common(공용 클래스 관리)
		├── 🗂️ entity(공통 엔티티)
		├── 🗂️ dto (공통 DTO)
		├── 🗂️ exception (공통 exception enum, BasicException)
		├── 🗂️ advice
		├── 🗂️ resolver

	├── 📂 external(외부 관리)
	├── 📂 config(외부 라이브러리)

├── 📂 test
```

</aside>
<hr>
</br>

# 💽 DB ERD


![image](https://github.com/Gwasuwon-shot/Tutice_Server/assets/65851554/f850fcb2-0519-4e99-adf0-ad1ae59df395)


</aside>
<hr>
</br>

# 📄 API Docs

api docs

</aside>
<hr>
</br>

# 🏃‍ 역할분담 & 구현 진척도

</aside>
<hr>
</br>

# 🗣️️ 컨벤션

> 💡 **동료들과 말투를 통일하기 위해 컨벤션을 지정합니다.**
> 오합지졸의 코드가 아닌, **한 사람이 짠 것같은 코드**를 작성하는 것이 추후 유지보수나 협업에서 도움이 됩니다. 내가 코드를 생각하면서 짤 수 있도록 해주는 룰이라고 생각해도 좋습니다!

## 👩‍💻 Coding Conventions

<details>
<summary>명명규칙(Naming Conventions)</summary>
<div markdown="1">

1. 이름으로부터 의도가 읽혀질 수 있게 쓴다.

2. 단수를 기본형으로 한다.

   - 기능 자체에서 단수, 복수를 구분하는 경우에만 복수 사용 ex. 다중삭제, 단일삭제

3. DB의 테이블, 클래스에는 `PascalCase`를 사용한다.

4. 변수, 메소드에는 `camelCase`를 사용한다.

5. DB의 테이블의 칼럼에는 `snake_case`를 사용한다.

6. 상수, enum에는 `UPPER_SNAKE_CASE`를 사용한다.

7. 메소드는 `crud + http method`(동사) + 명사 형태로 작성한다.

   - c : ex. `createUser`
   - r : ex. `getUser`
   - u : ex. `updateUser`
   - d : ex. `deleteUser`

8. 약어 사용은 최대한 지양한다.

9. 이름에 네 단어 이상이 들어가면 팀원과 상의를 거친 후 사용한다.
   </div>
   </details>

<details>
<summary>주석(Comment)</summary>
<div markdown="1">

1. 해당 메소드가 어디에 쓰이는지 설명한다.

2. 해당 분기문이 어떤 분기인지 설명한다.

3. 반복문에서 어떤 조건에서 반복되는지 설명한다.

4. 정렬하고 필터링할때 어떤 조건의 정렬과 필터링인지 설명한다.

</div>
</details>

<hr>
</br>

## 🌳 Branch

🌱 git branch 전략

`main branch` : 배포 단위 branch

`dev branch` : 주요 개발 branch, main merge 전 거치는 branch

`feat branch`: 각자 개발 branch

- 할 일 issue 등록 후 issue 번호와 isuue 이름으로 branch 생성 후 작업
  - ex) feat/#`issue num`-`isuue name(기능요약)`
- 해당 branch 작업 완료 후 PR 보내기
  - 항상 local에서 충돌 해결 후 → remote에 올리기
  - reviewer에 서로 tag후 code-review
  - comment 전 merge 불가!
  - review반영 후, 본인이 merge.

### branch 구조

```jsx
- main
- dev
- feat
   ├── #1-isuue name1
   └── #2-isuue name2
```

</aside>
<hr>
</br>

## 🧵 Commit Convention

<aside>
📍  git commit message convention

- 커밋은 한글로 작성한다.

`ex) (#13)🍊feat : 변경 내용 `

```plain
- 🍊 feat:      (수화가) 새로운 기능 구현
- 🍋 feat:      (소정이가) 새로운 기능 구현
- 🐛 fix:       버그, 오류 해결
- 🧹 chore:     src 또는 test 파일을 수정하지 않는 기타 변경 사항 ( 새로운 파일 생성, 파일 이동, 이름 변경 등 )
- ♻️ refactor:  버그 수정이나 기능 추가가 없는 코드 변경 ( 코드 구조 변경 등의 리팩토링 )
- 🏗️ build:    빌드 시스템 또는 외부에 영향을 미치는 변경 사항 종속성 ( 라이브러리 추가 등 )
- 📈 perf:      성능을 향상 시키기 위한 코드 변경
- 🧪 test:      테스트 추가 또는 이전 테스트 수정
- 📝 docs:      README나 WIKI 등의 문서 개정
- ⏪️ revert:    이전 커밋을 되돌리는 경우
- 📦 ci:      CI 구성 파일 및 스크립트 변경
- 🖇️ merge: 다른브렌치를 merge하는 경우
- 📌 init : Initial commit을 하는 경우
```
