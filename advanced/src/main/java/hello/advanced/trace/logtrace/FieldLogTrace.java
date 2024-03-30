package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생

    private void syncTraceId() {
        if(traceIdHolder == null) { // 최초 호출이라면 새로 생성
            traceIdHolder = new TraceId();
        } else { // 이전 로그가 있다면 level++
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel()) { // level == 0이면 traceId 삭제
            traceIdHolder = null;
        } else { // level != 0이면 level--
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    public void complete(TraceStatus traceStatus, Exception e) {
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - traceStatus.getStartTimeMs();
        TraceId traceId = traceStatus.getTraceId();
        if(e == null) {
            log.info("[{}] {}{} time={}ms",
                    traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    traceStatus.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    traceStatus.getMessage(),
                    resultTimeMs,
                    e.toString());
        }
        releaseTraceId();
    }

    // depth(level) 만큼 띄어쓰기하여 depth 표시
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<level; i++) {
            sb.append((i == level-1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
