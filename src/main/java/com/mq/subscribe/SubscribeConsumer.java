package com.mq.subscribe;

/**
 * 开发公司：联信
 * 版权：联信
 * <p>
 * Annotation
 *
 * @author 刘志强
 * @created Create Time: 2021/3/5
 */

import com.mq.domain.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SubscribeConsumer {

    /**
     * 订阅消费 消费延迟队列
     * @param messageModel
     */
    @JmsListener(destination = "queue.delay")
    public void consumerDelayQueue(MessageModel<?> messageModel) {
        log.info("收到来自queue.delay的消息，时间[{}],内容[{}]",System.currentTimeMillis(),messageModel);
    }

    /**
     * 订阅普通的队列
     * @param messageModel
     */
    @JmsListener(destination = "queue.common")
    public void consumerCommonQueue(MessageModel<?> messageModel) {
        log.info("收到来自queue.common的消息，时间[{}],内容[{}]",System.currentTimeMillis(),messageModel);
    }
}