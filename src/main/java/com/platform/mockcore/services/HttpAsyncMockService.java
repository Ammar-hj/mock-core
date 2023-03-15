package com.platform.mockcore.services;

import com.platform.mockcore.model.HttpMockContext;

import javax.servlet.http.HttpServletRequest;

public interface HttpAsyncMockService {
    void mock(HttpMockContext context, HttpServletRequest request) throws Exception;
}
