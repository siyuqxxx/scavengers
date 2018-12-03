package com.reading.data.service.impl;

import com.reading.data.dao.YySeatAppointmentMapper;
import com.reading.data.model.YySeatAppointment;
import com.reading.data.model.YySeatAppointmentExample;
import com.reading.data.service.YySeatAppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
@Service
public class YySeatAppointmentServiceImpl implements YySeatAppointmentService {
    @Resource
    private YySeatAppointmentMapper mapper;

    @Override
    public YySeatAppointment find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YySeatAppointment> select() {
        YySeatAppointmentExample example = new YySeatAppointmentExample();
        example.createCriteria().andStatusEqualTo(1);
        return mapper.selectByExample(example);
    }

    @Override
    public List<YySeatAppointment> page(int page, int size) {
        YySeatAppointmentExample example = new YySeatAppointmentExample();
        example.setLimitStart((page - 1) * size);
        example.setLimitEnd(size);
        example.createCriteria().andStatusEqualTo(1);
        return mapper.selectByExample(example);
    }

    @Override
    public YySeatAppointment add(YySeatAppointment bean) {
        mapper.insertSelective(bean);
        return find(bean.getKeyid());
    }

    @Override
    public YySeatAppointment save(YySeatAppointment bean) {
        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getKeyid());
    }

    @Override
    public int count() {
        YySeatAppointmentExample example = new YySeatAppointmentExample();
        example.createCriteria().andStatusEqualTo(1);
        return mapper.countByExample(example);
    }

    @Override
    public List<YySeatAppointment> selectByExample(int p, int size, YySeatAppointmentExample example) {
        example.setLimitStart((p - 1) * size);
        example.setLimitEnd(size);
        return mapper.selectByExample(example);
    }

    @Override
    public List<YySeatAppointment> selectByExample(YySeatAppointmentExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int countByExample(YySeatAppointmentExample yySeatAppointmentExample) {
        return mapper.countByExample(yySeatAppointmentExample);
    }

    @Override
    public int getAppointmentSeatCount(String sql) {
        Integer count = mapper.getAppointmentSeatCount(sql);

        if (count != null) {
            return count;
        }
        return 0;

    }

    //这个用yy_seat_appointment的SeatId和yy_seat_info的keyId做了关联
    @Override
    public int getOKAppointmentSeatCount(String sql) {
        Integer count = mapper.getOKAppointmentSeatCount(sql);

        if (count != null) {
            return count;
        }
        return 0;

    }
}
