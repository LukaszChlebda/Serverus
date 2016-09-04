package org.chlebda.serverus;

/**
 * Created by lukasz on 14.08.16.
 */
public class Main {
    public static void main(String[] args) {

        Server server = new Server(4000);
        //server.start();
        server.startTCPServer();

    }
}
