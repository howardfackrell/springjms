package com.hlf.poc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;
import java.util.Date;

/**
 * Created by howard.fackrell on 8/13/14.
 */
public class MessageConsumer implements MessageListener {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("consumerConfig.xml");
    }

    public final Date start = new Date();

    public long elapsed() {
        Date end = new Date();
        return end.getTime() - start.getTime();
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println( ((TextMessage) message).getText() + "- " + elapsed());
            }
            else if (message instanceof ObjectMessage) {
                System.out.println( ((ObjectMessage) message).getObject());
            }
            else if (message instanceof MapMessage) {
                System.out.println( ((MapMessage) message).getMapNames());
            }
            else {
                System.out.println( "That wasn't a TextMessage" );
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
