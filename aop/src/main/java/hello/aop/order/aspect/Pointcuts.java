package hello.aop.order.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {} // pointcut signature

    @Pointcut("execution(* *..*Service.*(..))") // 클래스 이름 패턴이 *Service
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
