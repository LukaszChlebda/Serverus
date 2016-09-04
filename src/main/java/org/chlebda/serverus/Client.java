package org.chlebda.serverus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by lukasz on 14.08.16.
 */
public class Client {

    private Thread therad;
    private int port;
    private byte[] num;
    public void start(int port, String threadName) {
        this.port = port;
        this.num = num;
        new Thread(() -> startClient(), threadName).start();
    }

    private void startClient() {
        byte[] loginNum = num;
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true)
        try {
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = Thread.currentThread().getName() + " " + inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
            clientSocket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
