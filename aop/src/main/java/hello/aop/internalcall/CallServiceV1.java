package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    // 생성자 주입을 하려 하면, 자기 자신을 생성할 때 자기 자신이 필요하므로 순환 참조가 된다.
    // 따라서 생성 이후 setter 주입
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 프록시의 internal()을 호출한다
    }

    public void internal() {
        log.info("call internal");
    }
}
