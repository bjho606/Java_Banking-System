import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Reremit {

	private static String senderAcnumPath = "";
	private static String receiverAcnumPath = "";
	
	public static void main(String myName_id,String myAcnum) {
		// TODO Auto-generated method stub
		senderAcnumPath = myName_id + "/" + myAcnum + ".txt";
		String currentDate = VirtualDate.inputVirtualDate();

		String myPath=myName_id+"/"+myAcnum+".txt";
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
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

        while(true) {
            System.out.print("입력 > ");
            try {
            	if ((selectNum=sc.nextInt())==0){ 
                    User.mainMenu();//메뉴로 돌아가는 메소드 사용
                    break;
                }else{
                	/////////////////파일 수정 파트/////////////////
                	
                	//출력 확인
                    System.out.println(transactions.get(selectNum));
                    break;
                }
            }catch(IndexOutOfBoundsException e){
            	 System.out.println("유효한 정수가 입력되지 않았습니다. 다시 입력해주세요");
            }
                               
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

	

