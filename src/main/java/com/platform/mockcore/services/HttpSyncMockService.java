package com.platform.mockcore.services;

import com.platform.mockcore.model.HttpMockContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpSyncMockService {
    void mock(HttpMockContext context, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
