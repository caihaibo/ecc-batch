package com.ecc.batch.config.job.simple;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.ecc.batch.job.RateCalculationJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by caihaibo on 2018/10/13.
 * 费率计算
 */
@Configuration
public class RateCalculationConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public RateCalculationJob rateCalculationJob(){
        return new RateCalculationJob();
    }
    @Bean(initMethod = "init")
    public JobScheduler rateCalculationJJobScheduler(final RateCalculationJob rateCalculationJob, @Value("${rateCalculationJob.cron}") final String cron, @Value("${rateCalculationJob.shardingTotalCount}") final int shardingTotalCount,
                                         @Value("${rateCalculationJob.shardingItemParameters}") final String shardingItemParameters){
        return new SpringJobScheduler(rateCalculationJob,regCenter,getLiteJobConfiguration(rateCalculationJob.getClass(),cron,shardingTotalCount,shardingItemParameters));
    }
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters){
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
