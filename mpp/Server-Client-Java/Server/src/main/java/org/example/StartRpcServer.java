package org.example;


import org.example.utils.AbstractServer;
import org.example.utils.RpcConcurentServer;
import org.example.utils.ServerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class StartRpcServer {
    private static final int defaultPort = 55555;
    public static void main(String[] args) {
        System.out.println("Server starting ...");
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/swimming.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find spectacoleserver.properties "+e);
            return;
        }
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(serverProps);
        SpectacolRepository spectacolRepository = new SpectacolRepository(serverProps);
        BiletRepository biletRepository = new BiletRepository(serverProps);
        //IService spectacoleServerImpl = (IService) new ServiceImpl(biletRepository, spectacolRepository, utilizatorRepository);
        ServiceImpl spectacoleServerImpl = new ServiceImpl(biletRepository, spectacolRepository, utilizatorRepository);

        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("spectacole.server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong Port Number: " + nef.getMessage());
            System.err.println("Using default port: " + defaultPort);
        }


        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new RpcConcurentServer(chatServerPort, spectacoleServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch(ServerException e){
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
