package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.RobotConsultRecord;
import com.reading.data.model.RobotConsultRecordExample;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */
public interface RobotConsultRecordService extends CurdService<RobotConsultRecord> {

    List<RobotConsultRecord> selectByExample(RobotConsultRecordExample example);

    List<RobotConsultRecord> selectBySql(String sql);

}
