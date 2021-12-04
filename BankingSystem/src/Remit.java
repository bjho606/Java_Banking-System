import java.io.*;

import java.util.Scanner;

public class Remit {
	static int caseLimit = 10000;
	static int dailyLimit = 20000;
	static int monthlyLimit = 50000;
	public static void inputRemit(String sendingPath, String recievePath) {
		Scanner sc = new Scanner(System.in);
		int sending_dailyAmount;
		int sending_monthlyAmount;
		int recieve_dailyAmount;
		int recieve_monthlyAmount;
		int balance;
		
		String[] sending_splits = getAmounts(sendingPath).split(" ");
		sending_dailyAmount = Integer.parseInt(sending_splits[0]);
		sending_monthlyAmount = Integer.parseInt(sending_splits[1]);
		String[] recieve_splits = getAmounts(recievePath).split(" ");
		recieve_dailyAmount = Integer.parseInt(recieve_splits[0]);
		recieve_monthlyAmount = Integer.parseInt(recieve_splits[1]);
		balance = getBalance(sendingPath);
		
		System.out.println("송금액을 입력해주세요. 0만 입력 시 메인메뉴로 이동합니다.");
		System.out.print("입력>");
		while(true) {
			String enteredRemit = sc.nextLine();
			if(enteredRemit.matches("^[0]$")) return;
			enteredRemit = enteredRemit.trim();
			if(!enteredRemit.matches("^[0-9]+$")) {
				System.out.println("송금액의 문법규칙이 어긋났습니다. 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			
			int int_enteredRemit = Integer.parseInt(enteredRemit);
			
			if(int_enteredRemit>balance) {
				System.out.println("계좌안의 예금액보다 적은 송금액을 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(int_enteredRemit>caseLimit) {
				System.out.println("건당한도액보다 적은 송금액을 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(int_enteredRemit+sending_dailyAmount>dailyLimit) {
				System.out.println("송금 시 송금하는 계좌의 일당 한도액을 초과합니다. 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(int_enteredRemit+sending_monthlyAmount>monthlyLimit) {
				System.out.println("송금 시 송금하는 계좌의 월당 한도액을 초과합니다. 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(int_enteredRemit+recieve_dailyAmount>dailyLimit) {
				System.out.println("송금 시 송금받는 계좌의 일당 한도액을 초과합니다. 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(int_enteredRemit+recieve_monthlyAmount>monthlyLimit) {
				System.out.println("송금 시 송금받는 계좌의 월당 한도액을 초과합니다. 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			
			String modified_sending_dailyAmount = Integer.toString(int_enteredRemit+sending_dailyAmount);
			String modified_sending_monthlyAmount = Integer.toString(int_enteredRemit+sending_monthlyAmount);
			String modified_sending_line = modified_sending_dailyAmount+" "+modified_sending_monthlyAmount;
			
			String modified_recieve_dailyAmount = Integer.toString(int_enteredRemit+recieve_dailyAmount);
			String modified_recieve_monthlyAmount = Integer.toString(int_enteredRemit+recieve_monthlyAmount);
			String modified_recieve_line = modified_recieve_dailyAmount+" "+modified_recieve_monthlyAmount;
			
			
			update_Amount(sendingPath, recievePath,modified_sending_line,modified_recieve_line,int_enteredRemit);
			
			System.out.println("송금 완료");
			return;
		}
	}


	public static String getAmounts(String path) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(path));
			String tmp = reader.readLine();
			return tmp;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static int getBalance(String path) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(path));
			int count = 0;
			String str;
			String historyLine="";
			while ((str=reader.readLine()) != null) {
				count++;
				historyLine = str;
			}
			reader.close();

			if(count==1)
				return 0;
			String[] lineSplits = historyLine.split(" ");
			return Integer.parseInt(lineSplits[2]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	private static void update_Amount(String sendingPath, String recievePath, String modified_sending_line,
			String modified_recieve_line,int int_enteredRemit) {
		// TODO Auto-generated method stub
		modify_line(sendingPath, modified_sending_line, int_enteredRemit, 1, recievePath);
		modify_line(recievePath, modified_recieve_line, int_enteredRemit, 2, sendingPath);
	}
	
	
	
	//path_n : 송금을 보내는 쪽에선, 돈을 받는 계좌를 의미하고 / 송금 받는 쪽에선 돈을 보낸 계좌를 의미함.
	private static void modify_line(String path, String line, int int_enteredRemit, int type, String path_n) {
		String date = VirtualDate.getDate();
		
		File file = new File(path);		

		String dummy = "";

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

			
			String thisLine;
			String lastLine = "";
			String firstLine = br.readLine();
			dummy += (line + "\r\n" );
			int count = 1;
			
			String path_input = path_n.replace('/', ' ');
			path_input = path_input.substring(0, path_input.length() - 4);
			
			while((thisLine = br.readLine())!=null) {
				dummy += (thisLine + "\r\n" ); 
				lastLine = thisLine;
				count++;
			}
			
			int balance;
			if(count==1) balance=0;
			else
				balance = Integer.parseInt(lastLine.split(" ")[2]);

			if(type==1) {
				String temp = "3 "+Integer.toString(int_enteredRemit)+" "+
						Integer.toString(balance-int_enteredRemit)+" "+ path_input + " " + date;
				dummy += (temp + "\r\n");
				//System.out.println(temp);/////////////
			}else {
				String temp = "2 "+Integer.toString(int_enteredRemit)+" "+
						Integer.toString(balance+int_enteredRemit)+" "+ path_input + " " + date;
					
				dummy += (temp + "\r\n");
				//System.out.println(temp);/////////////
			}
			
			
			
			
			FileWriter fw = new FileWriter(path);

			fw.write(dummy);			

			//bw.close();

			fw.close();

			br.close();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}
}
