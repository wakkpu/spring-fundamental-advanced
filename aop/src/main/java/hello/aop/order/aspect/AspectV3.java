package hello.aop.order.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {
    @Around("hello.aop.order.aspect.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    /* 메서드 실행 전후에 작업을 수행
     * 가장 강력한 어드바이스이다
     * - JoinPoint 실행 여부 선택
     * - 전달값 변환
     * - 반환값 변환
     * - 예외 변환
     *
     * 어드바이스의 첫번째 파라미터는 ProceedingJoinPoint를 사용해야 한다.
     * - proceed()를 통해 대상을 실행한다.
     * - proceed()를 여러 번 실행해 재시도할 수도 있다.
     */
    @Around("hello.aop.order.aspect.Pointcuts.orderAndService()") // hello.aop.order패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[begin transaction] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[commit transaction] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[rollback transaction] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[resource release] {}", joinPoint.getSignature());
        }
    }
}
