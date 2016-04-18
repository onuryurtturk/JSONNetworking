package onur.com.jsonlibrary;

/**
 * Created by Onur on 16/04/16.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class JSONClient {

    static private final String SIGNOFF_TOKEN = "###";
    private Socket clientSocket = null;
    private PrintWriter out = null;
    private int timeout;
    private Scanner socketScanner = null;


    private static final byte[] VERSION1 = new byte[]{0x17, 0x78};

    public static JSONPacket getResponse(String host, int port, JSONPacket request) throws IOException {
        return null;
    }


    /**
     * Connecting to server
     */

    public JSONClient(String hostname, int port) {

        try {

            clientSocket = new Socket(hostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            socketScanner = new Scanner(clientSocket.getInputStream());


        } catch (UnknownHostException e) {
            System.err.println("Host not found" + hostname);
            return;
        } catch (IOException e) {
            System.err.println("Connection error to" + hostname);
            return;
        }
    }

    /**
     * Constructor
     */
    public JSONClient(Socket baseClient) {
        this.clientSocket = baseClient;
    }

    /**
     * Check Client is Connected
     */
    public boolean isConnected() {
        if (clientSocket.isBound())
            return true;
        else
            return false;
    }

    /**
     * Connect to Server
     */
    public void connect(SocketAddress endpoint) throws IOException {
        clientSocket.connect(endpoint, timeout);
    }

    /**
     * Send message to server
     */

    public void sendJsonPacket(JSONPacket packet) throws IOException {
        String message = String.valueOf(packet.getPayload());
        Log.e("Sending Message", "Send Message" + message);
        out.println(message);
    }

    /**
     * Close connection with server
     */

    public boolean close() {

        try {

            JSONObject object = new JSONObject();
            object.put("message", SIGNOFF_TOKEN);
            sendJsonPacket(new JSONPacket(object));
            clientSocket.close();
            return true;

        } catch (java.io.IOException eio) {
            System.err.println(eio);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;

        }

    }


    /**
     * Read a line from socket
     */
    public String serverResponse() {
        return socketScanner.nextLine();
    }


    public void setSoTimeout(int timeout) throws SocketException {

        this.timeout = timeout;
    }

    private static int byteArrayToInt(byte[] buffer, int offset) {
        return (buffer[offset] & 0xFF) << 24
                | (buffer[offset + 1] & 0xFF) << 16
                | (buffer[offset + 2] & 0xFF) << 8 | buffer[offset + 3] & 0xFF;
    }

    private static byte[] intToByteArray(int buffer) {
        return new byte[]{(byte) ((buffer >> 24) & 0xFF),
                (byte) ((buffer >> 16) & 0xFF), (byte) ((buffer >> 8) & 0xFF),
                (byte) (buffer & 0xFF)};
    }

}