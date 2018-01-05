package shiro.hcw.mq;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import shiro.hcw.pojo.User;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2018/1/2 17:57
 * Description:
 * Others:
 */
@Service
public class ProducerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendObjectMessage(Destination destination, User user){

        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                logger.info("{}:向队列【{}】发送消息---------------------->{}",Thread.currentThread().getName(),destination.toString(),user);
                ActiveMQObjectMessage message = (ActiveMQObjectMessage) session.createObjectMessage();
                message.setObject((Serializable) user);
                return message;
            }
        });
    }

    public void sendMessage(final String msg){
        String destination = jmsTemplate.getDefaultDestinationName();
        logger.info("{}:向队列【{}】发送消息---------------------->{}",Thread.currentThread().getName(),destination.toString(),msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
