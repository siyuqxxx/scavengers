package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.RobotConfig;
import com.reading.data.model.RobotConfigExample;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */
public interface RobotConfigService extends CurdService<RobotConfig> {

    List<RobotConfig> selectByExample(RobotConfigExample example);

}
