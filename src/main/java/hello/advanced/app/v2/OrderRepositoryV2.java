package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {
    private final HelloTraceV2 helloTrace;

    public void save(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = helloTrace.beginSync(traceId, "OrderRepository.save()");
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            helloTrace.end(status);
        } catch (Exception e) {
            helloTrace.exception(status, e);
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
