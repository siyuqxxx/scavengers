package com.reading.data.model;

import com.reading.base.BaseModel;

import java.util.Date;
import java.util.List;

public class WechatThreeAuthorize extends BaseModel {
    private Long id;

    private Long libraryId;

    private String libraryName;

    private String authorizerAppid;

    private String authorizerAccessToken;

    private String expiresIn;

    private String authorizerRefreshToken;

    private String wechatThreeFuncationIds;

    private Date createTime;

    private Date updateTime;

    private Integer authorizeStatus;

    private Date authorizeStatusTime;

    private Integer status;

    private String authCode;

    private List<WechatThreeFuncation> funcations;

    public List<WechatThreeFuncation> getFuncations() {
        return funcations;
    }

    public void setFuncations(List<WechatThreeFuncation> funcations) {
        this.funcations = funcations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName == null ? null : libraryName.trim();
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid == null ? null : authorizerAppid.trim();
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken == null ? null : authorizerAccessToken.trim();
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn == null ? null : expiresIn.trim();
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken == null ? null : authorizerRefreshToken.trim();
    }

    public String getWechatThreeFuncationIds() {
        return wechatThreeFuncationIds;
    }

    public void setWechatThreeFuncationIds(String wechatThreeFuncationIds) {
        this.wechatThreeFuncationIds = wechatThreeFuncationIds == null ? null : wechatThreeFuncationIds.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAuthorizeStatus() {
        return authorizeStatus;
    }

    public void setAuthorizeStatus(Integer authorizeStatus) {
        this.authorizeStatus = authorizeStatus;
    }

    public Date getAuthorizeStatusTime() {
        return authorizeStatusTime;
    }

    public void setAuthorizeStatusTime(Date authorizeStatusTime) {
        this.authorizeStatusTime = authorizeStatusTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }
}