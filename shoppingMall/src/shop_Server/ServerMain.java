package shop_Server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerMain {
    private ServerSocket server_socket;
    private int clientNumber = 0;

    public ServerMain() throws IOException {
        server_socket = new ServerSocket(45012);
        System.out.println("45012포트 오픈됨");

        // 무한 while 루프: 새로운 연결 요청 대기
        while (true) {
            Socket socket = server_socket.accept();
            
            if (socket != null) {            	
                ServerThread server_thread = new ServerThread(socket, this);
                Thread thread = new Thread(server_thread);
                thread.start();
            }
        }
//		server_socket.close();
//		System.out.println("통신서버를 종료합니다.");
    }
    
	public int getClientNumber() {
		return ++clientNumber;
	}	

    public static void main(String[] args) {
        try {
            new ServerMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}