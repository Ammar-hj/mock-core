package com.platform.mockcore.services.impl;

import com.platform.mockcore.Interceptor.MdcManager;
import com.platform.mockcore.common.HttpInterfaceCacheManager;
import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.exception.MockException;
import com.platform.mockcore.model.HttpMockContext;
import com.platform.mockcore.model.dao.HttpInterfaceDao;
import com.platform.mockcore.model.request.HttpInterfaceKeyReq;
import com.platform.mockcore.model.request.HttpInterfaceReq;
import com.platform.mockcore.services.HttpAsyncMockService;
import com.platform.mockcore.services.HttpMockService;
import com.platform.mockcore.services.HttpSyncMockService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class HttpMockServiceImpl implements HttpMockService {
    private static final Logger logger = LoggerFactory.getLogger(HttpMockServiceImpl.class);
    @Autowired
    HttpInterfaceCacheManager httpInterfaceCacheManager;
    @Autowired
    HttpInterfaceDao httpInterfaceDao;
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    HttpSyncMockService syncMockService;
    @Autowired
    HttpAsyncMockService asyncMockService;

    @Override
    public void mock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpMockContext httpMockContext = new HttpMockContext();
        //加载数据
        HttpInterfaceKeyReq httpInterfaceKeyReq = new HttpInterfaceReq();
        httpInterfaceKeyReq.setRequestMethod(request.getMethod());
        httpInterfaceKeyReq.setRequestUri(request.getRequestURI());
        httpMockContext.setHttpInterfaceReq(loadHttpInterfaceReq(httpInterfaceKeyReq));

        //同步请求
        syncMockService.mock(httpMockContext, request, response);

        //异步请求
        if (BooleanUtils.isTrue(httpMockContext.getHttpInterfaceReq().getNeedAsyncCallback())) {
            String traceId = MDC.get(MdcManager.MDC_TRACE_ID_KEY);
            MockHttpServletRequest mockHttpServletRequest = buildMockRequest(request, buildHttpBody(request));
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        MDC.put(MdcManager.MDC_TRACE_ID_KEY, traceId);
                        asyncMockService.mock(httpMockContext, mockHttpServletRequest);
                        MDC.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private HttpInterfaceReq loadHttpInterfaceReq(HttpInterfaceKeyReq httpInterfaceKeyReq) {
        logger.info("Loading data from redis...");
        HttpInterfaceReq httpInterfaceReq = httpInterfaceCacheManager.get(httpInterfaceKeyReq);
        if (httpInterfaceReq == null) {
            logger.info("Loading data from DB...");
            httpInterfaceReq = httpInterfaceDao.queryByKey(httpInterfaceKeyReq);
            if (httpInterfaceReq == null) {
                throw new MockException(RespCodeEnum.NOT_FOUND_INTERFACE);
            }
            httpInterfaceCacheManager.set(httpInterfaceReq);
        }

        if (BooleanUtils.isNotTrue(httpInterfaceReq.getStart())) {
            throw new MockException(RespCodeEnum.INTERFACE_NOT_OPEN);
        }
        return httpInterfaceReq;
    }

    private String buildHttpBody(HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();
        String method = request.getMethod();
        String requestEncoding = request.getCharacterEncoding();

        boolean isFormPost = (contentType != null && contentType.contains("x-www-form-urlencoded") &&
                HttpMethod.POST.matches(method));
        if (isFormPost) {
            // 读取getParameterMap造出body
            StringBuilder content = new StringBuilder();
            Map<String, String[]> form = request.getParameterMap();
            for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext(); ) {
                String name = nameIterator.next();
                List<String> values = Arrays.asList(form.get(name));
                for (Iterator<String> valueIterator = values.iterator(); valueIterator.hasNext(); ) {
                    String value = valueIterator.next();
                    content.append(URLEncoder.encode(name, requestEncoding));
                    if (value != null) {
                        content.append('=');
                        content.append(URLEncoder.encode(value, requestEncoding));
                        if (valueIterator.hasNext()) {
                            content.append('&');
                        }
                    }
                }
                if (nameIterator.hasNext()) {
                    content.append('&');
                }
            }
            return content.toString();
        } else {
            return IOUtils.toString(request.getInputStream(), requestEncoding);
        }
    }

    private MockHttpServletRequest buildMockRequest(HttpServletRequest request, String body) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setParameters(request.getParameterMap());

        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = (String) headers.nextElement();
            mockHttpServletRequest.addHeader(key, request.getHeader(key));
        }
        mockHttpServletRequest.setMethod(request.getMethod());
        mockHttpServletRequest.setRequestURI(request.getRequestURI());
        mockHttpServletRequest.setRemoteAddr(request.getRemoteAddr());
        mockHttpServletRequest.setRemoteHost(request.getRemoteHost());
        mockHttpServletRequest.setRemotePort(request.getRemotePort());
        mockHttpServletRequest.setRemoteUser(request.getRemoteUser());
        mockHttpServletRequest.setAttribute("body", body);
        return mockHttpServletRequest;
    }
}
