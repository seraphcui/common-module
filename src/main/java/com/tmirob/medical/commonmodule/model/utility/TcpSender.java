/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-9-18 下午3:35.
 */

package com.tmirob.medical.commonmodule.model.utility;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class TcpSender {

    private String ip;
    private String port;
    private Socket clientSocket;
    // Big end and small end convert.
    private boolean reverse;

    public TcpSender(String ip, String port, boolean reverse){
        this.ip = ip;
        this.port = port;
        this.reverse = reverse;
    }

    public TcpSender(String ip, String port){
        this(ip, port, false);
    }

    public String getIp() {return ip;}
    public void setIp(String ip) {this.ip = ip;}
    public String getPort() {return port;}
    public void setPort(String port) {this.port = port;}

    public ErrorCode send(String content){
        return send(content, true);
    }

    /**
     * Send data and receive response.
     * @param content send data
     * @param close True to close the connection after send data, and new a connection at first.
     * @return ErrorCode response
     */
    public synchronized ErrorCode send(String content, boolean close){
        try {
            // GB2312 is the way currently found can communicate with LabVIEW (Windows) in Chinese.
            byte[] contentBytes = content.getBytes(Charset.forName("GB2312"));
            int length = contentBytes.length;
            ErrorCode errorCode = ErrorCode.Success;
            if(close){
                errorCode = setupConnection();
            }else{
                if(clientSocket == null || !clientSocket.isConnected()){
                    errorCode = setupConnection();
                }
            }
            if(!errorCode.equals(ErrorCode.Success)){
                return errorCode;
            }

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
            if(reverse){
                length = Integer.reverseBytes(length);
            }
            outToServer.writeInt(length);
            outToServer.write(contentBytes);
            outToServer.flush();

            byte[] response = new byte[4];
            inFromServer.readFully(response);
            ByteBuffer wrapped = ByteBuffer.wrap(response);

            if(close){
                clientSocket.close();
            }
            if(wrapped.getInt() == ErrorCode.Success.ordinal()){
                return ErrorCode.Success;
            }
            return ErrorCode.TcpFailException;
        }catch (SocketTimeoutException e) {
            return ErrorCode.TcpResponseTimeoutException;
        }catch (EOFException e){
            // This could be thrown from the readFully function as long as the server side closes its socket.
            // Not sure why server side closes its socket, but the client side should reconnect if catching this exception.
            if(!close){
                setupConnection();
            }
            return ErrorCode.TcpResponseEOFException;
        }
        catch (IOException e){
            if(!close){
                return setupConnection();
            }
            return ErrorCode.TcpFailException;
        }
    }

    public ErrorCode close() {
        if(this.clientSocket != null){
            try{
                clientSocket.close();
                clientSocket = null;
                return ErrorCode.Success;
            }catch (IOException e){
                clientSocket = null;
                return ErrorCode.TcpCloseFailException;
            }
        }
        return ErrorCode.NoTcpSetupException;
    }

    private ErrorCode setupConnection(){
        try {
            close();
            clientSocket = new Socket();
            // Set timeout for the read operation.
            clientSocket.setSoTimeout(5000);
            InetSocketAddress address = new InetSocketAddress(ip, Integer.parseInt(port));
            clientSocket.connect(address, 5000);
            return ErrorCode.Success;
        }catch (IOException e){
            return ErrorCode.NoTcpSetupException;
        }catch (NumberFormatException e){
            return ErrorCode.IpOrPortNotValidException;
        }
    }
}
