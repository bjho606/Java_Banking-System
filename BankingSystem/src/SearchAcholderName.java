import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SearchAcholderName {
	
	private static String senderAccountDir = "";
	private static String senderAcnum = "";
	private static String senderAcnumPath = "";
	private static String receiverAccountDir = "";
	private static String receiverAcnumPath = "";
		
    public static void main(String name_id, String myAcnum) {
    	senderAccountDir = name_id;
    	senderAcnum = myAcnum + ".txt";
    	senderAcnumPath = "./members/" + name_id + "/" + myAcnum + ".txt";

        Scanner sc = new Scanner(System.in);
        String AcholderName = "";
        System.out.println("송금할 사람의 이름을 입력해 주세요");

        do {
            System.out.print("입력 > ");
            AcholderName = sc.nextLine();
            // 탈출 문자 입력 확인
 			if(AcholderName.contentEquals("cancel")) {
 				User.escapeBankingTask = true;
 				return;
 			}
            AcholderName = AcholderName.replaceAll(" ", "");
        } while (!nameIsRight(AcholderName));

        searchAcInfo(AcholderName);
    }

    public static boolean nameIsRight(String name) {
        if (!Pattern.matches("^[가-힣]*$", name)) {
            System.out.println("계좌주는 완전한 한글 문자만 입력 가능합니다");
            return false;
        }
        if (name.length() < 2 || name.length() > 5) {
            System.out.println("계좌주는 2글자 이상 5글자 이하로 입력해주세요");
            return false;
        }
        return true;
    }

    /*
     	해당 계좌주의 계정정보 확인
     */
    public static void searchAcInfo(String acholderName) {

        File dir = new File("./members/");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.startsWith(acholderName+"_");
            }
        };

        String[] dirs = dir.list(filter);
        if (dirs.length == 0) {
            System.out.println("검색 결과가 없습니다. 메인메뉴로 이동합니다.");
            User.mainMenu(); // 메인으로
            return;
        }
        System.out.println("[송금할 사람의 인덱스 번호를 입력해 주세요]");

        List<String> files = new ArrayList<String>();

        for (String i : dirs)
            files.add(i);

        for (int i = 0; i < files.size(); i++) {
            System.out.println(i + ". " + files.get(i));
        }

        selectAcholderName(files);
    }

    private static void selectAcholderName(List<String> files) {
        Scanner sc = new Scanner(System.in);
        int selectNum = -1;
        String input = "";

        do {
            System.out.print("입력 > ");
            input = sc.nextLine();
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        receiverAccountDir = files.get(selectNum);
        searchAcnum(files.get(selectNum)); // 해당 계좌주의 계좌번호 선택으로 이동
        
    }

    public static int selectNumCheck(String selectNum, int max) {
        try {
            int result = Integer.parseInt(selectNum);
            if (result >= 0 && result < max) {
                return result;
            }
        } catch (Exception e) {
            System.out.println("범위 내 정수를 입력해 주세요");
            return -1;
        }
        System.out.println("범위 내 정수를 입력해 주세요");
        return -1;
    }

    /*
		해당 계좌주의 계좌번호 확인
     */
    public static void searchAcnum(String name_id) {
        File dir = new File("./members/"+name_id);

        String[] acnums = dir.list();
       
        System.out.println("["+name_id+" 님의 계좌번호입니다. 송금 희망하는 계좌번호의 인덱스 번호를 입력해 주세요]");
        List<String> files = new ArrayList<String>();

        for (String i : acnums) {
        	if (i.equals(senderAcnum)) {
        		continue;
        	}
            files.add(i);
        }
        if (files.size() == 0) {
            System.out.println("해당 계좌주는 계좌를 소유하고 있지 않아, 송금에 실패하였습니다. 메인메뉴로 돌아갑니다.");
            User.mainMenu(); //메인으로 이동
            return;
        }
        for (int i = 0; i < files.size(); i++) {
            System.out.println(i + ". " + files.get(i));
        }
        
        selectAcnum(files);
    }

    private static void selectAcnum(List<String> files) {
        Scanner sc = new Scanner(System.in);
        int selectNum = -1;
        String input = "";

        do {
            System.out.print("입력 > ");
            input = sc.nextLine();
            // 탈출 문자 입력 확인
 			if(input.contentEquals("cancel")) {
 				User.escapeBankingTask = true;
 				return;
 			}
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

//        receiverAcnumPath = "./members/" + receiverAccountDir + "/" + files.get(selectNum);
        receiverAcnumPath = receiverAccountDir + "/" + files.get(selectNum);        
        System.out.println("송금을 시작합니다");
//        System.out.println(senderAcnumPath + "에서 "+ receiverAcnumPath + " 로 " + "송금을 시작합니다"); // 확인용
        VirtualDate.inputVirtualDate();
        Remit.inputRemit(senderAcnumPath, receiverAcnumPath);
        
    }
}