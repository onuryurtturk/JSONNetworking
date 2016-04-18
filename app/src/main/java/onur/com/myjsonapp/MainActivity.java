package onur.com.myjsonapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import onur.com.jsonlibrary.JSONClient;
import onur.com.jsonlibrary.JSONPacket;
import onur.com.jsonlibrary.JSONServer;

public class MainActivity extends AppCompatActivity {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8192;

    TextView txt_results;
    EditText edt_user_input;
    Button btn_send;
    Thread myThread;
    JSONPacket packet;
    JSONServer echo_server;
    JSONClient echo_client;
    SocketServerMessageThread socketServerReplyThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = (Button) findViewById(R.id.btn_send);
        txt_results = (TextView) findViewById(R.id.txt_results);
        edt_user_input = (EditText) findViewById(R.id.edt_input);

        echo_server = new JSONServer();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(edt_user_input.getText())) {


                    myThread = new Thread(new SocketServerThread());
                    myThread.start();
                } else {

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.text_empty), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private class SocketServerThread extends Thread {

        @Override
        public void run() {
            try {

                echo_client = new JSONClient(HOSTNAME, PORT);
                echo_server.clientConnect();
                JSONObject object = new JSONObject();
                object.put("message", String.valueOf(edt_user_input.getText()));
                packet = new JSONPacket(object);
                echo_client.sendJsonPacket(packet);

                socketServerReplyThread = new SocketServerMessageThread();
                socketServerReplyThread.run();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    private class SocketServerMessageThread extends Thread {

        @Override
        public void run() {
            try {

                final String m = echo_server.getMessage();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        txt_results.setText(String.valueOf(txt_results.getText()) + "\n" + m);
                    }
                });

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
