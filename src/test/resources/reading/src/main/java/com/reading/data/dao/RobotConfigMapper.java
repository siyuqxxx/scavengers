package com.reading.data.dao;

import com.reading.data.model.RobotConfig;
import com.reading.data.model.RobotConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotConfigMapper {
    int countByExample(RobotConfigExample example);

    int deleteByExample(RobotConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobotConfig record);

    int insertSelective(RobotConfig record);

    List<RobotConfig> selectByExample(RobotConfigExample example);

    RobotConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobotConfig record, @Param("example") RobotConfigExample example);

    int updateByExample(@Param("record") RobotConfig record, @Param("example") RobotConfigExample example);

    int updateByPrimaryKeySelective(RobotConfig record);

    int updateByPrimaryKey(RobotConfig record);
}