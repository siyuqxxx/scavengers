package com.reading.data.dao;

import com.reading.data.model.YyTimeConfig;
import com.reading.data.model.YyTimeConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YyTimeConfigMapper {
    int countByExample(YyTimeConfigExample example);

    int deleteByExample(YyTimeConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YyTimeConfig record);

    int insertSelective(YyTimeConfig record);

    List<YyTimeConfig> selectByExample(YyTimeConfigExample example);

    YyTimeConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YyTimeConfig record, @Param("example") YyTimeConfigExample example);

    int updateByExample(@Param("record") YyTimeConfig record, @Param("example") YyTimeConfigExample example);

    int updateByPrimaryKeySelective(YyTimeConfig record);

    int updateByPrimaryKey(YyTimeConfig record);
}