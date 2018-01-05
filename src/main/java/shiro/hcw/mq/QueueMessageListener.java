package shiro.hcw.mq;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shiro.hcw.pojo.User;
import shiro.hcw.service.UserService;

import javax.jms.*;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2018/1/2 17:55
 * Description:在实际项目中，我们很少会自己手动去获取消息，如果需要手动去获取消息，那就没有必要使用到ActiveMq了，可以用一个Redis 就足够了。
               不能手动去获取消息，那么我们就可以选择使用一个监听器来监听是否有消息到达，这样子可以很快的完成对消息的处理。
 * Others:监听器实际上会消费队列中的消息
 */
//@Service
public class QueueMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private Destination destination;
    @Autowired
    UserService userService;

    @Override
    public void onMessage(Message message) {

        try {
            logger.info("QueueMessageListener监听到了消息：[{}]",message.getJMSDestination().toString());
            // 接受文本类型的消息
            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;
                try {
                    logger.info("发送的文本消息内容为：{}",text.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            // 接受Map类型的消息
            if (message instanceof MapMessage) {
                MapMessage map = (MapMessage) message;
                try {
                    logger.info("发送的map消息内容为：{}",map.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 接收object类型的消息
            if (message instanceof ActiveMQObjectMessage) {
                ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage) message;
                User user = null;
                try {
                    if (objectMessage != null) {
                        user = (User) objectMessage.getObject();
                    }
                    if (user != null) {
                        userService.insert(user);
                        logger.info("从队列 【{}】收到了消息：{}", destination.toString(), user.toString());
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            logger.error("error:{}",e.getMessage());
        }


//        try {
//            logger.info("QueueMessageListener监听到了文本消息：{}", tm.getObject());
////            consumerService.receive(destination);
//            //do something ...
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
