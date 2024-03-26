package com.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * 开发公司：联信
 * 版权：联信
 * <p>
 * Annotation
 *
 * @author 刘志强
 * @created Create Time: 2021/3/9
 */
@Configuration
public class QueueConfig {

    /**
     * 延迟队列 用于存放预约功能过来的消息
     * @return
     */
    @Bean(name = "queue.delay")
    public Queue delayQueue() {
        return new ActiveMQQueue("queue.delay");
    }

    /**
     * 正常的队列
     * @return
     */
    @Bean(name = "queue.common")
    public Queue commonQueue() {
        return new ActiveMQQueue("queue.common");
    }
}
