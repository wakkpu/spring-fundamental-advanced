package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {
    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
    /*
     공통 로직1과 공통 로직2는 호출하는 메서드만 다르고 전체 흐름이 완전히 같다.
     공통 로직1과 공통 로직2를 하나의 메서드로 뽑아서 합칠 수 있을까?
     쉬워보이지만 생각보다 쉽지 않다. 중간에 호출하는 메서드가 다르기 때문이다.
     호출하는 메서드인 callA()와 callB() 이 부분만 동적으로 처리할 수 있다면
     문제를 해결할 수 있을 듯 하다.
     -> 리플렉션
     */
    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        // 공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스의 메타 정보
        /* 내부 클래스는 구분을 위해 $를 사용한다 */
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA의 메타 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB의 메타 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }
}
