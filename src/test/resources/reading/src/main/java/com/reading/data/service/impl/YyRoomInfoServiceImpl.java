package com.reading.data.service.impl;

import com.reading.data.dao.YyRoomInfoMapper;
import com.reading.data.model.YyRoomInfo;
import com.reading.data.model.YyRoomInfoExample;
import com.reading.data.service.YyRoomInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
@Service
public class YyRoomInfoServiceImpl implements YyRoomInfoService{
    @Resource
    private YyRoomInfoMapper mapper;

    @Override
    public YyRoomInfo find(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YyRoomInfo> select() {
        YyRoomInfoExample example = new YyRoomInfoExample();
        return mapper.selectByExample(example);
    }

    @Override
    public List<YyRoomInfo> page(int page, int size) {
        YyRoomInfoExample example = new YyRoomInfoExample();
        example.setLimitEnd(size);
        example.setLimitStart((page - 1) * size);
        return mapper.selectByExample(example);
    }

    @Override
    public YyRoomInfo add(YyRoomInfo bean) {
        mapper.insertSelective(bean);
        return find(bean.getKeyid());
    }

    @Override
    public YyRoomInfo save(YyRoomInfo bean) {
        mapper.updateByPrimaryKeySelective(bean);
        return find(bean.getKeyid());
    }

    @Override
    public int count() {
        YyRoomInfoExample example = new YyRoomInfoExample();
        return mapper.countByExample(example);
    }

    @Override
    public List<YyRoomInfo> selectByExample(int p, int size, YyRoomInfoExample example) {
        example.setLimitStart((p - 1) * size);
        example.setLimitEnd(size);
        return mapper.selectByExample(example);
    }

    @Override
    public List<YyRoomInfo> selectByExample(YyRoomInfoExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int countByExample(YyRoomInfoExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public void adds(YyRoomInfo bean) {
        mapper.insertSelective(bean);
    }

    @Override
    public List<YyRoomInfo> selectByBuildingIdAndDevice(Integer floorId) {
        return mapper.selectByBuildingIdAndDevice(floorId);
    }

    @Override
    public void updateRestrictedAppointment(Long libraryId) {
        mapper.updateRestrictedAppointment(libraryId);
    }

}
