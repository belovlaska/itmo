package client;
import client.cli.Runner;
import client.network.TCPClient;
import client.utils.console.Console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import java.lang.System;
import java.net.InetAddress;
import java.net.InetSocketAddress;


public class App {
    public static final Logger logger = LogManager.getLogger("ClientLogger");
    private static final int PORT = 18019;

    public static void main(String[] args){
        var console = new Console();
        try{
            var client = new TCPClient(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
            var cli = new Runner(client, console);

            cli.interactiveMode();
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
        }
        catch (IOException e) {
            logger.info("Can not connect to server.", e);
            System.out.println("Can not connect to server!");
        }
    }


}
