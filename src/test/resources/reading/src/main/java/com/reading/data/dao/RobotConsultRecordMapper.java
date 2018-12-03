package com.reading.data.dao;

import com.reading.data.model.RobotConsultRecord;
import com.reading.data.model.RobotConsultRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotConsultRecordMapper {
    int countByExample(RobotConsultRecordExample example);

    int deleteByExample(RobotConsultRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobotConsultRecord record);

    int insertSelective(RobotConsultRecord record);

    List<RobotConsultRecord> selectByExample(RobotConsultRecordExample example);

    RobotConsultRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobotConsultRecord record, @Param("example") RobotConsultRecordExample example);

    int updateByExample(@Param("record") RobotConsultRecord record, @Param("example") RobotConsultRecordExample example);

    int updateByPrimaryKeySelective(RobotConsultRecord record);

    int updateByPrimaryKey(RobotConsultRecord record);

    List<RobotConsultRecord> selectBySql(@Param("sql")String sql);
}