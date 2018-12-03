package com.reading.data.service;

import com.reading.base.CurdService;
import com.reading.data.model.RobotCorpus;
import com.reading.data.model.RobotCorpusExample;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */
public interface RobotCorpusService extends CurdService<RobotCorpus> {

    List<RobotCorpus> selectByExample(RobotCorpusExample example);

    int countByExample(RobotCorpusExample example);
}
