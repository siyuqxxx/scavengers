package com.reading.data.service.impl;

import com.reading.data.dao.RobotCorpusMapper;
import com.reading.data.model.RobotCorpus;
import com.reading.data.model.RobotCorpusExample;
import com.reading.data.service.RobotCorpusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */
@Service
public class RobotCorpusServiceImpl implements RobotCorpusService {
    @Resource
    private RobotCorpusMapper mapper;

    @Override
    public RobotCorpus find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RobotCorpus> select() {
        return null;
    }

    @Override
    public List<RobotCorpus> page(int page, int size) {
        return null;
    }

    @Override
    public RobotCorpus add(RobotCorpus bean) {

        mapper.insertSelective(bean);
        return find(bean.getId());
    }

    @Override
    public RobotCorpus save(RobotCorpus bean) {
        bean.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getId());
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<RobotCorpus> selectByExample(RobotCorpusExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int countByExample(RobotCorpusExample example) {
        return mapper.countByExample(example);
    }
}
