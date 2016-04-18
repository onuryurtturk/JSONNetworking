package onur.com.jsonlibrary;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Onur on 16/04/16.
 */
public class JSONServer {


    private int connectedSocketLimit = 10000;
    private RequestCallback onRequest;
    private ErrorCallback onError;
    private boolean connected;
    public final ArrayList<JSONClient> clients = new ArrayList<JSONClient>();
    static private final int DEFAULT_PORT = 8192;
    static private final String SIGN_OFF_TOKEN = "###";
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    Scanner socketScanner = null;
    PrintWriter outputToClient = null;
    OutputStream outputStream = null;


    /**
     * Set port constructor
     */
    public JSONServer() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            serverSocket.setSoTimeout(100);
            if (serverSocket.isBound())
                connected = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Client Connecting to server
     */
    public boolean clientConnect() {
        try {

            clientSocket = serverSocket.accept();
            socketScanner = new Scanner(clientSocket.getInputStream());
            outputStream = clientSocket.getOutputStream();
            outputToClient = new PrintWriter(outputStream, true);
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Client Disconnecting from server
     */
    public boolean clientDisconnect() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Disconnect server
     */

    public boolean closeServer() {
        try {
            serverSocket.close();
            if (clientSocket != null) {
                clientDisconnect();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * get message sent by client
     */

    public String getMessage() {
        String message = socketScanner.nextLine();
        message = message.trim();

        if (message.toUpperCase().startsWith(SIGN_OFF_TOKEN)) {
            clientDisconnect();
        }

        try {
            message = (new JSONObject(message)).getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        message = "Hi Im Server your message is : " + message;

        return message;
    }

    public void setOnRequest(RequestCallback handler) {
        onRequest = handler;
    }

    public void setOnError(ErrorCallback handler) {
        onError = handler;
    }

    public boolean isConnected() {
        return connected;
    }


    private void handleClient(JSONClient client) {
        clients.add(client);
    }


    /**
     * Interfaces
     **/

    public interface RequestCallback {
        public JSONPacket onRequest(JSONPacket request);
    }

    public interface ErrorCallback {
        public void onError(Exception e);
    }
}
