package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {
    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer(); // CGLIB은 Enhancer를 사용해서 프록시를 생성한다
        enhancer.setSuperclass(ConcreteService.class); // CGLIB은 구체 클래스를 `상속`받아서 프록시를 생성
        enhancer.setCallback(new TimeMethodInterceptor(target)); // 프록시에 적용할 실행 로직 할당

        ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시 생성

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}
