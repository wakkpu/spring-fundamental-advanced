# 포인트컷 지시자
AspectJ가 제공하는 포인트컷을 표현하기 위한 포인트컷 표현식

### 포인트컷 지시자의 종류
- execution : 메서드 실행 조인 포인트를 매칭한다
- within : 특정 타입 내의 조인 포인트를 매칭한다
- args : 인자가 주어진 타입의 인스턴스인 조인 포인트
- this : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
- target : Target 객체(스프링 AOP 프록시가 가르키는 실제 대상)를 대상으로 하는 조인 포인트
- @target : 실행 객체의 클래스에 주어진 타입의 어노테이션이 있는 조인 포인트
- @annotation : 메서드가 주어진 어노테이션을 가지고 있는 조인 포인트를 매칭
- @args : 전달된 실제 인수의 런타임 타입의 어노테이션을 갖는 조인 포인트
- bean : 스프링 전용 포인트컷 지시자, 빈의 이름으로 포인트컷을 지정한다

### execution

execution 문법
```
execution(modifiers-pattern? ret-type-patten declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)

execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
```

- 메서드 실행 조인 포인트를 매칭한다
- ?는 생략 가능하다
- `*` 같은 패턴을 지정할 수 있다
- 패키지에서 `.` 은 해당 패키지만을, `..`은 해당 패키지와 그 하위 패키지도 포함한다는 의미이다