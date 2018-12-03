package com.reading.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WechatThreeAuthorizeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public WechatThreeAuthorizeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIsNull() {
            addCriterion("library_id is null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIsNotNull() {
            addCriterion("library_id is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdEqualTo(Long value) {
            addCriterion("library_id =", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotEqualTo(Long value) {
            addCriterion("library_id <>", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThan(Long value) {
            addCriterion("library_id >", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("library_id >=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThan(Long value) {
            addCriterion("library_id <", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThanOrEqualTo(Long value) {
            addCriterion("library_id <=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIn(List<Long> values) {
            addCriterion("library_id in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotIn(List<Long> values) {
            addCriterion("library_id not in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdBetween(Long value1, Long value2) {
            addCriterion("library_id between", value1, value2, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotBetween(Long value1, Long value2) {
            addCriterion("library_id not between", value1, value2, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryNameIsNull() {
            addCriterion("library_name is null");
            return (Criteria) this;
        }

        public Criteria andLibraryNameIsNotNull() {
            addCriterion("library_name is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryNameEqualTo(String value) {
            addCriterion("library_name =", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameNotEqualTo(String value) {
            addCriterion("library_name <>", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameGreaterThan(String value) {
            addCriterion("library_name >", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameGreaterThanOrEqualTo(String value) {
            addCriterion("library_name >=", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameLessThan(String value) {
            addCriterion("library_name <", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameLessThanOrEqualTo(String value) {
            addCriterion("library_name <=", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameLike(String value) {
            addCriterion("library_name like", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameNotLike(String value) {
            addCriterion("library_name not like", value, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameIn(List<String> values) {
            addCriterion("library_name in", values, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameNotIn(List<String> values) {
            addCriterion("library_name not in", values, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameBetween(String value1, String value2) {
            addCriterion("library_name between", value1, value2, "libraryName");
            return (Criteria) this;
        }

        public Criteria andLibraryNameNotBetween(String value1, String value2) {
            addCriterion("library_name not between", value1, value2, "libraryName");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidIsNull() {
            addCriterion("authorizer_appid is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidIsNotNull() {
            addCriterion("authorizer_appid is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidEqualTo(String value) {
            addCriterion("authorizer_appid =", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidNotEqualTo(String value) {
            addCriterion("authorizer_appid <>", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidGreaterThan(String value) {
            addCriterion("authorizer_appid >", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidGreaterThanOrEqualTo(String value) {
            addCriterion("authorizer_appid >=", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidLessThan(String value) {
            addCriterion("authorizer_appid <", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidLessThanOrEqualTo(String value) {
            addCriterion("authorizer_appid <=", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidLike(String value) {
            addCriterion("authorizer_appid like", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidNotLike(String value) {
            addCriterion("authorizer_appid not like", value, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidIn(List<String> values) {
            addCriterion("authorizer_appid in", values, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidNotIn(List<String> values) {
            addCriterion("authorizer_appid not in", values, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidBetween(String value1, String value2) {
            addCriterion("authorizer_appid between", value1, value2, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAppidNotBetween(String value1, String value2) {
            addCriterion("authorizer_appid not between", value1, value2, "authorizerAppid");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenIsNull() {
            addCriterion("authorizer_access_token is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenIsNotNull() {
            addCriterion("authorizer_access_token is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenEqualTo(String value) {
            addCriterion("authorizer_access_token =", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenNotEqualTo(String value) {
            addCriterion("authorizer_access_token <>", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenGreaterThan(String value) {
            addCriterion("authorizer_access_token >", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenGreaterThanOrEqualTo(String value) {
            addCriterion("authorizer_access_token >=", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenLessThan(String value) {
            addCriterion("authorizer_access_token <", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenLessThanOrEqualTo(String value) {
            addCriterion("authorizer_access_token <=", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenLike(String value) {
            addCriterion("authorizer_access_token like", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenNotLike(String value) {
            addCriterion("authorizer_access_token not like", value, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenIn(List<String> values) {
            addCriterion("authorizer_access_token in", values, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenNotIn(List<String> values) {
            addCriterion("authorizer_access_token not in", values, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenBetween(String value1, String value2) {
            addCriterion("authorizer_access_token between", value1, value2, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerAccessTokenNotBetween(String value1, String value2) {
            addCriterion("authorizer_access_token not between", value1, value2, "authorizerAccessToken");
            return (Criteria) this;
        }

        public Criteria andExpiresInIsNull() {
            addCriterion("expires_in is null");
            return (Criteria) this;
        }

        public Criteria andExpiresInIsNotNull() {
            addCriterion("expires_in is not null");
            return (Criteria) this;
        }

        public Criteria andExpiresInEqualTo(String value) {
            addCriterion("expires_in =", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInNotEqualTo(String value) {
            addCriterion("expires_in <>", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInGreaterThan(String value) {
            addCriterion("expires_in >", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInGreaterThanOrEqualTo(String value) {
            addCriterion("expires_in >=", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInLessThan(String value) {
            addCriterion("expires_in <", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInLessThanOrEqualTo(String value) {
            addCriterion("expires_in <=", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInLike(String value) {
            addCriterion("expires_in like", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInNotLike(String value) {
            addCriterion("expires_in not like", value, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInIn(List<String> values) {
            addCriterion("expires_in in", values, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInNotIn(List<String> values) {
            addCriterion("expires_in not in", values, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInBetween(String value1, String value2) {
            addCriterion("expires_in between", value1, value2, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andExpiresInNotBetween(String value1, String value2) {
            addCriterion("expires_in not between", value1, value2, "expiresIn");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIsNull() {
            addCriterion("authorizer_refresh_token is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIsNotNull() {
            addCriterion("authorizer_refresh_token is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenEqualTo(String value) {
            addCriterion("authorizer_refresh_token =", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotEqualTo(String value) {
            addCriterion("authorizer_refresh_token <>", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenGreaterThan(String value) {
            addCriterion("authorizer_refresh_token >", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenGreaterThanOrEqualTo(String value) {
            addCriterion("authorizer_refresh_token >=", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLessThan(String value) {
            addCriterion("authorizer_refresh_token <", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLessThanOrEqualTo(String value) {
            addCriterion("authorizer_refresh_token <=", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLike(String value) {
            addCriterion("authorizer_refresh_token like", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotLike(String value) {
            addCriterion("authorizer_refresh_token not like", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIn(List<String> values) {
            addCriterion("authorizer_refresh_token in", values, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotIn(List<String> values) {
            addCriterion("authorizer_refresh_token not in", values, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenBetween(String value1, String value2) {
            addCriterion("authorizer_refresh_token between", value1, value2, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotBetween(String value1, String value2) {
            addCriterion("authorizer_refresh_token not between", value1, value2, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsIsNull() {
            addCriterion("wechat_three_funcation_ids is null");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsIsNotNull() {
            addCriterion("wechat_three_funcation_ids is not null");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsEqualTo(String value) {
            addCriterion("wechat_three_funcation_ids =", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsNotEqualTo(String value) {
            addCriterion("wechat_three_funcation_ids <>", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsGreaterThan(String value) {
            addCriterion("wechat_three_funcation_ids >", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsGreaterThanOrEqualTo(String value) {
            addCriterion("wechat_three_funcation_ids >=", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsLessThan(String value) {
            addCriterion("wechat_three_funcation_ids <", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsLessThanOrEqualTo(String value) {
            addCriterion("wechat_three_funcation_ids <=", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsLike(String value) {
            addCriterion("wechat_three_funcation_ids like", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsNotLike(String value) {
            addCriterion("wechat_three_funcation_ids not like", value, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsIn(List<String> values) {
            addCriterion("wechat_three_funcation_ids in", values, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsNotIn(List<String> values) {
            addCriterion("wechat_three_funcation_ids not in", values, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsBetween(String value1, String value2) {
            addCriterion("wechat_three_funcation_ids between", value1, value2, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andWechatThreeFuncationIdsNotBetween(String value1, String value2) {
            addCriterion("wechat_three_funcation_ids not between", value1, value2, "wechatThreeFuncationIds");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusIsNull() {
            addCriterion("authorize_status is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusIsNotNull() {
            addCriterion("authorize_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusEqualTo(Integer value) {
            addCriterion("authorize_status =", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusNotEqualTo(Integer value) {
            addCriterion("authorize_status <>", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusGreaterThan(Integer value) {
            addCriterion("authorize_status >", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("authorize_status >=", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusLessThan(Integer value) {
            addCriterion("authorize_status <", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("authorize_status <=", value, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusIn(List<Integer> values) {
            addCriterion("authorize_status in", values, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusNotIn(List<Integer> values) {
            addCriterion("authorize_status not in", values, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusBetween(Integer value1, Integer value2) {
            addCriterion("authorize_status between", value1, value2, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("authorize_status not between", value1, value2, "authorizeStatus");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeIsNull() {
            addCriterion("authorize_status_time is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeIsNotNull() {
            addCriterion("authorize_status_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeEqualTo(Date value) {
            addCriterion("authorize_status_time =", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeNotEqualTo(Date value) {
            addCriterion("authorize_status_time <>", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeGreaterThan(Date value) {
            addCriterion("authorize_status_time >", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("authorize_status_time >=", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeLessThan(Date value) {
            addCriterion("authorize_status_time <", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeLessThanOrEqualTo(Date value) {
            addCriterion("authorize_status_time <=", value, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeIn(List<Date> values) {
            addCriterion("authorize_status_time in", values, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeNotIn(List<Date> values) {
            addCriterion("authorize_status_time not in", values, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeBetween(Date value1, Date value2) {
            addCriterion("authorize_status_time between", value1, value2, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andAuthorizeStatusTimeNotBetween(Date value1, Date value2) {
            addCriterion("authorize_status_time not between", value1, value2, "authorizeStatusTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNull() {
            addCriterion("auth_code is null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNotNull() {
            addCriterion("auth_code is not null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeEqualTo(String value) {
            addCriterion("auth_code =", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotEqualTo(String value) {
            addCriterion("auth_code <>", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThan(String value) {
            addCriterion("auth_code >", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_code >=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThan(String value) {
            addCriterion("auth_code <", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThanOrEqualTo(String value) {
            addCriterion("auth_code <=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLike(String value) {
            addCriterion("auth_code like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotLike(String value) {
            addCriterion("auth_code not like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIn(List<String> values) {
            addCriterion("auth_code in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotIn(List<String> values) {
            addCriterion("auth_code not in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeBetween(String value1, String value2) {
            addCriterion("auth_code between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotBetween(String value1, String value2) {
            addCriterion("auth_code not between", value1, value2, "authCode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}