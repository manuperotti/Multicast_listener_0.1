package com.example.multicast_listener_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btStart = findViewById(R.id.btSTART);
        final TextView txIP = findViewById(R.id.txIP);
        final TextView txPuerto = findViewById(R.id.txPUERTO);
        final TextView txDatos = findViewById(R.id.txDATOS);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msj = "IP = " + txIP.getText().toString() + "\nPuerto = " + txPuerto.getText().toString();
                txDatos.setText(msj);

                // join a Multicast group and send the group salutations
                new Red().execute(msj);
//                byte[] buf = new byte[1000];                //Buffer de mensajes
//                DatagramPacket recv = new DatagramPacket(buf, buf.length);
//
//                //Toast.makeText(getApplicationContext(), "A ver si quiere...", Toast.LENGTH_SHORT).show();
//
//                String msj = "IP = " + txIP.getText().toString() + "\nPuerto = " + txPuerto.getText().toString();
//                txDatos.setText(msj);
//
//                try
//                {
//                    InetAddress group = InetAddress.getByName(txIP.getText().toString());
//                    MulticastSocket s = new MulticastSocket(Integer.parseInt(txPuerto.getText().toString()));
//                    s.joinGroup(group);
//
//                    while (!recv.toString().equals("fin"))
//                    {
//                        //wait(1000);
//                        Toast.makeText(getApplicationContext(), "Intentando recivir", Toast.LENGTH_SHORT).show();
//                        s.receive(recv);
//                        txDatos.setText(recv.toString());
//                    }
//                    Toast.makeText(getApplicationContext(), "Abortado", Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e){
//                    System.out.println(e.getMessage());
//                }
            }
        });
        // join a Multicast group and send t he group salutations
        ///...
        /*
        InetAddress group = InetAddress.getByName("228.5.6.7");
        MulticastSocket s = new MulticastSocket(6789);
        s.joinGroup(group);
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        DatagramPacket hi = new DatagramPacket(bytes, bytes.length,
                group, 6789);
        s.send(hi);
        // get their responses!
        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        s.receive(recv);
        ///...
        // OK, I'm done talking - leave the group...
        s.leaveGroup(group);

         */

    }

    private class Red extends AsyncTask<String, Void, byte[]> {
        @Override
        protected byte[] doInBackground(String... params) {
            byte[] buf = new byte[1000];

            try {
                String msg = params[0];
                InetAddress group = InetAddress.getByName("228.5.6.7");
                MulticastSocket s = new MulticastSocket(6789);
                s.joinGroup(group);
                byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                DatagramPacket hi = new DatagramPacket(bytes, bytes.length,
                        group, 6789);
                s.send(hi);

                // get their responses!

                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);

                Log.d("Multicast Log", Arrays.toString(buf));

                // OK, I'm done talking - leave the group...
                s.leaveGroup(group);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (SecurityException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            return buf; //hay que ver si esto tiene algo

        }

        @Override
        protected void onPostExecute(byte[] buf) {
            super.onPostExecute(buf);

            //imprimir variables por UI

        }

    }

}
