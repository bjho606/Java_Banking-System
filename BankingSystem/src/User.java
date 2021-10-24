package banksystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class User {
	static String name, id;
	static Scanner scan = new Scanner(System.in);
	static String account;
	
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
					if(accountSelect() == 0) {
						continue;
					}

					System.out.println("[원하시는 메뉴를 선택하세요]");
					System.out.println("0. 메인 메뉴로 돌아가기");
					System.out.println("1. 통장내역 확인");
					System.out.println("2. 입/출금");
					System.out.println("3. 송금");
					System.out.print("입력 > ");
					
					while(true) {
						String sub_mn;
						int sub_menu;
						try {
							sub_mn = scan.nextLine();
							sub_mn = sub_mn.trim();
							sub_menu = Integer.parseInt(sub_mn);
						}catch(NumberFormatException e) {
							System.out.println("0이상 3이하의 숫자로 입력해주세요.");
							System.out.print("입력> ");
							continue;
						}
						
						if(sub_menu == 0) {
							//메인메뉴로 이동
							break;
						}else if(sub_menu == 1) {
							//통장내역 확인
							
							break;
						}else if(sub_menu == 2) {
							//입출금
							DepositWithdraw.depositwithdraw(name, id, account);
							break;
						}else if(sub_menu == 3) {
							//송금
							
							break;
						}else {
							System.out.println("0이상 2이하의 숫자로 입력해주세요");
							System.out.print("입력> ");
							continue;
						}

					}
					
					
	
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
	
	
	//계좌 선택 부분
	public static int accountSelect() {
		try{
			File file = new File("./acum_list.txt");
			FileReader fr = new FileReader(file);	
			BufferedReader br = new BufferedReader(fr);

			String aclist;
			
			while((aclist = br.readLine()) != null) {
				String[] list_name = aclist.split(" ");
				
				ArrayList<Integer> al = new ArrayList<>();
				
				if(name.equals(list_name[0])) {
					for(int i=2; i<list_name.length; i++) {
						String[] list_acum = list_name[i].split("-");
						al.add(Integer.parseInt(list_acum[0]));
					}
					Collections.sort(al);
					
					System.out.println("[이름]");
					System.out.println(list_name[0] + "\n");
					System.out.println("[계좌번호 목록]");
					System.out.println("0. 메인 메뉴로 돌아가기");
					
					String[] acc = new String[al.size()];
					for(int i=0; i<al.size(); i++) {
						for(int j=2; j<list_name.length; j++) {
							int sp = Integer.parseInt(list_name[j].split("-")[0]);
							if(sp == al.get(i)) {
								System.out.println(i+1 + ". " + list_name[j]);
								acc[i] = list_name[j];
							}
						}
					}
					
					System.out.print("입력 > ");
					String acum;
					int user_acum;
					while(true) {
						try {
							acum = scan.nextLine();
							acum = acum.trim();
							user_acum = Integer.parseInt(acum);
						}catch(NumberFormatException e) {
							System.out.println("0이상"+ al.size() + "이하의 숫자로 입력해주세요.");
							System.out.print("입력> ");
							continue;
						}
						
						if(user_acum == 0) {
							return 0;
							//0입력했을 때 메인 메뉴로 돌아가는 코드 짜야됨.
						}else if(user_acum <= al.size() & user_acum > 0) {
							account = acc[user_acum-1]; 
							break;
						}else {
							System.out.println("0이상"+ al.size() + "이하의 숫자로 입력해주세요.");
							System.out.print("입력> ");
							continue;
						}
						
					}
					break;
				}
			}
			br.close();
		}catch(FileNotFoundException e) {
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	

	
}
