package com.reading.data.service.impl;

import com.reading.data.dao.WechatThreeAuthorizeMapper;
import com.reading.data.model.WechatThreeAuthorize;
import com.reading.data.model.WechatThreeAuthorizeExample;
import com.reading.data.service.WechatThreeAuthorizeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WechatThreeAuthorizeServiceImpl implements WechatThreeAuthorizeService {
    @Resource
    WechatThreeAuthorizeMapper mapper;

    @Override
    public List<WechatThreeAuthorize> selectByExample(WechatThreeAuthorizeExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public WechatThreeAuthorize find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WechatThreeAuthorize> select() {
        return null;
    }

    @Override
    public List<WechatThreeAuthorize> page(int page, int size) {
        return null;
    }

    @Override
    public WechatThreeAuthorize add(WechatThreeAuthorize bean) {
        mapper.insertSelective(bean);
        return find(bean.getId());
    }

    @Override
    public WechatThreeAuthorize save(WechatThreeAuthorize bean) {

        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getId());
    }

    @Override
    public int count() {
        WechatThreeAuthorizeExample example=new WechatThreeAuthorizeExample();
        return mapper.countByExample(example);
    }
}
