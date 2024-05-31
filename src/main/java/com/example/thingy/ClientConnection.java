package com.example.thingy;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnection {
    public String name;
    Socket actualSocket;
    public String clientIP;
    ObjectOutputStream objOut;
    ObjectInputStream objIn;

    public ClientConnection(Socket actualSocket) throws Exception {
        this.actualSocket = actualSocket;
        this.clientIP = actualSocket.getInetAddress().getHostAddress();
        OutputStream out = actualSocket.getOutputStream();
        this.objOut = new ObjectOutputStream(out);
        InputStream in = actualSocket.getInputStream();
        this.objIn = new ObjectInputStream(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectOutputStream getObjOut() {
        return objOut;
    }

    public void setObjOut(ObjectOutputStream objOut) {
        this.objOut = objOut;
    }

    public Socket getActualSocket() {
        return actualSocket;
    }

    public void setActualSocket(Socket actualSocket) {
        this.actualSocket = actualSocket;
    }

    public ObjectInputStream getObjIn() {
        return objIn;
    }

    public void setObjIn(ObjectInputStream objIn) {
        this.objIn = objIn;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String toString() {
        return "Client Connection IP " + actualSocket.getInetAddress().getHostAddress() +
                " named: " + name;
    }
}