package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.YyTimeConfig;
import com.reading.data.model.YyTimeConfigExample;

import java.util.List;

public interface YyTimeConfigService extends CurdService<YyTimeConfig> {

    List<YyTimeConfig> selectByExample(YyTimeConfigExample example);

}
