package com.reading.data.service.impl;

import com.reading.data.dao.RobotConfigMapper;
import com.reading.data.model.RobotConfig;
import com.reading.data.model.RobotConfigExample;
import com.reading.data.service.RobotConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */
@Service
public class RobotConfigServiceImpl implements RobotConfigService {
    @Resource
    private RobotConfigMapper mapper;

    @Override
    public RobotConfig find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RobotConfig> select() {
        return null;
    }

    @Override
    public List<RobotConfig> page(int page, int size) {
        RobotConfigExample example = new RobotConfigExample();
        example.setOrderByClause("id desc");
        example.setLimitStart((page - 1) * size);
        example.setLimitEnd(size);
        example.createCriteria().andStatusEqualTo(1);
        return mapper.selectByExample(example);
    }

    @Override
    public RobotConfig add(RobotConfig bean) {

        mapper.insertSelective(bean);
        return find(bean.getId());
    }

    @Override
    public RobotConfig save(RobotConfig bean) {

        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getId());
    }

    @Override
    public int count() {
        RobotConfigExample example = new RobotConfigExample();
        example.createCriteria().andStatusEqualTo(1);
        return mapper.countByExample(example);
    }

    @Override
    public List<RobotConfig> selectByExample(RobotConfigExample example) {
        return mapper.selectByExample(example);
    }
}
