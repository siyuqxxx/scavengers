package com.reading.data.dao;

import com.reading.data.model.YyRoomInfo;
import com.reading.data.model.YyRoomInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YyRoomInfoMapper {
    int countByExample(YyRoomInfoExample example);

    int deleteByExample(YyRoomInfoExample example);

    int deleteByPrimaryKey(Long keyid);

    int insert(YyRoomInfo record);

    int insertSelective(YyRoomInfo record);

    List<YyRoomInfo> selectByExample(YyRoomInfoExample example);

    YyRoomInfo selectByPrimaryKey(Long keyid);

    int updateByExampleSelective(@Param("record") YyRoomInfo record, @Param("example") YyRoomInfoExample example);

    int updateByExample(@Param("record") YyRoomInfo record, @Param("example") YyRoomInfoExample example);

    int updateByPrimaryKeySelective(YyRoomInfo record);

    int updateByPrimaryKey(YyRoomInfo record);

    List<YyRoomInfo> selectByBuildingIdAndDevice(Integer floorId);

    void updateRestrictedAppointment(Long libraryId);
}