package org.example.utils;



import org.example.IService;
import org.example.rpcProtocol.RpcReflectionWorker;

import java.net.Socket;

public class RpcConcurentServer extends AbsConcurrentServer {
    private final IService spectacoleServer;

    public RpcConcurentServer(int port, IService agentieServer) {
        super(port);
        this.spectacoleServer = agentieServer;
        System.out.println("Spectacole- SpectacoleRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        RpcReflectionWorker worker = new RpcReflectionWorker(spectacoleServer, client);
        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}