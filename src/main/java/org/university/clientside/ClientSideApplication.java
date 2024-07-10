package org.university.clientside;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
            System.out.println("Connection accepted by the Server..");
            SocketChannel client = SocketChannel.open(
                    new InetSocketAddress("localhost", 808));

            File file = new File("C:\\Users\\User\\Downloads\\QWERTY.pptx");
            FileInputStream fis = new FileInputStream(file);

            byte[] payload = new byte[(int) file.length()];
            fis.read(payload);
            fis.close();

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(new FileData(file.getName(), file.length(), payload));
            System.out.println(json);
            ByteBuffer buf = ByteBuffer.wrap(json.getBytes());

//            JSONObject json = new JSONObject();
//            json.put("fileName", "TEST.txt");
//            json.put("length", file.length());
//            json.put("payload", new String(payload));
//            System.out.println(payload);
//            String jsonString =  json.toString();


//            ByteBuffer buffer = ByteBuffer.allocate(1000);
//            buffer.put(jsonString.getBytes());
//            buffer.flip();
            client.write(buf);
            //System.out.println(String.format("Sending JSON: %s\nbufforBytes: %d", jsonString, bytesWritten));
            client.close();
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class FileData {
        private final String name;
        private final long length;
        private final byte[] data;

        public FileData(String name, long length, byte[] data) {
            this.name = name;
            this.length = length;
            this.data = data;
        }
    }
}