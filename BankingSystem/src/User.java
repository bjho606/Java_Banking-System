import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class User {
	static String name, id;
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
					AccountCreator.run(name, id);

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
	
	
	
	public static void printHistory(String userFile, String account) {
		try {
			Scanner fileSc = new Scanner(new File(userFile+"/"+account));
			Scanner sc = new Scanner(System.in);
			String ch = "";
			ch = fileSc.nextLine();
			while( fileSc.hasNextLine() )
			{
			    ch = fileSc.nextLine();
			    System.out.println(ch);
			}
			while(true) {
				System.out.println("나가려면 0을 입력하세요.");
				System.out.print("입력>");
				String tmp = sc.nextLine();
				if(tmp.matches("^[0]$"))
					break;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
}
