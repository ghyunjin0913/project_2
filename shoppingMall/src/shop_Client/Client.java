package shop_Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import shop_Client.Client;

import shop_Server.loginMenu;
import shop_Server.member_VO;
import shop_Server.productMenu;

public class Client {

	public Client() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 45012);
			System.out.println(
					"클라이언트: Successful Connection to the Server");

			// I/O 스트림
			BufferedReader in_socket = new BufferedReader(
					new InputStreamReader(
						socket.getInputStream(), 
						StandardCharsets.UTF_8));
			PrintWriter out_socket = new PrintWriter(
					new OutputStreamWriter(
						socket.getOutputStream(), 
						StandardCharsets.UTF_8), true);
			
			String login = loginMenu.mainMenu(out_socket);  // 로그인 및 회원가입 정보
        	out_socket.println(login);
        	if(login.contains(member_VO.getMname())) {  // 로그인 성공 시
        		int out = productMenu.proMenu(out_socket);  // 메뉴 번호
        		out_socket.print(out);
        		String search = productMenu.searchWord;
        		out_socket.println(search);
        	}
        	socket.close();
    		System.out.println("클라이언트: 소켓 종료");
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

