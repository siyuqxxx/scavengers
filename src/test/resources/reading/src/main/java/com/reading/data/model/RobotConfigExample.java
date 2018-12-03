package com.reading.data.model;

import java.util.ArrayList;
import java.util.List;

public class RobotConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public RobotConfigExample() {
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

        public Criteria andApikeyIsNull() {
            addCriterion("apikey is null");
            return (Criteria) this;
        }

        public Criteria andApikeyIsNotNull() {
            addCriterion("apikey is not null");
            return (Criteria) this;
        }

        public Criteria andApikeyEqualTo(String value) {
            addCriterion("apikey =", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyNotEqualTo(String value) {
            addCriterion("apikey <>", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyGreaterThan(String value) {
            addCriterion("apikey >", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyGreaterThanOrEqualTo(String value) {
            addCriterion("apikey >=", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyLessThan(String value) {
            addCriterion("apikey <", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyLessThanOrEqualTo(String value) {
            addCriterion("apikey <=", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyLike(String value) {
            addCriterion("apikey like", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyNotLike(String value) {
            addCriterion("apikey not like", value, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyIn(List<String> values) {
            addCriterion("apikey in", values, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyNotIn(List<String> values) {
            addCriterion("apikey not in", values, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyBetween(String value1, String value2) {
            addCriterion("apikey between", value1, value2, "apikey");
            return (Criteria) this;
        }

        public Criteria andApikeyNotBetween(String value1, String value2) {
            addCriterion("apikey not between", value1, value2, "apikey");
            return (Criteria) this;
        }

        public Criteria andTokenIsNull() {
            addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(String value) {
            addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(String value) {
            addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThan(String value) {
            addCriterion("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualTo(String value) {
            addCriterion("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThan(String value) {
            addCriterion("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualTo(String value) {
            addCriterion("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLike(String value) {
            addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(String value) {
            addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(List<String> values) {
            addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(List<String> values) {
            addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(String value1, String value2) {
            addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(String value1, String value2) {
            addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andVersionsIsNull() {
            addCriterion("versions is null");
            return (Criteria) this;
        }

        public Criteria andVersionsIsNotNull() {
            addCriterion("versions is not null");
            return (Criteria) this;
        }

        public Criteria andVersionsEqualTo(Integer value) {
            addCriterion("versions =", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotEqualTo(Integer value) {
            addCriterion("versions <>", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThan(Integer value) {
            addCriterion("versions >", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThanOrEqualTo(Integer value) {
            addCriterion("versions >=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThan(Integer value) {
            addCriterion("versions <", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThanOrEqualTo(Integer value) {
            addCriterion("versions <=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsIn(List<Integer> values) {
            addCriterion("versions in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotIn(List<Integer> values) {
            addCriterion("versions not in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsBetween(Integer value1, Integer value2) {
            addCriterion("versions between", value1, value2, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotBetween(Integer value1, Integer value2) {
            addCriterion("versions not between", value1, value2, "versions");
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

        public Criteria andMatchingScoreIsNull() {
            addCriterion("matching_score is null");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreIsNotNull() {
            addCriterion("matching_score is not null");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreEqualTo(Integer value) {
            addCriterion("matching_score =", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreNotEqualTo(Integer value) {
            addCriterion("matching_score <>", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreGreaterThan(Integer value) {
            addCriterion("matching_score >", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("matching_score >=", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreLessThan(Integer value) {
            addCriterion("matching_score <", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreLessThanOrEqualTo(Integer value) {
            addCriterion("matching_score <=", value, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreIn(List<Integer> values) {
            addCriterion("matching_score in", values, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreNotIn(List<Integer> values) {
            addCriterion("matching_score not in", values, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreBetween(Integer value1, Integer value2) {
            addCriterion("matching_score between", value1, value2, "matchingScore");
            return (Criteria) this;
        }

        public Criteria andMatchingScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("matching_score not between", value1, value2, "matchingScore");
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