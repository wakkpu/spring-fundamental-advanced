package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {
    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    /**
     * 변하는 부분 : 비즈니스 로직
     * 변하지 않는 부분 : 시간 측정
     * => 템플릿 메서드 패턴을 사용해서 변하는 부분과 변하지 않는 부분을 분리해보자
     */
    public void logic1() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();

        long resultTIme = endTime - startTime;

        log.info("resultTime={}", resultTIme);
    }

    public void logic2() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();

        long resultTIme = endTime - startTime;

        log.info("resultTime={}", resultTIme);
    }

    /**
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();

        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();

        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("새로운 비즈니스 로직 1 실행");
            }
        };
        template1.execute();
    }
}
