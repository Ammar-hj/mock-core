package com.platform.mockcore.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class HttpInterfaceKeyReq implements Serializable {

    public interface Insert {
    }

    public interface Update {
    }

    @NotBlank(groups = {Insert.class, Update.class})
    @Pattern(regexp = "^/.*", groups = {Insert.class, Update.class})
    private String requestUri;

    @NotNull(groups = {Insert.class, Update.class})
    private String requestMethod;

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return "HttpInterfaceKeyReq{" +
                "requestUri='" + requestUri + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                '}';
    }
}
