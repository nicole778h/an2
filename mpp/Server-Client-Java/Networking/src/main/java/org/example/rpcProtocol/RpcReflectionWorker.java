package org.example.rpcProtocol;



import org.example.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RpcReflectionWorker implements Runnable, IObserver {
    private final IService server;
    private final Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public RpcReflectionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + request.type().name();
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) {
        synchronized (output) {
            try {
                output.writeObject(response);
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Response handleFIND_UTILIZATOR(Request request) {
        List<String> list = (List<String>) request.data();
        Utilizator utilizator = server.findUtilizatorByUsername(list.get(0));
        if (utilizator != null) {
            return new Response.Builder().type(ResponseType.OK).data(utilizator).build();
        }
        return new Response.Builder().type(ResponseType.ERROR).build();
    }
   /* private Response handleFIND_UTILIZATOR_BY_USERNAME(Request request) {
        String username = (String) request.data(); // Obții direct username-ul din obiectul de cerere
        Utilizator utilizator = server.findUtilizatorByUsername(username);
        if (utilizator != null) {
            return new Response.Builder().type(ResponseType.OK).data(utilizator).build();
        } else {
            return new Response.Builder().type(ResponseType.ERROR).build();
        }
    }*/
   private Response handleFIND_UTILIZATOR_BY_USERNAME(Request request) {
       List<String> list = (List<String>) request.data();
       Utilizator utilizator = server.findUtilizatorByUsername(list.get(0));
       if (utilizator!=null) {
           return new Response.Builder().type(ResponseType.OK).data(utilizator).build();
       }
       return new Response.Builder().type(ResponseType.ERROR).build();
   }





    private Response handleFIND_SPECTACOLE(Request request) {
        List<Spectacol> spectacole = server.findAllSpectacole();
        if (spectacole != null) { // Verifică dacă lista de spectacole nu este null
            return new Response.Builder().type(ResponseType.OK).data(spectacole).build();
        } else {
            return new Response.Builder().type(ResponseType.ERROR).build(); // Tratează cazul în care nu există spectacole disponibile
        }
    }



    private Response handleFIND_SPECTACOLE_BY_DATE(Request request) {
        List<String> dateList = (List<String>) request.data();
        LocalDate date = LocalDate.parse(dateList.get(0));
        List<Spectacol> spectacole = server.findSpectacoleByDate(date);
        if (spectacole != null) {
            return new Response.Builder().type(ResponseType.OK).data(spectacole).build();
        } else {
            return new Response.Builder().type(ResponseType.ERROR).build();
        }
    }
    private Response handleBUY_BILETE(Request request) {
        Bilet bilet = (Bilet) request.data();

        // Salvarea biletului
        server.saveBilet(bilet);

        // Obține spectacolul asociat biletului
        //Spectacol spectacol = server.findSpectacolById(bilet.getIdSpectacol());

        // Actualizarea numărului de locuri disponibile și vândute pentru spectacol
        // Poate fi necesar să actualizezi și alte informații legate de spectacol în funcție de cerințele tale
        //server.updateSpectacol(spectacol);

        return new Response.Builder().type(ResponseType.OK).build();
    }

    @Override
    public void init_model() {

    }

    // Implementează și restul metodelor de tratare a cererilor aici
}
