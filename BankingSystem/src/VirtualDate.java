import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class VirtualDate {
	static String eightDigits = "^[0-9]{8}$";
	
	public static String inputVirtualDate() {
		Scanner scanner = new Scanner(System.in);
		File file = new File("./time.txt");
		Scanner virtualDateScanner;
		String date=null;
		try {
			virtualDateScanner = new Scanner(file);
			date = virtualDateScanner.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("가상일자(YYYYMMDD)를 입력해주세요.");
		System.out.print("입력>");
		while(true) {
			String inputDate = scanner.nextLine();
			
			if(!Pattern.matches(eightDigits, inputDate)) {
				System.out.println("가상일자는 8자리 정수로만 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			if(!checkDate(inputDate)) {
				System.out.println("해당 날짜는 유효하지 않는 날짜입니다. 확인 후 다시 입력해주세요.");
				System.out.print("입력>");
				continue;
			}
			else {
				int tmp = date.compareTo(inputDate);
				if(tmp>0) {
					System.out.println("가상일자는 현재 날짜 기준 오늘 또는 미래의 날짜를 입력해주세요.");
					System.out.print("입력>");
					continue;
				}else {
					update(date,inputDate);
					writeDate(file,inputDate);
					return inputDate;
				}
			}
		}
		
		
	}
	
	public static boolean checkDate(String inputDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //검증할 날짜 포맷 설정
            sdf.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
            sdf.parse(inputDate); //대상 값 포맷에 적용되는지 확인
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	public static void writeDate(File file,String inputDate) {
		try {
			FileWriter fw = new FileWriter(file);
		    fw.write(inputDate);
		    fw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public static void update(String date,String inputDate) {
		int digit = 0;
		for(digit=0;digit<date.length();digit++) {
			char tmp1 = date.charAt(digit);
			char tmp2 = inputDate.charAt(digit);
			if(tmp1!=tmp2)
				break;	
		}
		if(0<=digit&&digit<=5)	
			updating(1);			//월,일 수정
		else if(6<=digit&&digit<=7)
			updating(2);			//일 수정
		else if(digit==8)
			return;
	}
	public static void updating(int type) {
		File rootDir = new File("./");
		File clientFiles[] = rootDir.listFiles();
		for(int i=0;i<clientFiles.length;i++) {
			File clientFile = clientFiles[i];
			String clientFileName = clientFile.getName();
			if(clientFile.isDirectory()&&!clientFileName.equals("bin")&&!clientFileName.equals("src")&&clientFileName.charAt(0)!='.') {
				File accountFiles[] = clientFile.listFiles();
				for(int j=0;j<accountFiles.length;j++) {
					File accountFile = accountFiles[j];
					modifyFile(accountFile,type);
				}
			}
		}
	}
	public static void modifyFile(File inFile,int type) {
//		try {
//			String thisLine = "";
//			
//			// 임시파일을 생성
//			File outFile = new File("$$$$$$$$.tmp");
//
//			// 아규먼트로 받은 입력 파일
//			FileInputStream fis = new FileInputStream(inFile);
//			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//
//			// output 파일
//			FileOutputStream fos = new FileOutputStream(outFile);
//			PrintWriter out = new PrintWriter(fos);
//
//			int i = 1;
//      
//			//파일 내용을 한라인씩 읽어 삽입될 라인이 오면 문자열을 삽입
//			while ((thisLine = in.readLine()) != null) {
//			        if (i == 1) {
//			        	
//			        	if(type==1) {	//년 또는 월이 바뀐경우
//			        		out.println("0 0");
//			        	}else {			//일만 바뀐경우
//			        		String tmp = thisLine;
//			        		String[] accs = tmp.split(" ");
//			        		tmp = "0 "+accs[1];
//			        		out.println(tmp);
//			        	}
//			        }
//			        else {
//			        	out.println(thisLine);
//			        }
//			        i++;
//			}
//			out.flush();
//			out.close();
//			in.close();
//
//			inFile.delete();
//
//			//임시파일을 원래 파일명으로 변경
//			outFile.renameTo(inFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		File file = inFile;		

		String dummy = "";

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

			
			String thisLine;
			String firstLine = br.readLine();
			
			
			if(type==1) {	//년 또는 월이 바뀐경우
        		dummy += ("0 0" + "\r\n");
        	}else {			//일만 바뀐경우
        		String[] splits = firstLine.split(" ");
        		String tmp = "0 "+splits[1];
        		dummy += (tmp + "\r\n"); 
        	}
			
			
			
			while((thisLine = br.readLine())!=null) {
				dummy += (thisLine + "\r\n" ); 
			}
			
			
			
			
			FileWriter fw = new FileWriter(file.getPath());

			fw.write(dummy);			

			//bw.close();

			fw.close();

			br.close();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static String getDate() {
		File file = new File("./time.txt");
		Scanner virtualDateScanner;
		String date=null;
		try {
			virtualDateScanner = new Scanner(file);
			date = virtualDateScanner.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
