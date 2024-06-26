# 프록시 기술과 한계 - 타입 캐스팅
JDK 동적 프록시와 CGLIB를 사용해서 AOP 프록시를 만드는 방법에는 각각 장단점이 있다.
JDK 동적 프록시는 인터페이스가 필수이고, 인터페이스를 기반으로 프록시를 생성한다.
CGLIB는 구체 클래스를 기반으로 프록시를 생성한다.

물론 인터페이스가 없고 구체 클래스만 있는 경우에는 CGLIB를 사용해야 한다.
그런데 인터페이스가 있는 경우에는 JDK 동적 프록시나 CGLIB 둘 중에 하나를 선택할 수 있다.

스프링이 프록시를 만들 때 제공하는 `ProxyFactory`에 `proxyTargetClass` 옵션에 따라 둘 중 하나를 선택해서 프록시를 만들 수 있다.

### JDK 동적 프록시의 한계
인터페이스 기반으로 프록시를 생성하는 JDK 동적 프록시는 구체 클래스로 타입 캐스팅이 불가능한 한계가 있다.

프록시를 캐스팅할 일이 많지 않을 것 같은데 큰 이슈일까? 의존 관계 주입 시에 문제가 될 수 있다.

### CGLIB의 한계
- 대상 클래스의 기본 생성자 필수
- 생성자 2번 호출 문제
- `final` 키워드 클래스, 메서드 사용 불가