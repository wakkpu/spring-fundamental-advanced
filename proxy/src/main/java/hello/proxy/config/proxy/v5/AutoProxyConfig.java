package hello.proxy.config.proxy.v5;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.proxy.v3.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {
    /*@Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }*/

    /*
     애플리케이션 서버를 실행해보면, 스프링이 초기화되면서 의도하지 않은 로그들도 발생하는데,
     기존 포인트컷이 단순히 메서드 이름에 request, order, save만 포함되어 있으면 프록시 객체를
     생성하기 때문이다.

     따라서 패키지 경로와 메서드 이름까지 함께 지정할 수 있는 정밀한 포인트컷을 사용해야 한다.
     */

    /*@Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        *//*
         * : 모든 반환 타입
         hello.proxy.app.. : 해당 패키지와 그 하위 패키지
         *(..) : *는 모든 메서드 이름을 의미, (..)는 파라미터는 상관 없음을 의미
         *//*
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }*/

    /*
     하지만 이 역시도 문제가 있다. no-log를 호출했을 때 로그가 출력된다.
     */
    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
