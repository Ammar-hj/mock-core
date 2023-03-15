package com.platform.mockcore.controller;

import com.platform.mockcore.services.HttpMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HttpController {
    @Autowired
    private HttpMockService httpMockService;

    @RequestMapping(value = "/")
    public void ignore() {
    }

    @RequestMapping(value = "/**/*")
    public void handleHttpRequests(HttpServletRequest request, HttpServletResponse response) throws IOException {
        httpMockService.mock(request, response);
    }
}
