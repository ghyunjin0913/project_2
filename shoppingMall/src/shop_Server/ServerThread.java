package shop_Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import shop_Server.loginMenu;
import shop_Server.productMenu;

public class ServerThread implements Runnable
{
	private Socket socket;
	private ServerMain server2;
	public Scanner sc;

	public ServerThread(Socket socket, ServerMain server2)
	{
		this.socket = socket;
		this.server2 = server2;
	}
	
	@Override
	public void run() 
	{
		int iClientNum = server2.getClientNumber();
		
		try {
			System.out.println("클라이언트: " + iClientNum + "번 접속됨.");
			
			// I/O 버퍼
			BufferedReader in_socket = 
					new BufferedReader(new InputStreamReader(
							socket.getInputStream(),
							StandardCharsets.UTF_8));
			PrintWriter out_socket = 
					new PrintWriter(new OutputStreamWriter(
							socket.getOutputStream(),
							StandardCharsets.UTF_8), true);
			while(true) {
				String ClientName = in_socket.readLine();  // 클라이언트 이름
				System.out.println(ClientName);
				if (ClientName != null) {		
					int outNum = in_socket.read();
					char cout = (char) outNum;	
					if(cout == 2) {
						String searchOut = in_socket.readLine();
						System.out.println(searchOut);
					} 					
					else if (cout == 9) {   // 로그아웃
						break;
					}
				} else if (ClientName.equals("블랙리스트")) {
					System.out.println("접속 차단");
					break;
				} break;
			}			
			socket.close();
			System.out.println("서버: " + iClientNum + "번 소켓 종료");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
