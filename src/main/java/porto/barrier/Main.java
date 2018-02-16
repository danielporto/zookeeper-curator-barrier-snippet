package porto.barrier;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.PropertyConfigurator;

import java.util.logging.Logger;


/*
 * This  is a simple project to showcase how to use zookeeper and curator
 * to build a barrier
 * */


public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws Exception {
        // set logging capabilities
        String log4jConfPath = "log4j.properties";
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s: %2$s - %5$s%6$s%n");
        PropertyConfigurator.configure(log4jConfPath);


        initializeClientCoodinator();
        LOGGER.info("Bye!!");
    }


    public static void initializeClientCoodinator() throws Exception {
        //coordination
        CuratorFramework curatorClient;
        String connectionString = "";
        RetryPolicy retryPolicy;
        int connectionDelay = 0;
        int connectionRetries = 0;
        DistributedDoubleBarrier barrier;
        int numberOfServiceReplicas = 0;
        String name = "zookeeper-3.5.3-beta";
        String groupName;
        String zkPath = "";


        connectionString = "zoo1:2181";
//        connectionString = "zoo1:2181,zoo2:2181,zoo3:2181,";

        connectionDelay = 1000;
        connectionRetries = 3;
        groupName = "barrier_group";
        numberOfServiceReplicas = 2;
        zkPath = "/" + groupName + "/barrier/g" + numberOfServiceReplicas;
        retryPolicy = new ExponentialBackoffRetry(connectionDelay, connectionRetries);
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        barrier = new DistributedDoubleBarrier(curatorClient, zkPath, numberOfServiceReplicas);
        LOGGER.info("Zookeeper communication layer created.");

        // connect to the controller
        curatorClient.start();
        LOGGER.info("Zookeeper communication layer starting. Block until connection is established.");

        // block until connection is established
        curatorClient.blockUntilConnected();
        LOGGER.info("Zookeeper communication layer started successfully!!");

        LOGGER.info("waiting on the barrier");
        barrier.enter();

        LOGGER.info("Leaving the barrier");
        barrier.leave();

        LOGGER.info("Closing connection to gracefully");
        curatorClient.close();

    }

}
