package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderService;
    private final LogTrace helloTrace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = helloTrace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            helloTrace.end(status);
            return "ok";
        } catch (Exception e) {
            helloTrace.exception(status, e);
            throw e; // 비즈니스 로직의 동작에 영향을 주면 안되므로 예외를 다시 던져야 한다
        }
    }
}
