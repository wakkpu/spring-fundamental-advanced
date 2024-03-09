package hello.advanced.app.v1;


import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 helloTrace;

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
