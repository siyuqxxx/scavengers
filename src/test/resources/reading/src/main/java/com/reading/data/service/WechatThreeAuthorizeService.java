package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.WechatThreeAuthorize;
import com.reading.data.model.WechatThreeAuthorizeExample;

import java.util.List;

public interface WechatThreeAuthorizeService extends CurdService<WechatThreeAuthorize> {

    List<WechatThreeAuthorize> selectByExample(WechatThreeAuthorizeExample example);
}
