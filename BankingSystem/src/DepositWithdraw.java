import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class DepositWithdraw {
	static Scanner scan = new Scanner(System.in);
	

	//입출금 부분
	public static void depositwithdraw(String n, String i, String ac) {
		System.out.println("[입/출금 메뉴 선택]");
		System.out.println("0 메인메뉴로 돌아가기");
		System.out.println("1 입금");
		System.out.println("2 출금");
		System.out.print("입력> ");
		
		
		String ssub_mn;
		int ssub_menu;
		while(true) {
			try {
				ssub_mn = scan.nextLine();
				ssub_mn = ssub_mn.trim();
				ssub_menu = Integer.parseInt(ssub_mn);
			}catch(NumberFormatException e) {
				System.out.println("0이상 2이하의 숫자로 입력해주세요.");
				System.out.print("입력> ");
				continue;
			}
			
			
			if(ssub_menu == 0) {
				//메인메뉴로 이동
				break;
			}else if(ssub_menu == 1) {
				//입금 함수
				deposit(n, i, ac);
				break;
			}else if(ssub_menu == 2) {
                //출금 함수
				withdraw(n, i, ac);
				break;
			}else {
				System.out.println("0이상 2이하의 숫자로 입력해주세요");
				System.out.print("입력> ");
				continue;
			}
		}
	}
	
	
	//입금 부분
	public static void deposit(String n, String i, String ac) {
		//가상일자 함수
		//String v_date = VirtualDate.inputVirtualDate();


		try {
			String f = "./members/" + n + "_" + i + "/" + ac + ".txt";
			File file = new File(f);
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw, true);
				
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			
			long fileSize = 0;
			long pos = 0;
			int cur_money = 0;
			String v_date = null;
			
			loop:
			while(true){
				v_date = VirtualDate.inputVirtualDate();
				
				fileSize = raf.length();
				pos = fileSize - 1;
				
				
				raf.seek(pos);
				pos = pos - 1;

			
				
				while(true) {
					raf.seek(pos);
					if(raf.readByte()=='\n'){

						break;
					}
					pos--;
					if(pos == 0) {
						cur_money = 0;
						break loop;
					}
				}

				String[] last = null;
				int count = 0;
				if(pos != 0) {
					raf.seek(pos+1);
					
					String lastline = raf.readLine();

					if(lastline != null) {
						//pw.print('\n');
						last = lastline.split(" ");
						cur_money = Integer.parseInt(last[2]);
					}
					
					
					//입출금 횟수 제한

					if(v_date.equals(last[5])) {
						
						if(Integer.parseInt(last[0]) == 0) {
							count++;
						}
						
						while(true){
							pos = pos - 1;
							if(pos < 0) {
								break loop;
							}
							while(true) {
								raf.seek(pos);
								if(raf.readByte()=='\n'){
									break;
								}
								pos--;
								if(pos == 0) {
									break;
								}
							}
							
							if(pos != 0) {
								raf.seek(pos+1);
						
								lastline = raf.readLine();
								
								if(lastline != null) {
									last = lastline.split(" ");
									if(last.length == 6) {
										if(v_date.equals(last[5])) {
											if(Integer.parseInt(last[0]) == 0) {
												count++;
											}
										}else {
											break loop;
										}
									}else {
										break loop;
									}
								}else {
									break loop;
								}
							}
							
							if(count == 2) {
								break;
							}
						}
					}else {
						break;
					}
				}
				if(count == 2) {
					System.out.println("해당 일자로는 더 이상 입금하실 수 없습니다. 다시 입력해주세요.");
					continue;
				}
			}
	
			
			pos = raf.length() - 1;
			while(true) {
				raf.seek(pos+1);
				if(raf.read()>0) {
					if(raf.readByte()=='\n'){
						//System.out.println("00");
						break;
					}
				}
				if(raf.read()<0) {
					raf.seek(pos);
					if(raf.readByte()=='\n') {
						//System.out.println("000");
						break;
					}
					//System.out.println("0000");
					pw.print('\n');
					break;
				}
				
				pos++;
			}
			
			
			raf.close();
			
			
			String in;
			int in_money;
			
			System.out.println("[입금]");
			
			while(true) {
				System.out.println("입금액을 입력하세요 (메인메뉴로 돌아가기 : 0)");
				System.out.print("입력> ");

				try{	
					in = scan.nextLine(); 
					// 탈출 문자 입력 확인
					if(in.contentEquals("cancel")) {
						User.escapeBankingTask = true;
						return;
					}
					
					in = in.trim();
					in_money = Integer.parseInt(in);
				}catch(NumberFormatException e) {
					System.out.println("문법 형식에 위배된 입금액입니다.");
					continue;
				}

				if(in_money == 0) {
					break;
					//메인메뉴로 이동
				}
				else if(in_money >= 1 && in_money <= 100000){
					break;
				}
				else if(in_money < 1 || in_money > 100000) {
					System.out.println("입금 가능 금액이 아닙니다.");
				}
				else if(in.isBlank()){
					System.out.println("문법 형식에 위배된 입금액입니다.");
				}
			}

			if(in_money != 0) {
				pw.print(0);
				pw.print(' ');
				pw.print(in_money);
				pw.print(' ');
				pw.print(cur_money + in_money);
				pw.print(' ');
				pw.print("NULL");
				pw.print(' ');
				pw.print("NULL");
				pw.print(' ');
				pw.print(v_date);
			}
			
			
			pw.flush();
			pw.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//출금 부분
	public static void withdraw(String n, String i, String ac) {
		//가상일자 함수
		//String v_date = VirtualDate.inputVirtualDate();


		try {
			String f = "./members/" + n + "_" + i + "/" + ac + ".txt";
			File file = new File(f);
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw, true);
				
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			
			long fileSize = 0;
			long pos = 0;
			int cur_money = 0;
			String v_date = null;
			
			loop:
			while(true){
				v_date = VirtualDate.inputVirtualDate();
				
				fileSize = raf.length();
				pos = fileSize - 1;
				
				
				raf.seek(pos);
				pos = pos - 1;

			
				
				while(true) {
					raf.seek(pos);
					if(raf.readByte()=='\n'){

						break;
					}
					pos--;
					if(pos == 0) {
						cur_money = 0;
						break loop;
					}
				}

				String[] last = null;
				int count = 0;
				if(pos != 0) {
					raf.seek(pos+1);
					
					String lastline = raf.readLine();

					if(lastline != null) {
						//pw.print('\n');
						last = lastline.split(" ");
						cur_money = Integer.parseInt(last[2]);
					}
					
					
					//입출금 횟수 제한

					if(v_date.equals(last[5])) {
						
						if(Integer.parseInt(last[0]) == 1) {
							count++;
						}
						
						while(true){
							pos = pos - 1;
							if(pos < 0) {
								break loop;
							}
							while(true) {
								raf.seek(pos);
								if(raf.readByte()=='\n'){
									break;
								}
								pos--;
								if(pos == 0) {
									break;
								}
							}
							
							if(pos != 0) {
								raf.seek(pos+1);
						
								lastline = raf.readLine();
								
								if(lastline != null) {
									last = lastline.split(" ");
									if(last.length == 6) {
										if(v_date.equals(last[5])) {
											if(Integer.parseInt(last[0]) == 1) {
												count++;
											}
										}else {
											break loop;
										}
									}else {
										break loop;
									}
								}else {
									break loop;
								}
							}
							
							if(count == 2) {
								break;
							}
						}
					}else {
						break;
					}
				}
				if(count == 2) {
					System.out.println("해당 일자로는 더 이상 출금하실 수 없습니다. 다시 입력해주세요.");
					continue;
				}
			}
	
			
			pos = raf.length() - 1;
			while(true) {
				raf.seek(pos+1);
				if(raf.read()>0) {
					if(raf.readByte()=='\n'){
						//System.out.println("00");
						break;
					}
				}
				if(raf.read()<0) {
					raf.seek(pos);
					if(raf.readByte()=='\n') {
						//System.out.println("000");
						break;
					}
					//System.out.println("0000");
					pw.print('\n');
					break;
				}
				
				pos++;
			}
			
			
			
			raf.close();
			
			String out;
			int out_money;
			
			System.out.println("[출금]");
			
			while(true) {
				System.out.println("출금액을 입력하세요 (메인메뉴로 돌아가기 : 0)");
				System.out.print("입력> ");

				try{	
					out = scan.nextLine(); 
					// 탈출 문자 입력 확인
					if(out.contentEquals("cancel")) {
						User.escapeBankingTask = true;
						return;
					}
					out = out.trim();
					out_money = Integer.parseInt(out);
				}catch(NumberFormatException e) {
					System.out.println("문법 형식에 위배된 출금액입니다.");
					continue;
				}

				if(out_money == 0) {
					//메인메뉴로 이동
					break;
				}
				else if(out_money >= 1 && out_money <= 100000 && out_money <= cur_money){
					break;
				}
				else if(out_money < 1 || out_money > 100000 || out_money > cur_money) {
					System.out.println("출금 가능 금액이 아닙니다.");
				}
				else if(out.isBlank()){
					System.out.println("문법 형식에 위배된 출금액입니다.");
				}
			}

			if(out_money != 0) {
				pw.print(1);
				pw.print(' ');
				pw.print(out_money);
				pw.print(' ');
				pw.print(cur_money - out_money);
				pw.print(' ');
				pw.print("NULL");
				pw.print(' ');
				pw.print("NULL");
				pw.print(' ');
				pw.print(v_date);
			}
			
			
			pw.flush();
			pw.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
