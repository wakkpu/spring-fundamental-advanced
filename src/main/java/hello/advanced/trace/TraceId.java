package hello.advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TraceId {
    private String id;

    // method 호출의 depth를 의미
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level+1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level-1);
    }

    public boolean isFirstLevel() {
        return this.level == 0;
    }
}
