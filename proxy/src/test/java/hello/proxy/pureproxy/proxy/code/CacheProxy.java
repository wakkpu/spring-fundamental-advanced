package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject { // 프록시는 실제 객체와 모양이 같아야 하므로 impl Subject
    // 클라이언트가 프록시를 호출하면 프록시는 실제 객체를 호출해야 함.
    // 따라서 내부에 실제 객체의 참조를 갖고 있어야 함.
    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String opertation() {
        log.info("프록시 호출");
        if(cacheValue == null) {
            cacheValue = target.opertation();
        }
        return cacheValue;
    }
}
