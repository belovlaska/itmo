package client;
import client.cli.Runner;
import client.network.TCPClient;
import client.utils.console.Console;

import common.exceptions.InvalidValueException;
import common.exceptions.WrongAmountOfElementsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import java.lang.System;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class App {

    private static Console console = new Console();
    public static final Logger logger = LogManager.getLogger("ClientLogger");
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    private static String host;
    private static int port;

    /**
     * It takes a String array of two elements,
     * and if the array has exactly two elements, it parses the second element as an integer,
     * and if the integer is in the range [0, 65535], it sets the host and port variables to the
     * corresponding values
     *
     * @param hostAndPortArgs the array of arguments passed to the main method.
     * @return Nothing.
     */
    private static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new InvalidValueException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(App.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            console.println("Usage: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            console.printError("The port must be represented by a number!");
        } catch (InvalidValueException exception) {
            console.printError("The port cannot be negative!");
        }
        return false;
    }

    public static void main(String[] args) {
        args = new String[]{"localhost", "1821"};
        if (!initializeConnectionAddress(args)) return;
        var userScanner = new Scanner(System.in);
        Runner runner = new Runner(userScanner);
        TCPClient client = new TCPClient(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, runner);
        client.run();
        userScanner.close();
    }
}
