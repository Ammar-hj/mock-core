package com.platform.mockcore.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpMockService {
    void mock(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
