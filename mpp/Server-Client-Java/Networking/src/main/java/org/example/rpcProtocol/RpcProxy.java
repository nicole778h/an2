package org.example.rpcProtocol;

import org.example.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RpcProxy implements IService {
    private final String host;
    private final int port;
    private Socket connection;
    private IObserver client;

    private static final int TIMEOUT_IN_MILLISECONDS = 500;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    private volatile boolean connected; // Adaugă o variabilă care să indice starea conexiunii

    public RpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
        initializeConnection();

    }

    @Override
    public List<Bilet> findAllBilete() {
        Request request = new Request.Builder().type(RequestType.FIND_BILETE).build();
        sendRequest(request);
        Response response = readResponse();
        return (List<Bilet>) response.data();
    }

    @Override
    public void saveBilet(Bilet bilet) {
        Request request = new Request.Builder().type(RequestType.SAVE_BILET).data(bilet).build();
        sendRequest(request);
    }

    @Override
    public void deleteBilet(Bilet bilet) {
        Request request = new Request.Builder().type(RequestType.DELETE_BILET).data(bilet).build();
        sendRequest(request);
    }

    @Override
    public void updateBilet(Bilet bilet) {
        Request request = new Request.Builder().type(RequestType.UPDATE_BILET).data(bilet).build();
        sendRequest(request);
    }

    @Override
    public void buyBilet(Bilet bilet) {
        Request request = new Request.Builder().type(RequestType.BUY_BILETE).data(bilet).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
            throw new RuntimeException("Eroare la achiziționarea biletului: " + err);
        }
    }

   /* @Override
    public List<Spectacol> findAllSpectacole() {
        return null;
    }*/

    public Utilizator findUtilizatorByUsername(String username) {
        initializeConnection();
        List<String> list = List.of(username);
        Request request = new Request.Builder().type(RequestType.FIND_UTILIZATOR_BY_USERNAME).data(list).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }

        return (Utilizator) response.data();
    }



    @Override
    public List<Spectacol> findSpectacoleByDate(LocalDate date) {
        initializeConnection();
        List<String> dateList = List.of(date.toString());
        Request request = new Request.Builder().type(RequestType.FIND_SPECTACOLE_BY_DATE).data(dateList).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
            return null; // sau aruncă o excepție, în funcție de logica aplicației tale
        }

        return (List<Spectacol>) response.data();
    }


    @Override
    public List<Spectacol> findAllSpectacole() {
        initializeConnection();
        Request request = new Request.Builder().type(RequestType.FIND_SPECTACOLE).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
            return null; // sau aruncă o excepție, în funcție de logica aplicației tale
        }

        return (List<Spectacol>) response.data();
    }

    @Override
    public void saveSpectacol(Spectacol spectacol) {
    }

    @Override
    public void deleteSpectacol(Spectacol spectacol) {
    }

    @Override
    public void updateSpectacol(Spectacol spectacol) {
    }

    @Override
    public List<Utilizator> findAllUtilizatori() {
        return null;
    }

    @Override
    public void saveUtilizator(Utilizator utilizator) {
    }

    @Override
    public void deleteUtilizator(Utilizator utilizator) {
    }

    @Override
    public void updateUtilizator(Utilizator utilizator) {
    }


    public void initializeConnection() {
        try {
            connection = new Socket(host,port);
            System.out.println(connection);
            System.out.println("Local Address: " + connection.getLocalAddress());
            System.out.println("Local Port: " + connection.getLocalPort());
            System.out.println("Remote Address: " + connection.getInetAddress());
            System.out.println("Remote Port: " + connection.getPort());

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            System.out.println("Am initializat conexiunea PROXY");
            input = new ObjectInputStream(connection.getInputStream());
            System.out.println("Am initializat inputul PROXY");
            finished = false;

            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Response readResponse() {
        Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.type().equals(ResponseType.UPDATE_OBSERVER)) {
            System.out.println("Incerc sa modific observerele din PROXY");
            client.init_model();
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.UPDATE_OBSERVER;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object responseObj = input.readObject();
                    System.out.println("response received " + responseObj);

                    // Verificăm dacă obiectul primit este de tipul Response
                    if (responseObj instanceof Response) {
                        Response response = (Response) responseObj;

                        if (isUpdate(response)) {
                            handleUpdate(response);
                        } else {
                            try {
                                qresponses.put(response);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        System.out.println("Received object is not of type Response");
                        // Tratează cazul în care obiectul primit nu este de tipul Response
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

}

