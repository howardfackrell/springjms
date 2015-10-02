package com.hlf.poc;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Date;

/**
 * Created by howard.fackrell on 11/18/14.
 */
public class JmsTemplateTimeoutTest {

    ApplicationContext context;

    ApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("applicationConfig.xml");
        }
        return context;
    }

    void sendMessage(final String message) {
        JmsTemplate jmsTemplate = (JmsTemplate) getContext().getBean("jmsTemplate");
        Queue q = (Queue) getContext().getBean("queue");

        jmsTemplate.send(q, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ActiveMQTextMessage m = new ActiveMQTextMessage();
                m.setText(message);
                return m;
            }
        });
    }

    public static void main(String[] args){
        MessageProducer producer = new MessageProducer();

            for (int i = 0; i < 1000; i++) {
                try {
                Date now = new Date();
                System.out.println("sent " + i + " at " + now);
                producer.sendMessage(i + " " + now);
            } catch (Exception e) {
                e.printStackTrace();
            }
                sleep(i * 100);

            }

    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
