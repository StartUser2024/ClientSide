package org.university.clientside;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSideApplication {
    public static void main(String args[]) {
        try {
            File file = new File("C:\\Users\\User\\Downloads\\Win81-WS1.ova");//qtcreator-windows-x64-mingw-13.0.0.7z");
            FileInputStream fis = new FileInputStream(file);
            System.out.println("Connection accepted by the Server..");
            SocketChannel client = SocketChannel.open(
                    new InetSocketAddress("localhost", 808));

            ByteBuffer buffer = ByteBuffer.allocate(1024*8);
            String message = file.getName() + "\r\n" + file.length() + "\r\n";
            int count;
            buffer.put(message.getBytes());
            System.out.println(new String(buffer.array(), 0, message.getBytes().length));
            buffer.flip();
            client.write(buffer);
            buffer.clear();

            while ((count = fis.read(buffer.array())) != -1) {
                buffer.put(buffer.array(), 0, count);
                buffer.flip();
                client.write(buffer);
                buffer.clear();
            }
            client.close();
            fis.close();
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}