package com.reading.data.service.impl;

import com.reading.data.dao.RobotConsultRecordMapper;
import com.reading.data.model.RobotConsultRecord;
import com.reading.data.model.RobotConsultRecordExample;
import com.reading.data.service.RobotConsultRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */
@Service
public class RobotConsultRecordServiceImpl implements RobotConsultRecordService {
    @Resource
    private RobotConsultRecordMapper mapper;

    @Override
    public RobotConsultRecord find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RobotConsultRecord> select() {
        return null;
    }

    @Override
    public List<RobotConsultRecord> page(int page, int size) {
        return null;
    }

    @Override
    public RobotConsultRecord add(RobotConsultRecord bean) {

        mapper.insertSelective(bean);
        return find(bean.getId());
    }

    @Override
    public RobotConsultRecord save(RobotConsultRecord bean) {
        mapper.updateByPrimaryKeySelective(bean);

        return find(bean.getId());
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<RobotConsultRecord> selectByExample(RobotConsultRecordExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<RobotConsultRecord> selectBySql(String sql) {
        return mapper.selectBySql(sql);
    }
}
