package com.example.order.mq.dto;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @ClassName BizCorrelationData
 * @Description 用于RabbitMQ接收发送失败回调信息
 * @Author hyg
 * @Date 2026/9/2 15:02
 */
public class BizCorrelationData extends CorrelationData {
    // 业务标识：stock / order / pay
    private String bizType;
    // 消息体（失败时方便重试）
    private String msgBody;

    public BizCorrelationData(String id, String bizType, String msgBody) {
        super(id);
        this.bizType = bizType;
        this.msgBody = msgBody;
    }

    public String getBizType() {
        return bizType;
    }

    public String getMsgBody() {
        return msgBody;
    }
}
