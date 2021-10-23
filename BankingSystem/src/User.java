import java.util.Scanner;

public class User {
	String name, id;
	static Scanner scan = new Scanner(System.in);
	
	public User(String name, String id) {
		this.name = name;
		this.id = id;
		
		mainMenu();
	}
	public static void mainMenu() {
		while(true) {
			System.out.println("[원하시는 메뉴를 선택하세요]");
			System.out.println("0. 로그아웃");
			System.out.println("1. 계좌 생성");
			System.out.println("2. 계좌 선택");
			System.out.print("입력 > ");
			
			String mainMenu = scan.nextLine();
			int mainMenuNum;
			
			if(mainMenu.matches(MainFlow.regExpMain)) {
				mainMenuNum = Integer.parseInt(mainMenu);
				if(mainMenuNum == 0) {
					// 로그아웃
					System.out.println("\n로그아웃 합니다.\n");
					break;
				} else if(mainMenuNum == 1) {
					// 계좌 생성
					

/* ------------------- 여기부터 하시면 됩니다 -----------------------------------------*/

				} else if(mainMenuNum == 2) {
					// 계좌 선택
					

/* ------------------- 여기부터 하시면 됩니다 -----------------------------------------*/

				} 
				System.out.println();
				
			} else {
				System.out.println("\n0 이상 2 이하의 숫자로 입력해주세요.\n");
			}
		}
	}
	public void printCurrentUser() {
		System.out.println(name + " " + id);
	}
	
}