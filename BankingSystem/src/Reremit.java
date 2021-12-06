import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.TargetDataLine;

public class Reremit {

	private static String senderAcnumPath = "";
	private static String receiverAcnumPath = "";
	
	public static void main(String myName_id,String myAcnum) {
		// TODO Auto-generated method stub
		senderAcnumPath = myName_id + "/" + myAcnum + ".txt";
		String currentDate = VirtualDate.inputVirtualDate();

		String myPath="./members/"+myName_id+"/"+myAcnum+".txt";
		try {
            File file = new File(myPath);
            FileReader filereader = new FileReader(file);
            BufferedReader br = new BufferedReader(filereader);

            String line;
            br.readLine();
            ArrayList<String> transactions=new ArrayList <String>();
            transactions.add("메인메뉴로 돌아가기");
            while((line = br.readLine()) != null) {
            	String[] info=line.split(" ");
            	try {
					if(isValidDate(currentDate,info[5])&&Integer.parseInt(info[0])==2) {
						
						transactions.add(line);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
            		
            }
            if(transactions.size()==1) {
            	System.out.println("되돌려줄 수 있는 계좌가 존재하지 않습니다. 메인 메뉴로 이동합니다.");
            	User.mainMenu();
            }
            
            System.out.println("0. "+transactions.get(0));
            for (int i=1;i<transactions.size();i++) {
            	String info[]=transactions.get(i).split(" ");
            	System.out.println(i+". "+ info[1]+" "+info[3]+" "+info[4]+" "+info[5]);

            }
            
            selectTransaction(transactions);
            
            br.close();
            filereader.close();
            transactions.clear();

        } catch (IOException ex) {
            System.err.println(ex);
        }
	}
	
	private static void selectTransaction(List<String> transactions) {
		Scanner sc=new Scanner(System.in);
        int selectNum =0;
        
        ArrayList<String> sinfoList = new ArrayList<>();
        ArrayList<String> rinfoList = new ArrayList<>();
        
        String[] target_transaction = new String[6];
        String[] tmp = senderAcnumPath.split("/");
        String[] tmp2 = tmp[1].split("\\.");

        String sacnumPath = ""; 
        String racnumPath = "";
        
    	// sender 정보
    	String sname_id = "";
    	String sacnum = "";
    	String sbalance = "";
    	String sdaily = "";		// s의 일 누적금액
    	String smonthly = "";	// s의 월 누적금액
    	String stransaction = "";
    	String saddTransaction = "";
    	
    	// receiver 정보
    	String rname_id = "";
    	String racnum = "";
    	String rbalance = "";
    	String rdaily = "";		// r의 일 누적금액
    	String rmonthly = "";	// r의 월 누적금액
    	String rtransaction = "";
    	String raddTransaction = "";

    	// 거래 날짜, 금액
    	String tdate = "";
    	String tamount = "";
    	
    	String today = "";
    	today = getToday();
    	
    	// 되돌려주기: 같은 날이면 0, 다른 달이면 1, 같은 달 다른 날이면 2
    	int cond = -1;
    	

        while(true) {
            System.out.print("입력 > ");
            try {
            	if ((selectNum=sc.nextInt())==0){ 
                    User.mainMenu();//메뉴로 돌아가는 메소드 사용
                    break;
                }else{
                	/////////////////파일 수정 파트/////////////////                	
                
                	target_transaction = transactions.get(selectNum).split(" ");
                	
                	sname_id = tmp[0];
                	sacnum = tmp2[0];
                	rname_id = target_transaction[3];
                	racnum = target_transaction[4];
                	tdate = target_transaction[5];
                	tamount = target_transaction[1];
                	
                	sacnumPath = "./members/" + senderAcnumPath; 					
                	racnumPath = "./members/" + rname_id + "/" + racnum + ".txt";

                	sinfoList = getAcInfo(sacnumPath);
                	rinfoList = getAcInfo(racnumPath);
                	
                	rdaily = rinfoList.get(0);
                	rmonthly = rinfoList.get(1);
                	rbalance = rinfoList.get(2);
                	
                	sdaily = sinfoList.get(0);
                	smonthly = sinfoList.get(1);
                	sbalance = sinfoList.get(2);
                	
                	stransaction = transactions.get(selectNum);
                	rtransaction = "3 " + tamount + " " + rbalance + " " + sname_id + " " + sacnum + " " + tdate;
                	
                	saddTransaction = "4 " + tamount + " " + String.valueOf(Integer.parseInt(sbalance) - Integer.parseInt(tamount)) + " " + "NULL NULL" + " " + today;
                	raddTransaction = "4 " + tamount + " " + String.valueOf(Integer.parseInt(rbalance) + Integer.parseInt(tamount)) + " " + "NULL NULL" + " " + today;
                	
                	cond = getCond(tdate, today);
                	
                	editFile(true, sacnumPath, stransaction, saddTransaction, cond, sdaily, smonthly, tamount);
                	editFile(false, racnumPath, rtransaction, raddTransaction, cond, rdaily, rmonthly, tamount);
                	
                	//출력 확인
//                  System.out.println("되돌려 줄 계정/계좌" + senderAcnumPath);
//                  System.out.println("해당 거래 내역 " + transactions.get(selectNum));
                	
//                	System.out.println("sname_id: "+sname_id);
//                	System.out.println("sacnum: "+sacnum);
//                	System.out.println("sbalance: "+sbalance);
//                	System.out.println("rname_id: "+rname_id);
//                	System.out.println("racnum: "+racnum);
//                	System.out.println("tdate: "+tdate);
//                	System.out.println("tamount: "+tamount);
//                	System.out.println("rdaily: "+rdaily);
//                	System.out.println("rmonthly: "+rmonthly);
//                	System.out.println("rbalance: "+rbalance);
//                	System.out.println("sdaily: "+sdaily);
//                	System.out.println("smonthly: "+smonthly);
//                	System.out.println("sbalance: "+sbalance);
//                	System.out.println("sacnumPath: "+sacnumPath);
//                	System.out.println("racnumPath: "+racnumPath);
//                	System.out.println("cond: "+cond);
//                	System.out.println("saddTransaction: " + saddTransaction);
//                	System.out.println("raddTransaction: " + raddTransaction);

                    break;
                }
            }catch(IndexOutOfBoundsException e){
            	 System.out.println("유효한 정수가 입력되지 않았습니다. 다시 입력해주세요");
            }
                               
        }
	}
	
	
	public static String getToday() {
		
		Path path = Paths.get("./time.txt");
		String today = "";
		
		try {
    		List<String> allLines = Files.readAllLines(path);
    		today = allLines.get(0);
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		return today;
	}

	// 해당계좌의 일누적금액, 월누적금액, 잔액 (순서대로) 반환하는 메서드
	public static ArrayList<String> getAcInfo(String acnumPath) {
		
		ArrayList<String> daily_monthly_balance = new ArrayList();
		String[] tmp = new String[2]; // 누적금액 정보
		String[] tmp2 = new String[6];// 마지막 거래 내역담기 (여기서 잔액 파싱)
		
				
		Path path = Paths.get(acnumPath);
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(acnumPath));
    		
    		// 라인수 세기
    		int lineCount = 0;
    		while (reader.readLine() != null) {
    			lineCount++;
    		}
    		
    		// 1. 파일 전체 읽기
    		List<String> allLines = Files.readAllLines(path);
    		
    		// 2. 처음, 마지막 라인 읽기
    		String nthLineStart = allLines.get(0);			// 공백기준 앞이 일누적, 뒤가 월누적
    		String nthLineEnd = allLines.get(lineCount-1);	// 공백기준 0 1 2번째가 잔액
    		tmp = nthLineStart.split(" ");
    		tmp2 = nthLineEnd.split(" ");
    		
//    		System.out.println("일누적금액 tmp[0]: "+tmp[0]);
//    		System.out.println("월누적금액 tmp[1]: "+tmp[1]);
//    		System.out.println("잔액 tmp2[2]: "+tmp2[2]);
    		
    		daily_monthly_balance.add(tmp[0]);
    		daily_monthly_balance.add(tmp[1]);
    		daily_monthly_balance.add(tmp2[2]);
    		
    		reader.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
		return daily_monthly_balance;
	}
	
	// 거래날짜와 돌려주기 날짜의 ..
	public static int getCond(String tdate, String today) {
		int cond = -1;
		
		if (tdate.substring(0, 4).equals(today.substring(0, 4))) { 
			if (tdate.substring(4, 6).equals(today.substring(4, 6))) { 
				if (tdate.subSequence(6, 8).equals(today.substring(6, 8))) { // 같은 날
					cond = 0;
				}
				else { // 같은 달 다른 날
					cond = 2;
				}
			}
			else { // 같은 년도, 다른 달
				cond = 1;
			}
		}
		else { // 다른 년도
			cond = 1;
		}
		
		return cond;
	}

	// sender, receiver 파일 고치는 메서드 
	public static void editFile(boolean isSender, String filePath, String targetTransaction, String addTransaction, int cond, String daily, String montly, String tamount) {
		
		String changeTransaction = "";
	
		if (isSender) {
			changeTransaction = targetTransaction.replaceFirst("2", "5"); 
		} else {
			changeTransaction = targetTransaction.replaceFirst("3", "5"); 
		}


		Path path = Paths.get(filePath);
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(filePath));
    		
    		// 라인수 세기
    		int targetLine = 0;
    		int lineCount = 0;
    		while (reader.readLine() != null) { lineCount++; }
    		
    		// 파일 전체 읽어서 리스트에 담기
    		List<String> allLines = Files.readAllLines(path);
    		
    		// 2. 처음부터 한 줄씩 읽으면서, 해당 거래내역이 몇 번째인지 알아내기
    		for (int i = 0; i< lineCount; i++) { if (allLines.get(i).equals(targetTransaction)) { targetLine = i; } }
    	
    		// 일누적금액, 월누적금액 갱신	
    		if (cond == 0) {
    			// 같은 날에 돌려줄 때: 일누적-거래액, 월누적-거래액 갱신
    			allLines.remove(0);
    			allLines.add(0, String.valueOf(Integer.parseInt(daily) - Integer.parseInt(tamount)) + " " + String.valueOf(Integer.parseInt(montly) - Integer.parseInt(tamount)));
    		}
    		else if (cond == 1) {
    			// 다른 달이므로 수정 없음
    			allLines.remove(0);
    			allLines.add(0, "0 0");

    		}
    		else if (cond == 2) {
    			// 같은 달, 다른 날: 월 누적액만 수정
    			allLines.remove(0);
    			allLines.add(0, "0 " + String.valueOf(Integer.parseInt(montly) - Integer.parseInt(tamount)));
    		}
    		
    		// 해당 거래내역 유형번호 수정
    		allLines.remove(targetLine);
    		allLines.add(targetLine, changeTransaction);
    		allLines.add(addTransaction);
    		  		
    		// 파일 쓰기
    		// 원래 파일 모두 지우고 한줄씩 쓰기
    		File file = new File(filePath);
    		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

    		for (int k = 0; k< allLines.size(); k++) {
    			writer.write(allLines.get(k)); 
        		writer.newLine(); 
    		}
    	
    		// 버퍼 및 스트림 뒷정리 
    		writer.flush(); // 버퍼의 남은 데이터를 모두 쓰기 
    		writer.close(); // 스트림 종료
    	
    		reader.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		
	}
	
	//되돌려주기 가능 확인 메소드
		public static boolean isValidDate(String date1,String date2) {
			 Calendar cal = Calendar.getInstance();
		        DateFormat df = new SimpleDateFormat("yyyyMMdd");
		        Date currentDate = null;
		        Date fileDate=null;
		        Date aWeekAgoDate=null;
		        try {
		        	currentDate = df.parse(date1);
					fileDate=df.parse(date2);
		        }catch (ParseException e) {
		            
		        }
				

		        cal.setTime(currentDate);
		        cal.add(Calendar.DATE, -7);
		        aWeekAgoDate=cal.getTime();

		        try {
		        	if(aWeekAgoDate.compareTo(fileDate)<0||aWeekAgoDate.compareTo(fileDate)==0) {
						return true;
				}
		        }catch(NullPointerException e) {
		        	
		        }

		        return false;
		    }


	}




	

