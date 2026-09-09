package com.example.order.mq.dto;

/**
 * @ClassName BizTypeConstants
 * @Description RabbitMQ业务类型
 * @Author hyg
 * @Date 2026/9/2 15:39
 */
public final class BizTypeConstants {
    private BizTypeConstants() {}

    //订单业务
    public static final String ORDER = "order";

    //账户业务
    public static final String ACCOUNT = "account";

    //库存业务
    public static final String STORAGE = "storage";
}
