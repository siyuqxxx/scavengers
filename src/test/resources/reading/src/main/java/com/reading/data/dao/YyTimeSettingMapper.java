package com.reading.data.dao;

import com.reading.data.model.YyTimeSetting;
import com.reading.data.model.YyTimeSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YyTimeSettingMapper {
    int countByExample(YyTimeSettingExample example);

    int deleteByExample(YyTimeSettingExample example);

    int deleteByPrimaryKey(Long keyid);

    int insert(YyTimeSetting record);

    int insertSelective(YyTimeSetting record);

    List<YyTimeSetting> selectByExample(YyTimeSettingExample example);

    YyTimeSetting selectByPrimaryKey(Long keyid);

    int updateByExampleSelective(@Param("record") YyTimeSetting record, @Param("example") YyTimeSettingExample example);

    int updateByExample(@Param("record") YyTimeSetting record, @Param("example") YyTimeSettingExample example);

    int updateByPrimaryKeySelective(YyTimeSetting record);

    int updateByPrimaryKey(YyTimeSetting record);
}