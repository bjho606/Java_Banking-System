import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainFlow {
	static Scanner scan = new Scanner(System.in);
	static String regExpMain = "^[0-2]$";
	static String regExpKor = "^[가-힣\\s]*$";	// 공백 허용
	static String regExpId = "^(?=.*[A-Za-z])(?=.*[0-9]).{1,}$";
	static String regExpPw = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%]).{1,}$";
	

	public static void main(String[] args) {		
		while(true) {
			System.out.println("[원하시는 메뉴를 선택하세요]");
			System.out.println("0. 종료");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.print("\n입력 > ");
			
			String accountMenu = scan.nextLine();
			int accountMenuNum;
			
			if(accountMenu.matches(regExpMain)) {
				accountMenuNum = Integer.parseInt(accountMenu);
				if(accountMenuNum == 0) {
					// 종료
					break;
				} else if(accountMenuNum == 1) {
					// 회원가입
					signUp();
					;
				} else if(accountMenuNum == 2) {
					// 로그인
					login();
					
/* ---------------------여기부터 다음 작업 추가하면 됩니다.-------------------------- */
					
				} 
				
			} else {
				System.out.println("\n0 이상 2 이하의 숫자로 입력해주세요.\n");
			}
		}
		
	}

	// [회원 가입]
	public static void signUp() {
		String name, id, pw;
		
		System.out.println("< 회원가입 >");
		// 회원 가입 조건
		System.out.println("\n계정에 등록할 이름을 입력해주세요.");
		name = createName();
		name = name.replaceAll(" ", "");
		System.out.println("\n계정에 등록할 아이디를 입력해주세요.");
		id = createId();
		System.out.println("\n계정에 등록할 비밀번를 입력해주세요.");
		pw = createPw();

		// 계정 디렉토리 생성
		String folderName = name + "_" + id;
		String path = "./" + folderName;
		File directory = new File(path);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		// login.txt에 계정 추가
		File file = new File("./login.txt");
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
			if(file.isFile() && file.canWrite()) {
				bufferedWriter.write(name + " " + id + " " + pw);
				bufferedWriter.newLine();
				
				bufferedWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n회원이 등록되었습니다.\n\n");
	}
	
	// [회원가입 - 이름 조건]
	private static String createName() {
		String name = "";
		while(true) {
			System.out.print("입력 > ");
			String tempName = scan.nextLine();
			if(!tempName.matches(regExpKor)) {
				System.out.println("이름은 완전한 한글 문자만 입력 가능합니다.");
				continue;
			} else if(checkBadSpace(tempName)) {
				System.out.println("공백은 성과 이름 사이 한번을 제외하고는 허용되지 않습니다.");
				continue;
			} else if(tempName.length() < 2 || tempName.length() > 5) {
				System.out.println("이름은 2글자 이상, 5글자 이하로 입력해주세요.");
				continue;
			} else {
				name = tempName;
				break;
			}
		}
		
		return name;
	}
	
	// [공백 조건 확인] 
	private static boolean checkBadSpace(String str) {
		if(str.startsWith(" ") || str.endsWith(" ")) {
			return true;
		} else if(str.length() - str.replace(" ", "").length() > 1) {
			return true;
		}
		
		return false;
	}

	// [회원가입 - 아이디 조건]
	private static String createId() {
		String id = "";
		while(true) {
			System.out.print("입력 > ");
			String tempId = scan.nextLine();
			char firstLetter = tempId.charAt(0);
			if(!tempId.matches(regExpId)) {
				System.out.println("아이디는 알파벳과 숫자로만 입력하여 두 종류 모두 사용해야 합니다.");
				continue;
			} else if(firstLetter >= 97 && firstLetter <= 122) {
				System.out.println("아이디의 첫 글자는 알파벳 대문자 혹은 숫자로 입력해야 합니다.");
				continue;
			} else if(tempId.length() < 6 || tempId.length() > 12) {
				System.out.println("아이디는 6글자 이상, 12글자 이하로 입력해주세요.");
				continue;
			} else if(isExistId(tempId)) {
				System.out.println("해당 아이디는 이미 존재합니다.");
				continue;
			} else {
				id = tempId;
				break;
			}
		}
		
		return id;
	}
	
	// [이미 있는 아이디인지 확인]
	private static boolean isExistId(String id) {
		boolean flag = false;
		
		try {
			File file = new File("./login.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				String[] words = line.split(" ");
				if(id.equals(words[1])) {
					flag = true;
					break;
				}
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	// [회원가입 - 비밀번호 조건]
	private static String createPw() {
		String pw = "";
		while(true) {
			System.out.print("입력 > ");
			String tempPw = scan.nextLine();
			if(!tempPw.matches(regExpPw)) {
				// ----------------------------------------------------------------------------------------------
				// 여기 부분 기획서에 특수기호 !@#$% 만 허용한다는 설명 넣어야할 듯..
				// !@#$% 를 포함하여 다른 특수기호를 누르면 입력이 되는 오류가 있음..
				System.out.println("비밀번호는 알파벳, 숫자, 특수 기호로만 입력하여 세 종류 모두 사용해야 합니다.");
				continue;
			} else if(tempPw.length() < 6 || tempPw.length() > 12) {
				System.out.println("비밀번는 6글자 이상, 12글자 이하로 입력해주세요.");
				continue;
			} else {
				pw = tempPw;
				break;
			}
		}
		
		return pw;
	}

	// [로그인]
	public static void login() {
		String loginId, loginPw;
		boolean isMatch = false;
		
		System.out.println("< 로그인 >");
		// 로그인 조건
		while(true) {
			System.out.println("\n아이디를 입력해주세요.");
			System.out.print("입력 > ");
			loginId = scan.nextLine();
			System.out.println("비밀번호를 입력해주세요.");
			System.out.print("입력 > ");
			loginPw = scan.nextLine();
			
			try {
				File file = new File("./login.txt");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				String line = "";
				while((line = bufferedReader.readLine()) != null) {
					String[] words = line.split(" ");
					if(loginId.equals(words[1]) && loginPw.equals(words[2])) {
						isMatch = true;
						break;
					}
				}
				
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 로그인 성공 
			if(isMatch) break;
			
			// 로그인 실패 
			System.out.println("입력이 잘 못 되었거나 계정이 존재하지 않습니다.");
		}
		
		// 로그인 성공 
		System.out.println("\n로그인에 성공하였습니다.\n\n");
		
	}

}
