package hello.proxy.config.proxy.v1;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderControllerV1 orderControllerProxy(LogTrace logTrace) {
        OrderControllerV1Impl orderControllerImpl = new OrderControllerV1Impl(orderServiceProxy(logTrace));
        return new OrderControllerInterfaceProxy(orderControllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderServiceProxy(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceImpl = new OrderServiceV1Impl(orderRepositoryProxy(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryProxy(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }
}
