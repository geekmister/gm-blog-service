package cn.gk.gmblog.adm.config;

import cn.gk.gmblog.adm.interceptor.HttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: Bean配置
 * @Bean: Web容器配置
 * @Bean: 异步线程池配置
 * @Bean: RestTemplate配置
 * @Author: Mr.geek
 * @Date: 2022/3/29.
 */
@Configuration
@EnableAsync
public class BeanConfig {

    @Bean
    WebMvcConfigurer configurer(){
        return new WebMvcConfigurerAdapter(){
            @Override
            public void configureAsyncSupport (AsyncSupportConfigurer configurer) {
                ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();

                // 核心线程数
                t.setCorePoolSize(10);
                // 最大线程数
                t.setMaxPoolSize(40);
                // 队列容量
                t.setQueueCapacity(80);
                // 设置线程活跃时间（秒）
                t.setKeepAliveSeconds(120);
                // 设置核心是否可以超时
                t.setAllowCoreThreadTimeOut(true);
                // 线程池前缀名称
                t.setThreadNamePrefix("Request thread pool-");
                // 设置拒绝策略
                t.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                // 等待所有任务结束后再关闭线程池
                t.setWaitForTasksToCompleteOnShutdown(true);
                // 异步线程池初始化
                t.initialize();

                configurer.setTaskExecutor(t);
            }
        };
    }

    /**
     * @name: asyncThreadPool
     * @Description: 异步任务线程池
     * @Return: t {ThreadPoolTaskExecutor} 异步线程池
     * @Author: geekmister
     * @Date: 2021/10/19
     */
    @Bean(name = "asyncThreadPool")
    public ThreadPoolTaskExecutor asyncThreadPool() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();

        // 核心线程数
        t.setCorePoolSize(5);
        // 最大线程数
        t.setMaxPoolSize(20);
        // 队列容量
        t.setQueueCapacity(40);
        // 设置线程活跃时间（秒）
        t.setKeepAliveSeconds(60);
        // 设置核心是否可以超时
        t.setAllowCoreThreadTimeOut(true);
        // 线程活跃时间（秒）
        t.setKeepAliveSeconds(120);
        // 线程池前缀名称
        t.setThreadNamePrefix("async thread pool-");
        // 设置拒绝策略
        t.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        t.setWaitForTasksToCompleteOnShutdown(true);
        // 异步线程池初始化
        t.initialize();

        return t;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate =
                new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HttpRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
