package com.ecc.batch.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caihaibo on 2017/10/13.
 * 测试
 */
@Slf4j
public class RateCalculationJob implements SimpleJob {


    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("费率跑批开始");
    }
}
