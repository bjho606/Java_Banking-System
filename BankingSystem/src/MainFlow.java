import java.util.Scanner;

public class MainFlow {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void login() {
		System.out.println("< 로그인 >");
		// 로그인 조건
		System.out.println("아이디를 입력해주세요.");
		System.out.println("입력 > ");
		String loginId = scan.nextLine();
		System.out.println("비밀번호를 입력해주세요.");
		System.out.println("입력 > ");
		String loginPw = scan.nextLine();
		
		// 로그인 성공 
		System.out.println("로그인에 성공하였습니다.");
		
		
	}

	public static void main(String[] args) {

		while(true) {
			System.out.println("[원하시는 메뉴를 선택하세요]");
			System.out.println("0. 종료");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("입력 > ");
			
			int accountMenu = scan.nextInt();
			scan.nextLine();
			if(accountMenu == 0) {
				// 종료
				break;
			} else if(accountMenu == 1) {
				// 회원가입
				;
			} else if(accountMenu == 2) {
				// 로그인
				login();
			} else {
				System.out.println("0 이상 2 이하의 숫자로 입력해주세요.");
			}
		}
	}

}
