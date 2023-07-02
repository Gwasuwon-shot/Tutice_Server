# Tutice_Server
32nd GoSopt WebJam 🌳Lesson 🔔Notification 🍎Service



</aside>
<hr>
</br>

# 🏠 server architecture

서버 아키텍처 이미지!

</aside>
<hr>
</br>

# 📁 Folder 구조

```jsx
├── 📂 main
	├── 🗂️ resources
		├── 📕 application.yml

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
		├── 🗂️ exception (공통 exception enum)
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


디비 erd 이미지

</aside>
<hr>
</br>

# 📄 API Docs

api docs

</aside>
<hr>
</br>


# 🙆‍♀️ 역할분담 & 구현 진척도



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

- ex)

  ```jsx
  // bad
  function q() {
    // ...stuff...
  }

  // good
  function query() {
    // ..stuff..
  }
  ```

2. 오브젝트, 함수, 그리고 인스턴스에는 `camelCase`를 사용한다.

- ex)

  ```jsx
  // bad
  const OBJEcttsssss = {};
  const this_is_my_object = {};
  function c() {}

  // good
  const thisIsMyObject = {};
  function thisIsMyFunction() {}
  ```

3. 클래스나 constructor에는 `PascalCase`를 사용한다.

- ex)

  ```jsx
  // bad
  function user(options) {
    this.name = options.name;
  }

  const bad = new user({
    name: "nope",
  });

  // good
  class User {
    constructor(options) {
      this.name = options.name;
    }
  }

  const good = new User({
    name: "yup",
  });
  ```

4. 함수 이름은 동사 + 명사 형태로 작성한다.
   ex) `postUserInformation( )`
5. 약어 사용은 최대한 지양한다.
6. 이름에 네 단어 이상이 들어가면 팀원과 상의를 거친 후 사용한다
   </div>
   </details>





<details>
<summary>주석(Functions)</summary>
<div markdown="1">

1. 화살표 함수를 사용한다.

- ex)

  ```jsx
  var arr1 = [1, 2, 3];
  var pow1 = arr.map(function(x) {
    // ES5 Not Good
    return x * x;
  });

  const arr2 = [1, 2, 3];
  const pow2 = arr.map((x) => x * x); // ES6 Good
  ```

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

`ex) feat(변경한 파일) : 변경 내용 (/#issue num)`

```plain
- ✨ feat:      새로운 기능 구현
- 🐛 fix:       버그, 오류 해결
- 🧹 chore:     src 또는 test 파일을 수정하지 않는 기타 변경 사항 ( 새로운 파일 생성, 파일 이동, 이름 변경 등 )
- ♻️ refactor:  버그 수정이나 기능 추가가 없는 코드 변경 ( 코드 구조 변경 등의 리팩토링 )
- 💎 style:     코드의 의미에 영향을 미치지 않는 변경 사항 ( 코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음 )
- 🏗️ build:    빌드 시스템 또는 외부에 영향을 미치는 변경 사항 종속성 ( 라이브러리 추가 등 )
- 📈 perf:      성능을 향상 시키기 위한 코드 변경
- 🧪 test:      테스트 추가 또는 이전 테스트 수정
- 📝 docs:      README나 WIKI 등의 문서 개정
- ⏪️ revert:    이전 커밋을 되돌리는 경우
- 📦 ci:      CI 구성 파일 및 스크립트 변경
- Merge: 다른브렌치를 merge하는 경우
- Init : Initial commit을 하는 경우
```

