package ds.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Producer {

    private final static String QUEUE_NAME = "rabbit";
    private final static String HOST_NAME = "roedeer.rmq.cloudamqp.com";
    private final static String HOST_PASS = "jSFPwZod7dGTKQ5V-Ex8zEKdxAxuNAMd";
    private final static String HOST_USER = "pnwczjmz";

    public List<ActivityEntry> readDataFromFile() {

        List<ActivityEntry> monitoredData = new ArrayList<ActivityEntry>();
        String file = "src/main/resources/activity.txt";
        ///Get data from file
        try {
            Stream<String> stream = Files.lines(Paths.get(file));

            monitoredData = stream.map(line -> line.split("\t\t")).map(a -> new ActivityEntry(a[0], a[1], a[2]))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ActivityEntry m : monitoredData) {
            System.out.println(m);
        }

        return monitoredData;
    }

    public void send() {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(HOST_USER);
        factory.setPassword(HOST_PASS);
        factory.setHost(HOST_NAME);
        factory.setVirtualHost(HOST_USER);

        //factory.setPort(portNumber);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            List<ActivityEntry> data = this.readDataFromFile();
            ObjectMapper objectMapper = new ObjectMapper();
            for(ActivityEntry activity: data){
                String message = objectMapper.writeValueAsString(activity);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }

        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
