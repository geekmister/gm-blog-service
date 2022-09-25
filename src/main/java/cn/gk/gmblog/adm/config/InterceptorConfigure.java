package cn.gk.gmblog.adm.config;

import cn.gk.gmblog.adm.interceptor.OAuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 拦截器配置
 * @Author: Mr.geek
 * @Date: 2021/11/10.
 */
@EnableWebMvc
@Configuration
public class InterceptorConfigure implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfigure.class);

    @Bean
    public OAuthInterceptor oAuthInterceptor() {
        return new OAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("exec function addInterceptors...");

        // 定义拦截请求
        String[] interceptOAuthPath = {"/**"};
        // 定义放行请求
        String[] excludeOAuthPath = {"/auth/**"};
        registry.addInterceptor(oAuthInterceptor())
                .addPathPatterns(interceptOAuthPath)
                .excludePathPatterns(excludeOAuthPath);

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
