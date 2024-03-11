package hello.advanced.app.v3;


import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
    private final OrderRepositoryV3 orderRepository;
    private final LogTrace helloTrace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = helloTrace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            helloTrace.end(status);
        } catch (Exception e) {
            helloTrace.exception(status, e);
            throw e;
        }
    }
}
