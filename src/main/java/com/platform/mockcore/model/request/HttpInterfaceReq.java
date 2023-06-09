package com.platform.mockcore.model.request;

import com.platform.mockcore.enums.ConfigMode;
import com.platform.mockcore.enums.SpaceEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HttpInterfaceReq extends HttpInterfaceKeyReq implements Serializable {

    @NotNull(groups = {Update.class})
    private Long id;

    @NotNull(groups = {Insert.class, Update.class})
    private String description;

    @NotNull(groups = {Insert.class, Update.class})
    private Boolean needAsyncCallback;

    @NotNull(groups = {Insert.class, Update.class})
    private ConfigMode configMode;

    @NotNull(groups = {Insert.class, Update.class})
    private String responseBody;

    @NotNull(groups = {Insert.class, Update.class})
    private String callbackRequestUrl;

    @NotNull(groups = {Insert.class, Update.class})
    private String callbackRequestMethod;

    @NotNull(groups = {Insert.class, Update.class})
    private String callbackRequestBody;

    @NotNull(groups = {Insert.class, Update.class})
    private String branchJumpScript;

    @NotNull(groups = {Insert.class, Update.class})
    private String syncScript;

    @NotNull(groups = {Insert.class, Update.class})
    private String asyncScript;

    @NotNull(groups = {Insert.class, Update.class})
    @Min(value = 0, groups = {Insert.class, Update.class})
    @Max(value = 20000, groups = {Insert.class, Update.class})
    private Integer syncDelay;

    @NotNull(groups = {Insert.class, Update.class})
    @Min(value = 0, groups = {Insert.class, Update.class})
    @Max(value = 20000, groups = {Insert.class, Update.class})
    private Integer asyncDelay;

    @NotNull(groups = {Insert.class, Update.class})
    private Boolean start;

    @NotNull(groups = {Insert.class, Update.class})
    private Long spaceId;

    private String lastUpdateUser;

    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    private SpaceEnum spaceEnum;

    private Date ctime;

    private Date mtime;

    @NotNull(groups = {Insert.class, Update.class})
    private List<HttpInterfaceHeaderReq> responseHeaderList;

    @NotNull(groups = {Insert.class, Update.class})
    private List<HttpInterfaceHeaderReq> callbackRequestHeaderList;

    @NotNull(groups = {Insert.class, Update.class})
    private List<HttpInterfaceBranchReq> branchScriptList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNeedAsyncCallback() {
        return needAsyncCallback;
    }

    public void setNeedAsyncCallback(Boolean needAsyncCallback) {
        this.needAsyncCallback = needAsyncCallback;
    }

    public ConfigMode getConfigMode() {
        return configMode;
    }

    public void setConfigMode(ConfigMode configMode) {
        this.configMode = configMode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getCallbackRequestUrl() {
        return callbackRequestUrl;
    }

    public void setCallbackRequestUrl(String callbackRequestUrl) {
        this.callbackRequestUrl = callbackRequestUrl;
    }

    public String getCallbackRequestMethod() {
        return callbackRequestMethod;
    }

    public void setCallbackRequestMethod(String callbackRequestMethod) {
        this.callbackRequestMethod = callbackRequestMethod;
    }

    public String getCallbackRequestBody() {
        return callbackRequestBody;
    }

    public void setCallbackRequestBody(String callbackRequestBody) {
        this.callbackRequestBody = callbackRequestBody;
    }

    public String getBranchJumpScript() {
        return branchJumpScript;
    }

    public void setBranchJumpScript(String branchJumpScript) {
        this.branchJumpScript = branchJumpScript;
    }

    public String getSyncScript() {
        return syncScript;
    }

    public void setSyncScript(String syncScript) {
        this.syncScript = syncScript;
    }

    public String getAsyncScript() {
        return asyncScript;
    }

    public void setAsyncScript(String asyncScript) {
        this.asyncScript = asyncScript;
    }

    public Integer getSyncDelay() {
        return syncDelay;
    }

    public void setSyncDelay(Integer syncDelay) {
        this.syncDelay = syncDelay;
    }

    public Integer getAsyncDelay() {
        return asyncDelay;
    }

    public void setAsyncDelay(Integer asyncDelay) {
        this.asyncDelay = asyncDelay;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpaceEnum getSpaceEnum() {
        return spaceEnum;
    }

    public void setSpaceEnum(SpaceEnum spaceEnum) {
        this.spaceEnum = spaceEnum;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public List<HttpInterfaceHeaderReq> getResponseHeaderList() {
        return responseHeaderList;
    }

    public void setResponseHeaderList(List<HttpInterfaceHeaderReq> responseHeaderList) {
        this.responseHeaderList = responseHeaderList;
    }

    public List<HttpInterfaceHeaderReq> getCallbackRequestHeaderList() {
        return callbackRequestHeaderList;
    }

    public void setCallbackRequestHeaderList(List<HttpInterfaceHeaderReq> callbackRequestHeaderList) {
        this.callbackRequestHeaderList = callbackRequestHeaderList;
    }

    public List<HttpInterfaceBranchReq> getBranchScriptList() {
        return branchScriptList;
    }

    public void setBranchScriptList(List<HttpInterfaceBranchReq> branchScriptList) {
        this.branchScriptList = branchScriptList;
    }

    @Override
    public String toString() {
        return "HttpInterfaceReq{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", needAsyncCallback=" + needAsyncCallback +
                ", configMode=" + configMode +
                ", responseBody='" + responseBody + '\'' +
                ", callbackRequestUrl='" + callbackRequestUrl + '\'' +
                ", callbackRequestMethod='" + callbackRequestMethod + '\'' +
                ", callbackRequestBody='" + callbackRequestBody + '\'' +
                ", branchJumpScript='" + branchJumpScript + '\'' +
                ", syncScript='" + syncScript + '\'' +
                ", asyncScript='" + asyncScript + '\'' +
                ", syncDelay=" + syncDelay +
                ", asyncDelay=" + asyncDelay +
                ", start=" + start +
                ", spaceId=" + spaceId +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", name='" + name + '\'' +
                ", spaceEnum=" + spaceEnum +
                ", gmtCreate=" + ctime +
                ", gmtModified=" + mtime +
                ", responseHeaderList=" + responseHeaderList +
                ", callbackRequestHeaderList=" + callbackRequestHeaderList +
                ", branchScriptList=" + branchScriptList +
                '}';
    }
}
