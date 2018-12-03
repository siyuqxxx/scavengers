package com.reading.data.model;

import com.reading.base.BaseModel;

import java.util.Objects;

public class YyTimeConfig extends BaseModel {
    private Long id;

    private Long libraryId;

    private String libraryName;

    private String layTime;

    private String sysTodStTime;

    private String sysTodEnTime;

    private String sysTomStTime;

    private String sysTomEnTime;

    private Integer time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YyTimeConfig config = (YyTimeConfig) o;
        return Objects.equals(id, config.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
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

    public String getLayTime() {
        return layTime;
    }

    public void setLayTime(String layTime) {
        this.layTime = layTime == null ? null : layTime.trim();
    }

    public String getSysTodStTime() {
        return sysTodStTime;
    }

    public void setSysTodStTime(String sysTodStTime) {
        this.sysTodStTime = sysTodStTime == null ? null : sysTodStTime.trim();
    }

    public String getSysTodEnTime() {
        return sysTodEnTime;
    }

    public void setSysTodEnTime(String sysTodEnTime) {
        this.sysTodEnTime = sysTodEnTime == null ? null : sysTodEnTime.trim();
    }

    public String getSysTomStTime() {
        return sysTomStTime;
    }

    public void setSysTomStTime(String sysTomStTime) {
        this.sysTomStTime = sysTomStTime == null ? null : sysTomStTime.trim();
    }

    public String getSysTomEnTime() {
        return sysTomEnTime;
    }

    public void setSysTomEnTime(String sysTomEnTime) {
        this.sysTomEnTime = sysTomEnTime == null ? null : sysTomEnTime.trim();
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}