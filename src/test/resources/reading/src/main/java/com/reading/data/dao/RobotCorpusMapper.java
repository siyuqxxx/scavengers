package com.reading.data.dao;

import com.reading.data.model.RobotCorpus;
import com.reading.data.model.RobotCorpusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotCorpusMapper {
    int countByExample(RobotCorpusExample example);

    int deleteByExample(RobotCorpusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobotCorpus record);

    int insertSelective(RobotCorpus record);

    List<RobotCorpus> selectByExample(RobotCorpusExample example);

    RobotCorpus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobotCorpus record, @Param("example") RobotCorpusExample example);

    int updateByExample(@Param("record") RobotCorpus record, @Param("example") RobotCorpusExample example);

    int updateByPrimaryKeySelective(RobotCorpus record);

    int updateByPrimaryKey(RobotCorpus record);
}