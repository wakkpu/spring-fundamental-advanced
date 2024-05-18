package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/*
 spring.aop.proxy-target-class=true -> CGLIB : 인터페이스가 있어도 구체 클래스를 상속 받아 프록시 생성
 spring.aop.proxy-target-class=false -> JDK Dynamic Proxy : 인터페이스 필수, 인터페이스를 구현한 프록시 생성
 */

@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
@Import({ThisTargetTest.ThisTargetAspect.class})
public class ThisTargetTest {
    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        // this : 스프링 빈으로 등록된 프록시 객체를 대상으로 포인트컷을 매칭. 부모 타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // target : 실제 타겟 객체를 대상으로 포인트컷을 매칭. 부모 타입 허용 X
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}

