package client;
import client.cli.Runner;

import client.network.TCPClient;
import client.utils.console.Console;

import common.exceptions.InvalidValueException;
import common.exceptions.WrongAmountOfElementsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.lang.System;
import java.util.Scanner;


public class App {

    private static final Console console = new Console();
    public static final Logger logger = LogManager.getLogger("ClientLogger");

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
        if (!initializeConnectionAddress(args)) return;

        var client = new TCPClient(host, port);
        var runner = new Runner(console, client);
        runner.run();
    }
}
