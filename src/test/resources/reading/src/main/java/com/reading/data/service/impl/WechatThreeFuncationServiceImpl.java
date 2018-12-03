package com.reading.data.service.impl;

import com.reading.data.dao.WechatThreeFuncationMapper;
import com.reading.data.model.WechatThreeFuncation;
import com.reading.data.model.WechatThreeFuncationExample;
import com.reading.data.service.WechatThreeFuncationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WechatThreeFuncationServiceImpl implements WechatThreeFuncationService {
    @Resource
    WechatThreeFuncationMapper mapper;

    @Override
    public List<WechatThreeFuncation> selectByExample(WechatThreeFuncationExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public WechatThreeFuncation find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WechatThreeFuncation> select() {
        return null;
    }

    @Override
    public List<WechatThreeFuncation> page(int page, int size) {
        return null;
    }

    @Override
    public WechatThreeFuncation add(WechatThreeFuncation bean) {
        return null;
    }

    @Override
    public WechatThreeFuncation save(WechatThreeFuncation bean) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
