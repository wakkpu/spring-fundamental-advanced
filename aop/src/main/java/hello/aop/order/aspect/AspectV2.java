package hello.aop.order.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // pointcut signature

    @Pointcut("execution(* *..*Service.*(..))") // 클래스 이름 패턴이 *Service
    private void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("allOrder() && allService()") // hello.aop.order패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
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
