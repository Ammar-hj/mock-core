package com.platform.mockcore.services.impl;

import com.platform.mockcore.enums.ConfigMode;
import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.exception.MockException;
import com.platform.mockcore.model.HttpMockContext;
import com.platform.mockcore.model.request.HttpInterfaceReq;
import com.platform.mockcore.services.GroovyService;
import com.platform.mockcore.services.HttpAsyncMockService;
import groovy.lang.Binding;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * 异步处理
 */
@Service
public class HttpAsyncMockServiceImpl implements HttpAsyncMockService {
    private static final Logger logger = LoggerFactory.getLogger(HttpAsyncMockServiceImpl.class);

    @Autowired
    GroovyService groovyService;

    @Override
    public void mock(HttpMockContext context, HttpServletRequest request) throws Exception {
        //异步配置超时
        HttpInterfaceReq httpInterfaceReq = context.getHttpInterfaceReq();
        if (httpInterfaceReq.getAsyncDelay() > 0) {
            try {
                logger.info("request sync timeout start...");
                Thread.sleep(httpInterfaceReq.getAsyncDelay());
                logger.info("request sync timeout end...");
            } catch (InterruptedException e) {
                throw new MockException(RespCodeEnum.THREAD_SLEEP_ERROR);
            }
        }
        //根据设置获取回调接口，构造请求
        URL url = new URL(httpInterfaceReq.getCallbackRequestUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod(httpInterfaceReq.getCallbackRequestMethod());
        httpInterfaceReq.getCallbackRequestHeaderList().forEach(httpHeader
                -> httpURLConnection.setRequestProperty(httpHeader.getName(), httpHeader.getValue()));

        ConfigMode configMode = httpInterfaceReq.getConfigMode();
        String requestData = null;
        if (configMode == ConfigMode.TEXT) {
            requestData = httpInterfaceReq.getCallbackRequestBody();
        } else if (configMode == ConfigMode.GROOVY) {
            Binding binding = new Binding();
            binding.setProperty("request", request);
            binding.setProperty("httpURLConnection", httpURLConnection);
            requestData = groovyService.exec(binding, httpInterfaceReq.getAsyncScript());
        } else if (configMode == ConfigMode.GROOVY_TEMPLATE_SWITCH_CASE) {
            Binding binding = new Binding();
            binding.setProperty("request", request);
            binding.setProperty("httpURLConnection", httpURLConnection);
            requestData = groovyService.exec(binding, context.getHttpInterfaceBranchReq().getAsyncScript());
        } else {
            throw new MockException(RespCodeEnum.UNKNOWN_BRANCH);
        }

        logger.info("RequestData = {}", requestData);
        if (StringUtils.isNotBlank(requestData)) {
            if (HttpMethod.GET.matches(httpInterfaceReq.getCallbackRequestMethod())) {
                try {
                    Field field = URLConnection.class.getDeclaredField("url");
                    field.setAccessible(true);
                    field.set(httpURLConnection,
                            new URL(String.format("%s?%s", httpInterfaceReq.getCallbackRequestUrl(), requestData)));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new MockException(RespCodeEnum.UNKNOWN_ERROR);
                }
            } else {
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(requestData.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        } else {
            logger.info("Async RequestData 为空");
            return;
        }
        int responseCode = httpURLConnection.getResponseCode();
        logger.info("Async ResponseCode = {}", responseCode);
        String responseData = IOUtils.toString(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
        logger.info("Async ResponseData = {}", responseData);
    }
}
