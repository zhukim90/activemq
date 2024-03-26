package com.mq.config;

import com.mq.domain.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * 开发公司：联信
 * 版权：联信
 * <p>
 * Annotation
 *
 * @author 刘志强
 * @created Create Time: 2021/3/9
 */
@Slf4j
@Service("producer")
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    /**
     * 发送消息至队列
     * @param queue 队列
     * @param data 消息内容
     */
    public void send(Queue queue, MessageModel<?> data) {
        jmsMessagingTemplate.convertAndSend(queue, data);
        log.info("发送消息普通消息：{}", data);
    }

    /**
     * 消息发送至预约
     * @param queue 队列
     * @param data 消息内容
     * @param time 延迟时间 毫秒
     */
    public void delaySend(Queue queue, MessageModel<?> data, Long time) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            // 获取连接工厂
            ConnectionFactory connectionFactory = jmsMessagingTemplate.getConnectionFactory();
            if (connectionFactory != null) {
                // 获取连接
                connection = connectionFactory.createConnection();
                connection.start();
                // 获取session，true开启事务，false关闭事务
                session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                // 创建一个消息队列
                producer = session.createProducer(queue);
                producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
                ObjectMessage message = session.createObjectMessage(data);
                //设置mq延迟计划 time毫秒后进入队列
                message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
                // 发送消息
                producer.send(message);
                log.info("发送消息延迟消息：{}", data);
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}