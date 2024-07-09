package org.university.clientside;

import lombok.SneakyThrows;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSideApplication {
    @SneakyThrows
    public static void main(String args[]) {
        try {
            File file = new File("C:\\Users\\User\\Downloads\\test.xlsx");
            FileInputStream fis = new FileInputStream(file);

            byte[] payload = new byte[(int) file.length()];
            fis.read(payload);
            fis.close();

            JSONObject json = new JSONObject();
            json.put("fileName", "test.xlsx");
            json.put("length", file.length());
            json.put("payload", new String(payload));
            System.out.println(payload);
            String jsonString =  json.toString();
            System.out.println(
                    "Connection accepted by the Server..");
            SocketChannel client = SocketChannel.open(
                    new InetSocketAddress("localhost", 808));

            ByteBuffer buffer = ByteBuffer.allocate(1000);
            buffer.put(jsonString.getBytes());
            buffer.flip();
            int bytesWritten = client.write(buffer);
            System.out.println(String.format("Sending JSON: %s\nbufforBytes: %d", jsonString, bytesWritten));
            client.close();
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}