package onur.com.jsonlibrary;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Onur on 18/04/16.
 */
public class ClientTest {

    private static final String TEST_HOSTNAME = "localhost";
    private static final int TEST_PORT = 8192;
    JSONClient testClient = null;


    @Test
    public void testClientConnection() {
        ServerSocket testServerSocket = null;
        try {
            testServerSocket = new ServerSocket(TEST_PORT);
            testServerSocket.setSoTimeout(100);
            testClient = new JSONClient(TEST_HOSTNAME, TEST_PORT);
            testServerSocket.accept();

            testServerSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            assertTrue(false);
        }

        testClient.close();
    }

    @Test
    public void testClientSendMessages() {
        ServerSocket testServerSocket = null;

        try {
            testServerSocket = new ServerSocket(TEST_PORT);
            testServerSocket.setSoTimeout(100);
            testClient = new JSONClient(TEST_HOSTNAME, TEST_PORT);

            Socket testSocket = testServerSocket.accept();
            Scanner testScanner = new Scanner(testSocket.getInputStream());
            String testMessage = "Test Message";

            JSONObject object = new JSONObject();
            object.put("message", testMessage);

            testClient.sendJsonPacket(new JSONPacket(object));
            String result = testScanner.nextLine();
            assertEquals(testMessage, result);

            testServerSocket.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        testClient.close();
    }

/*    @Test
    public void testClientDisconnect() {
        ServerSocket testServerSocket = null;
        try {
            testServerSocket = new ServerSocket(TEST_PORT);
            testServerSocket.setSoTimeout(100);

            testClient = new JSONClient(TEST_HOSTNAME, TEST_PORT);
            Socket testSocket = testServerSocket.accept();

            Scanner testScanner = new Scanner(testSocket.getInputStream());
            testClient.close();
            String testMessage = "Test Message";

            testClient.sendMessage(testMessage);

            String result = testScanner.next();

            assertEquals("BYE", result);
        } catch (Exception e) {
        }

        try {
            testServerSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testClient.close();
    }*/


}