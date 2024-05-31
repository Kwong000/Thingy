package com.example.thingy;

import com.example.CommunicationData;

import java.net.Socket;

public class Client {
    static DataStructure queue;
    public static void main(String[] args) throws Exception {

        // Controller.initialize()
        System.out.println("Connecting to my server");
        Socket newSocket = new Socket("192.168.5.165",3256);
        queue = new DataStructure();
        ClientConnection newClient = new ClientConnection(newSocket);

        DataReader myDataReader = new DataReader(newClient, queue);
        ProgramLogic myProgramLogic = new ProgramLogic(queue,null,false);
        Thread dataReadThread = new Thread(myDataReader);
        Thread programLogicThread = new Thread(myProgramLogic);
        dataReadThread.start();
        programLogicThread.start();

        // Controller.sendMessage()  onAction
        CommunicationData data1 = new CommunicationData(null,null,"HIIII", 0);
        newClient.getObjOut().writeObject(data1);
        System.out.println("Client wrote: " + data1);
    }
}