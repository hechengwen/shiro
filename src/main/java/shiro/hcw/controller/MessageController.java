package shiro.hcw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.hcw.mq.ConsumerService;
import shiro.hcw.mq.ProducerService;
import shiro.hcw.pojo.User;
import shiro.hcw.utils.MD5Util;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2018/1/2 18:00
 * Description:
 * Others:
 */
@Controller
@RequestMapping(value = "message")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Destination destination;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/SendMessage", method = RequestMethod.GET)
    @ResponseBody
    public void send(String msg) {
        logger.info(Thread.currentThread().getName() + "------------send to jms Start");
        User user = new User();
        for (int i = 0; i < 10; i++) {
            try {
                user.setUserName("hechengwen" + i);
                user.setPassword(MD5Util.MD5("hechengwen" + i));
                user.setMobile("1771036389" + i);
                user.setCreateTime(new Date());
                if (i % 2 == 0) {
                    user.setRealName("zhangsan" + i);
                } else user.setRealName("lisi" + i);
                producerService.sendObjectMessage(destination, user);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
//        user.setUserName("hechengwen");
//        user.setPassword(MD5Util.MD5("hechengwen"));
//        user.setMobile("17710363898");
//        user.setCreateTime(new Date());
//        producerService.sendObjectMessage(destination, user);
        logger.info(Thread.currentThread().getName() + "------------send to jms End");
    }

    @RequestMapping(value= "/ReceiveMessage",method = RequestMethod.GET)
    @ResponseBody
    public String receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        Message tm = consumerService.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        return tm.toString();
    }

    /**
     * * 序列化对象
     * * @param object
     * * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
