package com.platform.mockcore.model;


import com.platform.mockcore.model.request.HttpInterfaceBranchReq;
import com.platform.mockcore.model.request.HttpInterfaceReq;

public class HttpMockContext {
    private HttpInterfaceReq httpInterfaceReq;
    private HttpInterfaceBranchReq httpInterfaceBranchReq;

    public HttpInterfaceReq getHttpInterfaceReq() {
        return httpInterfaceReq;
    }

    public void setHttpInterfaceReq(HttpInterfaceReq httpInterfaceReq) {
        this.httpInterfaceReq = httpInterfaceReq;
    }

    public HttpInterfaceBranchReq getHttpInterfaceBranchReq() {
        return httpInterfaceBranchReq;
    }

    public void setHttpInterfaceBranchReq(HttpInterfaceBranchReq httpInterfaceBranchReq) {
        this.httpInterfaceBranchReq = httpInterfaceBranchReq;
    }
}
