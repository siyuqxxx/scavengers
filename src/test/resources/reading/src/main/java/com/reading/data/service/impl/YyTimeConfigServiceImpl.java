package com.reading.data.service.impl;

import com.reading.data.dao.YyTimeConfigMapper;
import com.reading.data.model.YyTimeConfig;
import com.reading.data.model.YyTimeConfigExample;
import com.reading.data.service.YyTimeConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class YyTimeConfigServiceImpl implements YyTimeConfigService {
    @Resource
    private YyTimeConfigMapper mapper;

    @Override
    public YyTimeConfig find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YyTimeConfig> select() {
        YyTimeConfigExample configExample=new YyTimeConfigExample();
        return mapper.selectByExample(configExample);
    }

    @Override
    public List<YyTimeConfig> page(int page, int size) {
        return null;
    }

    @Override
    public YyTimeConfig add(YyTimeConfig bean) {
        mapper.insertSelective(bean);

        return find(bean.getId());
    }

    @Override
    public YyTimeConfig save(YyTimeConfig bean) {
        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getId());
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<YyTimeConfig> selectByExample(YyTimeConfigExample example) {
        return mapper.selectByExample(example);
    }
}
