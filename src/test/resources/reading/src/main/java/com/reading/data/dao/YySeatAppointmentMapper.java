package com.reading.data.dao;

import com.reading.data.model.YySeatAppointment;
import com.reading.data.model.YySeatAppointmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YySeatAppointmentMapper {
    int countByExample(YySeatAppointmentExample example);

    int deleteByExample(YySeatAppointmentExample example);

    int deleteByPrimaryKey(Long keyid);

    int insert(YySeatAppointment record);

    int insertSelective(YySeatAppointment record);

    List<YySeatAppointment> selectByExample(YySeatAppointmentExample example);

    YySeatAppointment selectByPrimaryKey(Long keyid);

    int updateByExampleSelective(@Param("record") YySeatAppointment record, @Param("example") YySeatAppointmentExample example);

    int updateByExample(@Param("record") YySeatAppointment record, @Param("example") YySeatAppointmentExample example);

    int updateByPrimaryKeySelective(YySeatAppointment record);

    int updateByPrimaryKey(YySeatAppointment record);

    Integer getAppointmentSeatCount(@Param("sql") String  sql);

    Integer getOKAppointmentSeatCount(@Param("sql") String  sql);
}