package com.reading.data.dao;

import com.reading.data.model.WechatThreeAuthorize;
import com.reading.data.model.WechatThreeAuthorizeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WechatThreeAuthorizeMapper {
    int countByExample(WechatThreeAuthorizeExample example);

    int deleteByExample(WechatThreeAuthorizeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatThreeAuthorize record);

    int insertSelective(WechatThreeAuthorize record);

    List<WechatThreeAuthorize> selectByExample(WechatThreeAuthorizeExample example);

    WechatThreeAuthorize selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatThreeAuthorize record, @Param("example") WechatThreeAuthorizeExample example);

    int updateByExample(@Param("record") WechatThreeAuthorize record, @Param("example") WechatThreeAuthorizeExample example);

    int updateByPrimaryKeySelective(WechatThreeAuthorize record);

    int updateByPrimaryKey(WechatThreeAuthorize record);
}