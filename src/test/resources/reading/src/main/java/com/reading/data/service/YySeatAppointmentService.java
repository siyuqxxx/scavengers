package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.YySeatAppointment;
import com.reading.data.model.YySeatAppointmentExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface YySeatAppointmentService extends CurdService<YySeatAppointment> {
   List<YySeatAppointment> selectByExample(int p, int size , YySeatAppointmentExample example);
   List<YySeatAppointment> selectByExample(YySeatAppointmentExample example);
   int countByExample(YySeatAppointmentExample yySeatAppointmentExample);

   int getAppointmentSeatCount (String  sql);

   //这个用yy_seat_appointment的SeatId和yy_seat_info的keyId做了关联
   int getOKAppointmentSeatCount(String sql);
}
