package com.reading.data.dao;

import com.reading.data.model.WechatThreeFuncation;
import com.reading.data.model.WechatThreeFuncationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WechatThreeFuncationMapper {
    int countByExample(WechatThreeFuncationExample example);

    int deleteByExample(WechatThreeFuncationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatThreeFuncation record);

    int insertSelective(WechatThreeFuncation record);

    List<WechatThreeFuncation> selectByExample(WechatThreeFuncationExample example);

    WechatThreeFuncation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatThreeFuncation record, @Param("example") WechatThreeFuncationExample example);

    int updateByExample(@Param("record") WechatThreeFuncation record, @Param("example") WechatThreeFuncationExample example);

    int updateByPrimaryKeySelective(WechatThreeFuncation record);

    int updateByPrimaryKey(WechatThreeFuncation record);
}