package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.WechatThreeFuncation;
import com.reading.data.model.WechatThreeFuncationExample;

import java.util.List;

public interface WechatThreeFuncationService extends CurdService<WechatThreeFuncation> {

    List<WechatThreeFuncation> selectByExample(WechatThreeFuncationExample example);

}
