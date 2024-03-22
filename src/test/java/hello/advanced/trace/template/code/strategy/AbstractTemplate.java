package hello.advanced.trace.template.code.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {
    /**
     * execute가 호출됐을 때 call을 호출하게 되고,
     * 호출되는 call은 오버라이드된 메서드를 따르게 된다.
     * -> 잘 변하지 않는 코드와 잘 변하는 코드를 분리함으로써
     * 코드의 중복을 제거하고, 변경 사항이 생겼을 때 해당 오버라이드된 메서드만
     * 수정함으로써 단일 책임 원칙을 준수할 수 있다.
     */
    public void execute() {
        long startTime = System.currentTimeMillis();
//        log.info("비즈니스 로직 실행"); // 변하는 부분
        call();

        long endTime = System.currentTimeMillis();

        long resultTIme = endTime - startTime;

        log.info("resultTime={}", resultTIme);
    }

    protected abstract void call();
}
