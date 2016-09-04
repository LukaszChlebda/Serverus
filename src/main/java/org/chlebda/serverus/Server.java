package org.chlebda.serverus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lukasz on 14.08.16.
 */
public class Server {

    private int port;
    private boolean listening = false;
    private DatagramSocket socket;
    private InetAddress address;
    private Thread listenThread;
    private AtomicInteger threadCounter;

    private byte[] sendData = new byte[1024];
    private byte[] dataRecived = new byte[1024];

    public Server(int port) {
        this.port = port;
        threadCounter = new AtomicInteger(0);
    }


    public void start() {
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server Started");
        } catch (SocketException e) {
            System.out.println("Port not available");
            return;
        }
        listening = true;
        threadCounter.getAndIncrement();

        listenThread = new Thread(() -> listen(), threadCounter.toString());
        listenThread.start();
    }

    private void listen() {
        while (listening) {
            DatagramPacket packetRecived = new DatagramPacket(dataRecived, dataRecived.length);
            try {
                socket.receive(packetRecived);
                String sentence = new String( packetRecived.getData());
                System.out.println(Thread.currentThread().getName() + " RECEIVED: " + sentence);
                InetAddress IPAddress = packetRecived.getAddress();
                int port = packetRecived.getPort();
                String capitalizedSentence = Thread.currentThread().getName() + " " + sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(DatagramPacket data) {

    }

    public void send(byte[] data, InetAddress address, int port) {

    }

    private void login(){};
    String clientSentence;
    String capitalizedSentence;
    ServerSocket welcomeSocket = null;

    public void startTCPServer() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        try {
            welcomeSocket = new ServerSocket(6789);
            atomicInteger.incrementAndGet();
            new Thread(() -> ThreadStart(), atomicInteger.toString()).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ThreadStart() {
        while(true)
        {
            System.out.println(Thread.currentThread().getName().toString());
            try {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            }catch (IOException e) {

            }
        }
    }
}
