package com.reading.data.model;

import com.reading.base.BaseModel;
import java.util.Date;

public class RobotCorpus extends BaseModel {
    private Long id;

    private Long libraryid;

    private String question;

    private String tulingId;

    private String answer;

    private String question1;

    private String question2;

    private String question3;

    private String question4;

    private String question5;

    private Integer corpusStatus;

    private Date createTime;

    private Date updateTime;

    private int number ;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibraryid() {
        return libraryid;
    }

    public void setLibraryid(Long libraryid) {
        this.libraryid = libraryid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getTulingId() {
        return tulingId;
    }

    public void setTulingId(String tulingId) {
        this.tulingId = tulingId == null ? null : tulingId.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1 == null ? null : question1.trim();
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2 == null ? null : question2.trim();
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3 == null ? null : question3.trim();
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4 == null ? null : question4.trim();
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5 == null ? null : question5.trim();
    }

    public Integer getCorpusStatus() {
        return corpusStatus;
    }

    public void setCorpusStatus(Integer corpusStatus) {
        this.corpusStatus = corpusStatus;
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
}