package com.batook.orcl;

import oracle.jms.*;
import oracle.sql.ORADataFactory;

import javax.jms.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

public class Test_JMS {
    public static void main(String argv[]) throws JMSException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        String url = "jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe";
        Properties props = new Properties();
        props.setProperty("user", "batook");
        props.setProperty("password", "gfhjdjp");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        java.sql.Connection conn = DriverManager.getConnection(url, props);
        QueueConnection qconn = AQjmsQueueConnectionFactory.createQueueConnection(conn);
        QueueSession qsess = qconn.createQueueSession(true, 0);
        Queue q = qsess.createQueue("clearance_queue");
        QueueSender qsend = qsess.createSender(q);
        TextMessage msg;
        msg = qsess.createTextMessage("TEST JAVA");
        qsend = qsess.createSender(q);
        qsend.send(msg);
        qsess.commit();
    }

}

