package onur.com.jsonlibrary;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ServerTest {

    private static final String TEST_HOSTNAME = "localhost";
    private static final int TEST_PORT = 8192;
    JSONServer server;


    @Before
    public void initialize() {
        server = new JSONServer();
    }

    @Test
    public void testClientConnection() {
        Socket testSocket = null;

        try {
            testSocket = new Socket(TEST_HOSTNAME, TEST_PORT);
            assertTrue(server.clientConnect());
        } catch (Exception e) {
            assertTrue(false);
        }

        server.closeServer();
        try {
            testSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testClientDisconnect() {
        Socket testSocket = null;
        try {
            testSocket = new Socket(TEST_HOSTNAME, TEST_PORT);
            server.clientConnect();
            server.clientDisconnect();
            Scanner testScanner = new Scanner(testSocket.getInputStream());
            testScanner.nextLine();
            assertTrue(false);
        } catch (Exception e) {
        }

        try {
            testSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testServerDisconnect() {
        Socket testSocket = null;
        try {
            testSocket = new Socket(TEST_HOSTNAME, TEST_PORT);
            server.clientConnect();
            server.closeServer();
            Scanner testScanner = new Scanner(testSocket.getInputStream());
            testScanner.nextLine();
            assertTrue(false);
        } catch (Exception e) {
        }

        try {
            testSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testClientMessage() {
        Socket testSocket = null;
        String testMessage = "This is test message";

        try {

            testSocket = new Socket(TEST_HOSTNAME, TEST_PORT);
            server.clientConnect();

            PrintWriter testWriter = new PrintWriter(testSocket.getOutputStream(), true);
            testWriter.println(testMessage);

            String resultString = server.getMessage();

            assertEquals(testMessage, resultString);
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            testSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}