package com.hlf.poc;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Enumeration;

/**
 * Created by howard.fackrell on 8/13/14.
 */
public class MessageProducer {

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
            if (i % 100 == 0) System.out.println(i);
            for (String s : args) {
                producer.sendMessage(s + i);
            }
        }
        System.out.println("All done!!");
    }
}
