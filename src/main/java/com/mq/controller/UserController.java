package com.mq.controller;


import com.mq.config.Producer;
import com.mq.domain.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;


/**
 * @author liuzhiqiang
 */
@RestController
@ResponseBody
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private Producer producer;

    @Autowired
    @Qualifier("queue.delay")
    private Queue delayQueue;

    @Autowired
    @Qualifier("queue.common")
    private Queue commonQueue;

    /**
     * 普通消息
     * @return
     */
    @GetMapping("/queue")
    public String queue() {
        // 构建消息实体
        MessageModel<String> messageModel = MessageModel.<String>builder()
                .message("消息内容")
                .title("消息标题==这是正常的消息")
                .data("你好，queue")
                .build();
        // 发送消息
        producer.send(commonQueue, messageModel);
        return "queue 发送成功";
    }

    /**
     * 延迟消息
     * @return
     */
    @GetMapping("/delay")
    public String delay() {
        // 构建消息实体
        MessageModel<String> messageModel = MessageModel.<String>builder()
                .message("消息内容")
                .title("消息标题==这是延迟的消息。发送的时间为" + System.currentTimeMillis())
                .data("你好，queue")
                .build();
        // 将消息发送至预约
        producer.delaySend(delayQueue, messageModel, 10 * 1000L);
        return "delay 发送成功";
    }


}