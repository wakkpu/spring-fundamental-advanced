package hello.proxy;

import hello.proxy.config.proxy.v1.concreteproxy.ConcreteProxyConfig;
import hello.proxy.config.proxy.v1.interfaceproxy.InterfaceProxyConfig;
import hello.proxy.config.proxy.v2.handler.DynamicProxyBasicConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/*
 * "`AppConfig.class`를 스프링 빈으로 등록한다"
 * 단, hello.proxy.app 이하의 경로의 패키지에 대해서만 component scan의 대상이 된다
 */
//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
@Import(DynamicProxyBasicConfig.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
