package com.ecc.batch.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * ActiveMQ Queue配置.
 */
@Configuration
public class QueueConfig {


    @Bean
    public Queue r360ScoreQueue() {
        return new ActiveMQQueue("r360.score.start");
    }

}
