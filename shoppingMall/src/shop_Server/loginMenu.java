package shop_Server;

import java.io.PrintWriter;
import java.util.Scanner;

public class loginMenu {
	public static Scanner sc = new Scanner(System.in);
	public static joinController jc = new joinController();
	public static member_VO member = new member_VO();
	
	
	public static String mainMenu(PrintWriter out) {	
		System.out.println("안녕하세요.쇼핑몰입니다.^^");
		while(true) {
			System.out.println("===== login =====");
			System.out.println("1. 로그인\n2. 회원가입");
			System.out.print("선택 : ");
			String login = sc.nextLine();
			
			String result;
						
			if(login.equals("1")) {
				int loggedIn = login();
				if(loggedIn == 1 ) {
					System.out.println("로그인 성공");
					result = member_VO.getMname() + "님 접속";
				} else if(loggedIn == 2) {
					result = "blacklist";
				} else {
					System.out.println("로그인 실패");
					continue;
				}				
			} else if(login.equals("2")) {
				join();
				result = member_VO.getMname() + "님 가입";
				out.println(result);
				continue;
			} else {
				System.out.println("다시 입력해주세요.");
				continue;
			}			
			return result;
		}
	}
	
	public static int login()  {
		System.out.print("id : ");
		String inputId = sc.nextLine();
		System.out.print("pw : ");
		String inputPw = sc.nextLine();
		
		int result = jc.login(inputId, inputPw);
		return result;
	}
	
	public static void join() {		
		System.out.print("id : ");
		String Id = sc.nextLine();
		jc.checkId(Id);
		System.out.print("pw : ");
		String Pw = sc.nextLine();
		System.out.print("이름 : ");
		String Name = sc.nextLine();
		
		member.setId(Id);
		member.setPw(Pw);
		member.setMname(Name);
		jc.join(member);

	}
}
