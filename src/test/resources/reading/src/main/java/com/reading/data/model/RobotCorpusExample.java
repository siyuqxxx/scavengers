package com.reading.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RobotCorpusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public RobotCorpusExample() {
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

        public Criteria andLibraryidIsNull() {
            addCriterion("libraryId is null");
            return (Criteria) this;
        }

        public Criteria andLibraryidIsNotNull() {
            addCriterion("libraryId is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryidEqualTo(Long value) {
            addCriterion("libraryId =", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotEqualTo(Long value) {
            addCriterion("libraryId <>", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidGreaterThan(Long value) {
            addCriterion("libraryId >", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidGreaterThanOrEqualTo(Long value) {
            addCriterion("libraryId >=", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidLessThan(Long value) {
            addCriterion("libraryId <", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidLessThanOrEqualTo(Long value) {
            addCriterion("libraryId <=", value, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidIn(List<Long> values) {
            addCriterion("libraryId in", values, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotIn(List<Long> values) {
            addCriterion("libraryId not in", values, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidBetween(Long value1, Long value2) {
            addCriterion("libraryId between", value1, value2, "libraryid");
            return (Criteria) this;
        }

        public Criteria andLibraryidNotBetween(Long value1, Long value2) {
            addCriterion("libraryId not between", value1, value2, "libraryid");
            return (Criteria) this;
        }

        public Criteria andQuestionIsNull() {
            addCriterion("question is null");
            return (Criteria) this;
        }

        public Criteria andQuestionIsNotNull() {
            addCriterion("question is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionEqualTo(String value) {
            addCriterion("question =", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotEqualTo(String value) {
            addCriterion("question <>", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionGreaterThan(String value) {
            addCriterion("question >", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionGreaterThanOrEqualTo(String value) {
            addCriterion("question >=", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLessThan(String value) {
            addCriterion("question <", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLessThanOrEqualTo(String value) {
            addCriterion("question <=", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLike(String value) {
            addCriterion("question like", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotLike(String value) {
            addCriterion("question not like", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionIn(List<String> values) {
            addCriterion("question in", values, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotIn(List<String> values) {
            addCriterion("question not in", values, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionBetween(String value1, String value2) {
            addCriterion("question between", value1, value2, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotBetween(String value1, String value2) {
            addCriterion("question not between", value1, value2, "question");
            return (Criteria) this;
        }

        public Criteria andTulingIdIsNull() {
            addCriterion("tuling_id is null");
            return (Criteria) this;
        }

        public Criteria andTulingIdIsNotNull() {
            addCriterion("tuling_id is not null");
            return (Criteria) this;
        }

        public Criteria andTulingIdEqualTo(String value) {
            addCriterion("tuling_id =", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdNotEqualTo(String value) {
            addCriterion("tuling_id <>", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdGreaterThan(String value) {
            addCriterion("tuling_id >", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdGreaterThanOrEqualTo(String value) {
            addCriterion("tuling_id >=", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdLessThan(String value) {
            addCriterion("tuling_id <", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdLessThanOrEqualTo(String value) {
            addCriterion("tuling_id <=", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdLike(String value) {
            addCriterion("tuling_id like", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdNotLike(String value) {
            addCriterion("tuling_id not like", value, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdIn(List<String> values) {
            addCriterion("tuling_id in", values, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdNotIn(List<String> values) {
            addCriterion("tuling_id not in", values, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdBetween(String value1, String value2) {
            addCriterion("tuling_id between", value1, value2, "tulingId");
            return (Criteria) this;
        }

        public Criteria andTulingIdNotBetween(String value1, String value2) {
            addCriterion("tuling_id not between", value1, value2, "tulingId");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNull() {
            addCriterion("answer is null");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNotNull() {
            addCriterion("answer is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerEqualTo(String value) {
            addCriterion("answer =", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotEqualTo(String value) {
            addCriterion("answer <>", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThan(String value) {
            addCriterion("answer >", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("answer >=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThan(String value) {
            addCriterion("answer <", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThanOrEqualTo(String value) {
            addCriterion("answer <=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLike(String value) {
            addCriterion("answer like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotLike(String value) {
            addCriterion("answer not like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerIn(List<String> values) {
            addCriterion("answer in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotIn(List<String> values) {
            addCriterion("answer not in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerBetween(String value1, String value2) {
            addCriterion("answer between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotBetween(String value1, String value2) {
            addCriterion("answer not between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andQuestion1IsNull() {
            addCriterion("question1 is null");
            return (Criteria) this;
        }

        public Criteria andQuestion1IsNotNull() {
            addCriterion("question1 is not null");
            return (Criteria) this;
        }

        public Criteria andQuestion1EqualTo(String value) {
            addCriterion("question1 =", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1NotEqualTo(String value) {
            addCriterion("question1 <>", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1GreaterThan(String value) {
            addCriterion("question1 >", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1GreaterThanOrEqualTo(String value) {
            addCriterion("question1 >=", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1LessThan(String value) {
            addCriterion("question1 <", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1LessThanOrEqualTo(String value) {
            addCriterion("question1 <=", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1Like(String value) {
            addCriterion("question1 like", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1NotLike(String value) {
            addCriterion("question1 not like", value, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1In(List<String> values) {
            addCriterion("question1 in", values, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1NotIn(List<String> values) {
            addCriterion("question1 not in", values, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1Between(String value1, String value2) {
            addCriterion("question1 between", value1, value2, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion1NotBetween(String value1, String value2) {
            addCriterion("question1 not between", value1, value2, "question1");
            return (Criteria) this;
        }

        public Criteria andQuestion2IsNull() {
            addCriterion("question2 is null");
            return (Criteria) this;
        }

        public Criteria andQuestion2IsNotNull() {
            addCriterion("question2 is not null");
            return (Criteria) this;
        }

        public Criteria andQuestion2EqualTo(String value) {
            addCriterion("question2 =", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2NotEqualTo(String value) {
            addCriterion("question2 <>", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2GreaterThan(String value) {
            addCriterion("question2 >", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2GreaterThanOrEqualTo(String value) {
            addCriterion("question2 >=", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2LessThan(String value) {
            addCriterion("question2 <", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2LessThanOrEqualTo(String value) {
            addCriterion("question2 <=", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2Like(String value) {
            addCriterion("question2 like", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2NotLike(String value) {
            addCriterion("question2 not like", value, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2In(List<String> values) {
            addCriterion("question2 in", values, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2NotIn(List<String> values) {
            addCriterion("question2 not in", values, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2Between(String value1, String value2) {
            addCriterion("question2 between", value1, value2, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion2NotBetween(String value1, String value2) {
            addCriterion("question2 not between", value1, value2, "question2");
            return (Criteria) this;
        }

        public Criteria andQuestion3IsNull() {
            addCriterion("question3 is null");
            return (Criteria) this;
        }

        public Criteria andQuestion3IsNotNull() {
            addCriterion("question3 is not null");
            return (Criteria) this;
        }

        public Criteria andQuestion3EqualTo(String value) {
            addCriterion("question3 =", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3NotEqualTo(String value) {
            addCriterion("question3 <>", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3GreaterThan(String value) {
            addCriterion("question3 >", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3GreaterThanOrEqualTo(String value) {
            addCriterion("question3 >=", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3LessThan(String value) {
            addCriterion("question3 <", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3LessThanOrEqualTo(String value) {
            addCriterion("question3 <=", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3Like(String value) {
            addCriterion("question3 like", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3NotLike(String value) {
            addCriterion("question3 not like", value, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3In(List<String> values) {
            addCriterion("question3 in", values, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3NotIn(List<String> values) {
            addCriterion("question3 not in", values, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3Between(String value1, String value2) {
            addCriterion("question3 between", value1, value2, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion3NotBetween(String value1, String value2) {
            addCriterion("question3 not between", value1, value2, "question3");
            return (Criteria) this;
        }

        public Criteria andQuestion4IsNull() {
            addCriterion("question4 is null");
            return (Criteria) this;
        }

        public Criteria andQuestion4IsNotNull() {
            addCriterion("question4 is not null");
            return (Criteria) this;
        }

        public Criteria andQuestion4EqualTo(String value) {
            addCriterion("question4 =", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4NotEqualTo(String value) {
            addCriterion("question4 <>", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4GreaterThan(String value) {
            addCriterion("question4 >", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4GreaterThanOrEqualTo(String value) {
            addCriterion("question4 >=", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4LessThan(String value) {
            addCriterion("question4 <", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4LessThanOrEqualTo(String value) {
            addCriterion("question4 <=", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4Like(String value) {
            addCriterion("question4 like", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4NotLike(String value) {
            addCriterion("question4 not like", value, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4In(List<String> values) {
            addCriterion("question4 in", values, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4NotIn(List<String> values) {
            addCriterion("question4 not in", values, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4Between(String value1, String value2) {
            addCriterion("question4 between", value1, value2, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion4NotBetween(String value1, String value2) {
            addCriterion("question4 not between", value1, value2, "question4");
            return (Criteria) this;
        }

        public Criteria andQuestion5IsNull() {
            addCriterion("question5 is null");
            return (Criteria) this;
        }

        public Criteria andQuestion5IsNotNull() {
            addCriterion("question5 is not null");
            return (Criteria) this;
        }

        public Criteria andQuestion5EqualTo(String value) {
            addCriterion("question5 =", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5NotEqualTo(String value) {
            addCriterion("question5 <>", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5GreaterThan(String value) {
            addCriterion("question5 >", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5GreaterThanOrEqualTo(String value) {
            addCriterion("question5 >=", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5LessThan(String value) {
            addCriterion("question5 <", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5LessThanOrEqualTo(String value) {
            addCriterion("question5 <=", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5Like(String value) {
            addCriterion("question5 like", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5NotLike(String value) {
            addCriterion("question5 not like", value, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5In(List<String> values) {
            addCriterion("question5 in", values, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5NotIn(List<String> values) {
            addCriterion("question5 not in", values, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5Between(String value1, String value2) {
            addCriterion("question5 between", value1, value2, "question5");
            return (Criteria) this;
        }

        public Criteria andQuestion5NotBetween(String value1, String value2) {
            addCriterion("question5 not between", value1, value2, "question5");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusIsNull() {
            addCriterion("corpus_status is null");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusIsNotNull() {
            addCriterion("corpus_status is not null");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusEqualTo(Integer value) {
            addCriterion("corpus_status =", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusNotEqualTo(Integer value) {
            addCriterion("corpus_status <>", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusGreaterThan(Integer value) {
            addCriterion("corpus_status >", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("corpus_status >=", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusLessThan(Integer value) {
            addCriterion("corpus_status <", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusLessThanOrEqualTo(Integer value) {
            addCriterion("corpus_status <=", value, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusIn(List<Integer> values) {
            addCriterion("corpus_status in", values, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusNotIn(List<Integer> values) {
            addCriterion("corpus_status not in", values, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusBetween(Integer value1, Integer value2) {
            addCriterion("corpus_status between", value1, value2, "corpusStatus");
            return (Criteria) this;
        }

        public Criteria andCorpusStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("corpus_status not between", value1, value2, "corpusStatus");
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