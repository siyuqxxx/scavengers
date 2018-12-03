package com.reading.data.model;

import com.reading.base.BaseModel;

import java.util.List;

public class YyTimeSetting extends BaseModel {
    private Long keyid;

    private Long libraryid;

    private String libraryname;

    private Integer dayTime;

    private Integer isOpenSign;

    private Integer minute;

    private String timeMark;

    private Integer status;

    private int number;

    //starttime,endtime,zmStartTime,zmEndTime 这四个字段数据库已删除
    private String starttime;

    private String endtime;

    private String zmStartTime;

    private String zmEndTime;

    private List<YyTimeSettingDetail> timeSettingDetailList;

    private String timeSettingDetail;

    public Long getKeyid() {
        return keyid;
    }

    public void setKeyid(Long keyid) {
        this.keyid = keyid;
    }

    public Long getLibraryid() {
        return libraryid;
    }

    public void setLibraryid(Long libraryid) {
        this.libraryid = libraryid;
    }

    public String getLibraryname() {
        return libraryname;
    }

    public void setLibraryname(String libraryname) {
        this.libraryname = libraryname == null ? null : libraryname.trim();
    }

    public Integer getDayTime() {
        return dayTime;
    }

    public void setDayTime(Integer dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getIsOpenSign() {
        return isOpenSign;
    }

    public void setIsOpenSign(Integer isOpenSign) {
        this.isOpenSign = isOpenSign;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getTimeMark() {
        return timeMark;
    }

    public void setTimeMark(String timeMark) {
        this.timeMark = timeMark == null ? null : timeMark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<YyTimeSettingDetail> getTimeSettingDetailList() {
        return timeSettingDetailList;
    }

    public void setTimeSettingDetailList(List<YyTimeSettingDetail> timeSettingDetailList) {
        this.timeSettingDetailList = timeSettingDetailList;
    }


    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }


    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setZmStartTime(String zmStartTime) {
        this.zmStartTime = zmStartTime;
    }


    public void setZmEndTime(String zmEndTime) {
        this.zmEndTime = zmEndTime;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getZmStartTime() {
        return zmStartTime;
    }

    public String getZmEndTime() {
        return zmEndTime;
    }

    public String getTimeSettingDetail() {
        return timeSettingDetail;
    }

    public void setTimeSettingDetail(String timeSettingDetail) {
        this.timeSettingDetail = timeSettingDetail;
    }
}