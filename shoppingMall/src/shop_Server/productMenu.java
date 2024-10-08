package shop_Server;

import java.io.PrintWriter;
import java.util.Scanner;

public class productMenu {
	public static Scanner sc = new Scanner(System.in);
	public static productController pc = new productController();
	public static String searchWord;
	
	public static int proMenu(PrintWriter out) {
		System.out.println("안녕하세요.쇼핑몰입니다.^^");
		while(true) {
			System.out.println("===== 메인 메뉴 =====");
			System.out.println("1. 상품리스트 ");
			System.out.println("2. 상품검색하기 ");
			System.out.println("3. 상품문의하기 ");
			System.out.println("9. 로그아웃");
			System.out.print("메뉴 번호 : ");
			
			int sInputKeyVal1 = sc.nextInt();
			switch (sInputKeyVal1) {
			case 1:   // 상품 리스트
				printList();
				continue;
			case 2:
				search();  // 상품 검색
				out.print(sInputKeyVal1);
				continue;
			case 3:
				System.out.println("'quit'입력시 대화가 종료됩니다. ");
				while(true) {
					System.out.print("전송하기>>> ");
					String outputMessage = sc.nextLine();
					//out.println(outputMessage);
					//out.flush();
					if ("quit".equalsIgnoreCase(outputMessage)) break;
									
					//String inputMessage = in.readLine();
					//System.out.println("From Server: " + inputMessage);
					//if ("quit".equalsIgnoreCase(inputMessage)) break;
				}
			case 9:  // 로그아웃
				System.out.println("프로그램 종료합니다.");
				break;
			default:
				System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
				continue;
			}
			return sInputKeyVal1;
		}
	}
	
	public static void printList() {
		for (product_VO row : pc.productList()) {	
			row.setPrice(pc.dis_pro(row.getPrice()));
			System.out.println(row);
		}
	}
	
	public static product_VO search() {
		System.out.print("상품명을 입력해주세요: ");
		String serchWord = sc.next();
		searchWord = serchWord + "검색";
		
		System.out.println(pc.productSearch(serchWord));
		
		return pc.productSearch(serchWord);
	}
	
}
