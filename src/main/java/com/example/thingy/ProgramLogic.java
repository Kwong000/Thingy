package com.example.thingy;


import com.example.CommunicationData;
import javafx.application.Platform;

import java.util.ArrayList;

public class ProgramLogic implements Runnable {
    DataStructure inData;
    boolean serverMode;
    ClientServerController theController;

    ArrayList<ClientConnection> manyClients = new ArrayList<ClientConnection>();

    public ProgramLogic(DataStructure inData, ClientServerController theController, boolean serverMode)  {
        this.inData = inData;
        this.theController = theController;
        this.serverMode = serverMode;
    }

    public void addSocket(ClientConnection newClient) {
        manyClients.add(newClient);
    }

    public void run() {
        CommunicationData inMessage1 = inData.get();
        while (true) {
            if (inMessage1 != null) {
                if (theController != null) {
                    // add the message to your JavaFX Control that displays many messages
                    CommunicationData finalInMessage = inMessage1;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (serverMode) {
                                if (finalInMessage.getTo().equalsIgnoreCase("ALL") ||
                                        finalInMessage.getTo().equalsIgnoreCase("SERVER")) {
                                    theController.allMessages.getItems().add(finalInMessage);
                                } else {
                                    CommunicationData privateMessage = new CommunicationData(finalInMessage.getFrom(),
                                            finalInMessage.getTo(), "PRIVATE",0);
                                    privateMessage.setFromIPAddress(finalInMessage.getFromIPAddress());
                                    theController.allMessages.getItems().add(privateMessage);
                                }
                            } else {
                                theController.allMessages.getItems().add(finalInMessage);
                            }
                        }
                    });
                } else {
                    System.out.println("ProgramLogicDoer got: " + inMessage1);
                }
                if (serverMode) {
                    try {
                        if (inMessage1.getMessage().equalsIgnoreCase("ID") &&
                                inMessage1.getTo().equalsIgnoreCase("SERVER")) {
                            String clientName = inMessage1.getFrom();
                            for (ClientConnection aClient : manyClients) {
                                if (inMessage1.getFromIPAddress().equalsIgnoreCase(aClient.getActualSocket().getInetAddress().getHostAddress())) {
                                    System.out.println("ProgramLogicDoer registered: " + clientName + " to IP" + aClient);
                                    aClient.setName(clientName);
                                    CommunicationData newClientMessage = new CommunicationData(clientName, "ALL", "NEW CLIENT", 0);
                                    aClient.getObjOut().writeObject(newClientMessage);
                                }
                            }
                        }
                        for (ClientConnection aClient : manyClients) {
                            if (inMessage1.getTo().equalsIgnoreCase("ALL")) {
                                System.out.println("ProgramLogicDoer ALL to: " + aClient.getName() + " message: " + inMessage1);
                                aClient.getObjOut().writeObject(inMessage1);
                            } else if (inMessage1.getTo().equalsIgnoreCase(aClient.getName())) {
                                System.out.println("ProgramLogicDoer private from" + inMessage1.getFrom() + "to: " + aClient.getName());
                                aClient.getObjOut().writeObject(inMessage1);
                            }
                        }

                    } catch (Exception ex) {
                        System.out.println("ProgramLogicDoer server: " + ex);
                    }
                }
            }
            inMessage1 = inData.get();
        }
    }
}