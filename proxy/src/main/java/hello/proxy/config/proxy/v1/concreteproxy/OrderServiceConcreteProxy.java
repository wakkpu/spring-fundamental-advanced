package hello.proxy.config.proxy.v1.concreteproxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        // 구체 클래스 프록시에서 불편한 점.
        // 부모 생성자를 호출해야 하는데, 부모 클래스의 기본 생성자가 없으므로 이런 식으로 우회
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }


    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
