package hello.aop.order.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV5 {
    /* JoinPoint 실행 전
     * @Around와 다르게 작업의 흐름을 변경할 수는 없다
     * @Around는 ProceedingJoinPoint.proceed()를 호출해야 다음 대상이 호출된다.
     * 하지만 @Before는 메서드 종료 시 자동으로 다음 타겟이 호출된다.
     * 예외가 발생하면 다음 코드는 호출되지 않는다.
     */
    @Before("hello.aop.order.aspect.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[begin transaction] {}", joinPoint.getSignature());
    }

    /* 메서드 실행이 정상적으로 반환될 때 실행
     * returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
     * returning 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행한다.
     * @Around와 다르게 반환되는 객체를 변경할 수 없다. 반환 객체를 변경하려면 @Around를 사용해야 한다.
     */
    @AfterReturning(value = "hello.aop.order.aspect.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[commit transaction] {}", joinPoint.getSignature());
    }

    /* 메서드 실행이 예외를 던져서 종료될 때 실행
     * throwing 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다
     * throwing 절에 지정된 타입과 맞는 예외를 대상으로 실행한다
     */
    @AfterThrowing(value = "hello.aop.order.aspect.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[rollback transaction] {}", joinPoint.getSignature());
    }

    /* 메서드 실행이 종료되면 실행한다 (finally처럼 동작한다고 생각)
     * 정상 및 예외 반환 조건을 모두 처리한다.
     * 일반적으로 리소스 및 유사한 목적을 해제하는 데 사용한다.
     */
    @After(value = "hello.aop.order.aspect.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[resource release] {}", joinPoint.getSignature());
    }
}
