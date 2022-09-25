package cn.gk.gmblog.adm.interceptor;

import cn.gk.gmblog.adm.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description: http请求拦截器
 * @Author: Mr.geek
 * @Date: 2022/1/20.
 */
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Token", ApplicationConfig.TOKEN);
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    /**
     * @name: traceRequest
     * @Description: 打印请求
     * @Param: request {HttpRequest} 请求
     * @Param: body {byte[]} 请求体
     * @Return:
     * @Author: geekmister
     * @Date: 2022/4/21
     */
    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.info("===========================request begin================================================");
        logger.info("URI         : {}", request.getURI());
        logger.info("Method      : {}", request.getMethod());
        logger.info("Headers     : {}", request.getHeaders());
        logger.info("Request body: {}", new String(body, "UTF-8"));
        logger.info("==========================request end================================================");
    }

    /**
     * @name: traceResponse
     * @Description: 打印响应
     * @Param: response {ClientHttpResponse} 响应
     * @Return:
     * @Author: geekmister
     * @Date: 2022/4/21
     */
    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }
        logger.info("============================response begin==========================================");
        logger.info("Status code  : {}", response.getStatusCode());
        logger.info("Status text  : {}", response.getStatusText());
        logger.info("Headers      : {}", response.getHeaders());
        logger.info("Response body: {}", inputStringBuilder.toString());//WARNING: comment out in production to improve performance
        logger.info("=======================response end=================================================");
    }
}
