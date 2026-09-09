package com.example.order.mq;

import com.example.order.mq.dto.BizCorrelationData;
import com.example.order.mq.dto.BizTypeConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        //在这里区分不同业务情景的回调

        //转换成自定义回调对象
        BizCorrelationData bizCorrelationData = (BizCorrelationData) correlationData;
        String bizType = bizCorrelationData.getBizType();
        //发送库存消息回调
        if (BizTypeConstants.STORAGE.equals(bizType)) {

        }
        //发送账户消息回调
        if (BizTypeConstants.ACCOUNT.equals(bizType)) {

        }
    }
}
