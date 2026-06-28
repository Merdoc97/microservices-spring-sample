package pl.piomin.microservices.account.config;

import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class VirtualThreadsConfig {
    @Bean
    public UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer() {
        int maxThreads = 10000;
        int maxQueue = 2000;

        ExecutorService executor = new ThreadPoolExecutor(
                maxThreads,
                maxThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(maxQueue),
                Thread.ofVirtual().factory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        return deploymentInfo -> deploymentInfo.setExecutor(executor);
    }
}
