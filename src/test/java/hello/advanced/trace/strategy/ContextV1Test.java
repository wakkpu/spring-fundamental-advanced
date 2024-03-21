package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.ContextV1;
import hello.advanced.trace.strategy.code.Strategy;
import hello.advanced.trace.strategy.code.StrategyLogic1;
import hello.advanced.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

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

    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };

        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = () -> log.info("비즈니스 로직 1 실행");

        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }
}
