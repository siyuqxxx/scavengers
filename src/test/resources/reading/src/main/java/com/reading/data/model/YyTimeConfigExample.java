package com.reading.data.model;

import java.util.ArrayList;
import java.util.List;

public class YyTimeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public YyTimeConfigExample() {
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

        public Criteria andLayTimeIsNull() {
            addCriterion("lay_time is null");
            return (Criteria) this;
        }

        public Criteria andLayTimeIsNotNull() {
            addCriterion("lay_time is not null");
            return (Criteria) this;
        }

        public Criteria andLayTimeEqualTo(String value) {
            addCriterion("lay_time =", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeNotEqualTo(String value) {
            addCriterion("lay_time <>", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeGreaterThan(String value) {
            addCriterion("lay_time >", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("lay_time >=", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeLessThan(String value) {
            addCriterion("lay_time <", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeLessThanOrEqualTo(String value) {
            addCriterion("lay_time <=", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeLike(String value) {
            addCriterion("lay_time like", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeNotLike(String value) {
            addCriterion("lay_time not like", value, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeIn(List<String> values) {
            addCriterion("lay_time in", values, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeNotIn(List<String> values) {
            addCriterion("lay_time not in", values, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeBetween(String value1, String value2) {
            addCriterion("lay_time between", value1, value2, "layTime");
            return (Criteria) this;
        }

        public Criteria andLayTimeNotBetween(String value1, String value2) {
            addCriterion("lay_time not between", value1, value2, "layTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeIsNull() {
            addCriterion("sys_tod_st_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeIsNotNull() {
            addCriterion("sys_tod_st_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeEqualTo(String value) {
            addCriterion("sys_tod_st_time =", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeNotEqualTo(String value) {
            addCriterion("sys_tod_st_time <>", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeGreaterThan(String value) {
            addCriterion("sys_tod_st_time >", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_tod_st_time >=", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeLessThan(String value) {
            addCriterion("sys_tod_st_time <", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeLessThanOrEqualTo(String value) {
            addCriterion("sys_tod_st_time <=", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeLike(String value) {
            addCriterion("sys_tod_st_time like", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeNotLike(String value) {
            addCriterion("sys_tod_st_time not like", value, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeIn(List<String> values) {
            addCriterion("sys_tod_st_time in", values, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeNotIn(List<String> values) {
            addCriterion("sys_tod_st_time not in", values, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeBetween(String value1, String value2) {
            addCriterion("sys_tod_st_time between", value1, value2, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodStTimeNotBetween(String value1, String value2) {
            addCriterion("sys_tod_st_time not between", value1, value2, "sysTodStTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeIsNull() {
            addCriterion("sys_tod_en_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeIsNotNull() {
            addCriterion("sys_tod_en_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeEqualTo(String value) {
            addCriterion("sys_tod_en_time =", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeNotEqualTo(String value) {
            addCriterion("sys_tod_en_time <>", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeGreaterThan(String value) {
            addCriterion("sys_tod_en_time >", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_tod_en_time >=", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeLessThan(String value) {
            addCriterion("sys_tod_en_time <", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeLessThanOrEqualTo(String value) {
            addCriterion("sys_tod_en_time <=", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeLike(String value) {
            addCriterion("sys_tod_en_time like", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeNotLike(String value) {
            addCriterion("sys_tod_en_time not like", value, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeIn(List<String> values) {
            addCriterion("sys_tod_en_time in", values, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeNotIn(List<String> values) {
            addCriterion("sys_tod_en_time not in", values, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeBetween(String value1, String value2) {
            addCriterion("sys_tod_en_time between", value1, value2, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTodEnTimeNotBetween(String value1, String value2) {
            addCriterion("sys_tod_en_time not between", value1, value2, "sysTodEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeIsNull() {
            addCriterion("sys_tom_st_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeIsNotNull() {
            addCriterion("sys_tom_st_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeEqualTo(String value) {
            addCriterion("sys_tom_st_time =", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeNotEqualTo(String value) {
            addCriterion("sys_tom_st_time <>", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeGreaterThan(String value) {
            addCriterion("sys_tom_st_time >", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_tom_st_time >=", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeLessThan(String value) {
            addCriterion("sys_tom_st_time <", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeLessThanOrEqualTo(String value) {
            addCriterion("sys_tom_st_time <=", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeLike(String value) {
            addCriterion("sys_tom_st_time like", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeNotLike(String value) {
            addCriterion("sys_tom_st_time not like", value, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeIn(List<String> values) {
            addCriterion("sys_tom_st_time in", values, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeNotIn(List<String> values) {
            addCriterion("sys_tom_st_time not in", values, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeBetween(String value1, String value2) {
            addCriterion("sys_tom_st_time between", value1, value2, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomStTimeNotBetween(String value1, String value2) {
            addCriterion("sys_tom_st_time not between", value1, value2, "sysTomStTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeIsNull() {
            addCriterion("sys_tom_en_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeIsNotNull() {
            addCriterion("sys_tom_en_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeEqualTo(String value) {
            addCriterion("sys_tom_en_time =", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeNotEqualTo(String value) {
            addCriterion("sys_tom_en_time <>", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeGreaterThan(String value) {
            addCriterion("sys_tom_en_time >", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_tom_en_time >=", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeLessThan(String value) {
            addCriterion("sys_tom_en_time <", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeLessThanOrEqualTo(String value) {
            addCriterion("sys_tom_en_time <=", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeLike(String value) {
            addCriterion("sys_tom_en_time like", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeNotLike(String value) {
            addCriterion("sys_tom_en_time not like", value, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeIn(List<String> values) {
            addCriterion("sys_tom_en_time in", values, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeNotIn(List<String> values) {
            addCriterion("sys_tom_en_time not in", values, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeBetween(String value1, String value2) {
            addCriterion("sys_tom_en_time between", value1, value2, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andSysTomEnTimeNotBetween(String value1, String value2) {
            addCriterion("sys_tom_en_time not between", value1, value2, "sysTomEnTime");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Integer value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Integer value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Integer value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Integer value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Integer value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Integer> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Integer> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Integer value1, Integer value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("time not between", value1, value2, "time");
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