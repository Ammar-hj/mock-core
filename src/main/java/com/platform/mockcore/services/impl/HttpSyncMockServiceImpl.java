package com.platform.mockcore.services.impl;

import com.platform.mockcore.enums.ConfigMode;
import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.exception.MockException;
import com.platform.mockcore.model.HttpMockContext;
import com.platform.mockcore.model.request.HttpInterfaceBranchReq;
import com.platform.mockcore.model.request.HttpInterfaceReq;
import com.platform.mockcore.services.HttpSyncMockService;
import groovy.lang.Binding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 同步请去处理
 */
@Service
public class HttpSyncMockServiceImpl implements HttpSyncMockService {
    private static final Logger logger = LoggerFactory.getLogger(HttpSyncMockServiceImpl.class);

    @Autowired
    GroovyServiceImpl groovyService;

    @Override
    public void mock(HttpMockContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //同步配置超时
        HttpInterfaceReq httpInterfaceReq = context.getHttpInterfaceReq();
        if (httpInterfaceReq.getSyncDelay() > 0) {
            try {
                logger.info("request sync timeout start...");
                Thread.sleep(httpInterfaceReq.getSyncDelay());
                logger.info("request sync timeout end...");
            } catch (InterruptedException e) {
                throw new MockException(RespCodeEnum.THREAD_SLEEP_ERROR);
            }
        }

        //构造参数处理对应类型
        ConfigMode configMode = httpInterfaceReq.getConfigMode();
        String responseBody = null;
        if (configMode == ConfigMode.TEXT) {
            responseBody = httpInterfaceReq.getResponseBody();
        } else if (configMode == ConfigMode.GROOVY) {
            Binding binding = new Binding();
            binding.setProperty("request", request);
            binding.setProperty("response", response);
            responseBody = groovyService.exec(binding, httpInterfaceReq.getSyncScript());
        } else if (configMode == ConfigMode.GROOVY_TEMPLATE_SWITCH_CASE) {
            //TODO 增加默认逻辑或者未匹配到直接请求原服务
            Binding binding = new Binding();
            binding.setProperty("request", request);
            binding.setProperty("response", response);
            String branchName = groovyService.exec(binding, httpInterfaceReq.getBranchJumpScript());
            HttpInterfaceBranchReq branchReq = null;
            for (HttpInterfaceBranchReq branch : httpInterfaceReq.getBranchScriptList()) {
                if (StringUtils.equals(branch.getName(), branchName)) {
                    branchReq = branch;
                }
            }
            if (branchReq == null) {
                throw new MockException(RespCodeEnum.NOT_FOUND_INTERFACE_BRANCH);
            }
            context.setHttpInterfaceBranchReq(branchReq);
            responseBody = groovyService.exec(binding, branchReq.getSyncScript());
        } else {
            throw new MockException(RespCodeEnum.UNKNOWN_BRANCH);
        }
        if (StringUtils.isNotBlank(responseBody)) {
            response.getWriter().write(responseBody);
        }
    }
}
