package com.reading.data.model;

import java.util.ArrayList;
import java.util.List;

public class YyTimeSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public YyTimeSettingExample() {
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

        public Criteria andKeyidIsNull() {
            addCriterion("KeyId is null");
            return (Criteria) this;
        }

        public Criteria andKeyidIsNotNull() {
            addCriterion("KeyId is not null");
            return (Criteria) this;
        }

        public Criteria andKeyidEqualTo(Long value) {
            addCriterion("KeyId =", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidNotEqualTo(Long value) {
            addCriterion("KeyId <>", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidGreaterThan(Long value) {
            addCriterion("KeyId >", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidGreaterThanOrEqualTo(Long value) {
            addCriterion("KeyId >=", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidLessThan(Long value) {
            addCriterion("KeyId <", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidLessThanOrEqualTo(Long value) {
            addCriterion("KeyId <=", value, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidIn(List<Long> values) {
            addCriterion("KeyId in", values, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidNotIn(List<Long> values) {
            addCriterion("KeyId not in", values, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidBetween(Long value1, Long value2) {
            addCriterion("KeyId between", value1, value2, "keyid");
            return (Criteria) this;
        }

        public Criteria andKeyidNotBetween(Long value1, Long value2) {
            addCriterion("KeyId not between", value1, value2, "keyid");
            return (Criteria) this;
        }

        public Criteria andLibraryidIsNull() {
            addCriterion("LibraryId is null");
            return (Criteria) this;
        }

        public Criteria andLibraryidIsNotNull() {
            addCriterion("LibraryId is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryidEqualTo(Long value) {
            addCriterion("LibraryId =", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotEqualTo(Long value) {
            addCriterion("LibraryId <>", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidGreaterThan(Long value) {
            addCriterion("LibraryId >", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidGreaterThanOrEqualTo(Long value) {
            addCriterion("LibraryId >=", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidLessThan(Long value) {
            addCriterion("LibraryId <", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidLessThanOrEqualTo(Long value) {
            addCriterion("LibraryId <=", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidIn(List<Long> values) {
            addCriterion("LibraryId in", values, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotIn(List<Long> values) {
            addCriterion("LibraryId not in", values, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidBetween(Long value1, Long value2) {
            addCriterion("LibraryId between", value1, value2, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotBetween(Long value1, Long value2) {
            addCriterion("LibraryId not between", value1, value2, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibrarynameIsNull() {
            addCriterion("LibraryName is null");
            return (Criteria) this;
        }

        public Criteria andLibrarynameIsNotNull() {
            addCriterion("LibraryName is not null");
            return (Criteria) this;
        }

        public Criteria andLibrarynameEqualTo(String value) {
            addCriterion("LibraryName =", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameNotEqualTo(String value) {
            addCriterion("LibraryName <>", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameGreaterThan(String value) {
            addCriterion("LibraryName >", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameGreaterThanOrEqualTo(String value) {
            addCriterion("LibraryName >=", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameLessThan(String value) {
            addCriterion("LibraryName <", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameLessThanOrEqualTo(String value) {
            addCriterion("LibraryName <=", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameLike(String value) {
            addCriterion("LibraryName like", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameNotLike(String value) {
            addCriterion("LibraryName not like", value, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameIn(List<String> values) {
            addCriterion("LibraryName in", values, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameNotIn(List<String> values) {
            addCriterion("LibraryName not in", values, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameBetween(String value1, String value2) {
            addCriterion("LibraryName between", value1, value2, "libraryname");
            return (Criteria) this;
        }

        public Criteria andLibrarynameNotBetween(String value1, String value2) {
            addCriterion("LibraryName not between", value1, value2, "libraryname");
            return (Criteria) this;
        }

        public Criteria andDayTimeIsNull() {
            addCriterion("day_time is null");
            return (Criteria) this;
        }

        public Criteria andDayTimeIsNotNull() {
            addCriterion("day_time is not null");
            return (Criteria) this;
        }

        public Criteria andDayTimeEqualTo(Integer value) {
            addCriterion("day_time =", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeNotEqualTo(Integer value) {
            addCriterion("day_time <>", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeGreaterThan(Integer value) {
            addCriterion("day_time >", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("day_time >=", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeLessThan(Integer value) {
            addCriterion("day_time <", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("day_time <=", value, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeIn(List<Integer> values) {
            addCriterion("day_time in", values, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeNotIn(List<Integer> values) {
            addCriterion("day_time not in", values, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeBetween(Integer value1, Integer value2) {
            addCriterion("day_time between", value1, value2, "dayTime");
            return (Criteria) this;
        }

        public Criteria andDayTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("day_time not between", value1, value2, "dayTime");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignIsNull() {
            addCriterion("is_open_sign is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignIsNotNull() {
            addCriterion("is_open_sign is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignEqualTo(Integer value) {
            addCriterion("is_open_sign =", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignNotEqualTo(Integer value) {
            addCriterion("is_open_sign <>", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignGreaterThan(Integer value) {
            addCriterion("is_open_sign >", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_open_sign >=", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignLessThan(Integer value) {
            addCriterion("is_open_sign <", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignLessThanOrEqualTo(Integer value) {
            addCriterion("is_open_sign <=", value, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignIn(List<Integer> values) {
            addCriterion("is_open_sign in", values, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignNotIn(List<Integer> values) {
            addCriterion("is_open_sign not in", values, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignBetween(Integer value1, Integer value2) {
            addCriterion("is_open_sign between", value1, value2, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andIsOpenSignNotBetween(Integer value1, Integer value2) {
            addCriterion("is_open_sign not between", value1, value2, "isOpenSign");
            return (Criteria) this;
        }

        public Criteria andMinuteIsNull() {
            addCriterion("minute is null");
            return (Criteria) this;
        }

        public Criteria andMinuteIsNotNull() {
            addCriterion("minute is not null");
            return (Criteria) this;
        }

        public Criteria andMinuteEqualTo(Integer value) {
            addCriterion("minute =", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotEqualTo(Integer value) {
            addCriterion("minute <>", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteGreaterThan(Integer value) {
            addCriterion("minute >", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("minute >=", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteLessThan(Integer value) {
            addCriterion("minute <", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("minute <=", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteIn(List<Integer> values) {
            addCriterion("minute in", values, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotIn(List<Integer> values) {
            addCriterion("minute not in", values, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteBetween(Integer value1, Integer value2) {
            addCriterion("minute between", value1, value2, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("minute not between", value1, value2, "minute");
            return (Criteria) this;
        }

        public Criteria andTimeMarkIsNull() {
            addCriterion("time_mark is null");
            return (Criteria) this;
        }

        public Criteria andTimeMarkIsNotNull() {
            addCriterion("time_mark is not null");
            return (Criteria) this;
        }

        public Criteria andTimeMarkEqualTo(String value) {
            addCriterion("time_mark =", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkNotEqualTo(String value) {
            addCriterion("time_mark <>", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkGreaterThan(String value) {
            addCriterion("time_mark >", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkGreaterThanOrEqualTo(String value) {
            addCriterion("time_mark >=", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkLessThan(String value) {
            addCriterion("time_mark <", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkLessThanOrEqualTo(String value) {
            addCriterion("time_mark <=", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkLike(String value) {
            addCriterion("time_mark like", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkNotLike(String value) {
            addCriterion("time_mark not like", value, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkIn(List<String> values) {
            addCriterion("time_mark in", values, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkNotIn(List<String> values) {
            addCriterion("time_mark not in", values, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkBetween(String value1, String value2) {
            addCriterion("time_mark between", value1, value2, "timeMark");
            return (Criteria) this;
        }

        public Criteria andTimeMarkNotBetween(String value1, String value2) {
            addCriterion("time_mark not between", value1, value2, "timeMark");
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