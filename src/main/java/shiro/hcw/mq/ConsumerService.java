package shiro.hcw.mq;

import com.google.common.eventbus.AsyncEventBus;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import shiro.hcw.pojo.User;
import shiro.hcw.service.UserService;

import javax.jms.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2018/1/2 17:59
 * Description:
 * Others:
 */
@Service
public class ConsumerService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    UserService userService;

//    @Autowired
//    private AsyncEventBus asyncEventBus;

    /**
     * 不继承MessageListener时可以用consumerService.receive()手动接受消息
     * @param destination
     * @return
     */
    public Message receive(Destination destination) {
        Message message = jmsTemplate.receive(destination);

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
                    logger.info("从队列 【{}】收到了消息：{}", destination.toString(), user.toString());
                    userService.insert(user);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        return message;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
