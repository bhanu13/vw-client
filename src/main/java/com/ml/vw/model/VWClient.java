package com.ml.vw.model;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.*;
import java.net.Socket;


@Slf4j
@Configuration
public class VWClient {

    private Socket connectionSocket;

    private OutputStream outStream;

    private BufferedReader inStream;

    private int portNumber = 8880;


    // The VW client that pipes in the data to the VW binary over CLI IO
    @SneakyThrows
    public VWClient() {
        connectionSocket = new Socket("localhost", portNumber);
        outStream = connectionSocket.getOutputStream();
        inStream = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        // ToDo: Log connection successful here
    }

    @SneakyThrows
    public double predict(String featureVector) {
        byte [] buffer = featureVector.getBytes();
        outStream.write(buffer);
        String output = inStream.readLine();
        double prediction = Math.signum(Double.parseDouble(output));
        // ToDo: Log predicted value here
        return prediction;
    }

    @PreDestroy
    @SneakyThrows
    public void shutdown() {
        connectionSocket.close();
    }
}
