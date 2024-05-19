package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    public void external() {
        log.info("call external");
        this.internal(); // 이 this는 프록시 객체가 아닌 원본 객체이므로 aop가 적용될 수 없다.
    }

    public void internal() {
        log.info("call internal");
    }
}
