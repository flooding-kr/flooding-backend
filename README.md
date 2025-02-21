## 도메인 용어

| 수행되는 로직 | 단어      |
|---------|---------|
| 수정하기    | Update  |
| 조회하기    | Fetch   |
| 삭제하기    | Remove  |
| 생성하기    | Create  |
|로그인| SignIn  |
|로그아웃| SignOut |
|회원가입| SignUp  |
|인증하기| Verify  |
|재발급|Reissue|
|취소하기|Cancel|
|예약하기|Reserve|
|탈퇴하기|Withdraw|

## IDE 설정

`IntelliJ IDEA`를 사용하시는 경우, 우측 상단의 설정에서 Plugins - Marketplace에서 Ktlint를 설치해주세요.

## Cloudflare R2

AWS S3 2.30.x는 R2 API를 완벽하게 지원하지 않기 때문에, 다음과 같은 코드를 추가해야 합니다.

```kotlin
.responseChecksumValidation(ResponseChecksumValidation.WHEN_REQUIRED)
.requestChecksumCalculation(RequestChecksumCalculation.WHEN_REQUIRED)
```
