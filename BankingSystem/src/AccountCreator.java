import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class AccountCreator {
	
	public static String createAccountNum(int virtualDate) {
        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        String acNum = "";
        System.out.println("[계좌생성]\n계좌번호의 앞 4자리 숫자를 입력하세요. (숫자 중복은 허용된다. " +
                "단, 맨 앞에 0을 쓰면 안되고, 계좌번호의 중복은 허용되지 않는다).");
        System.out.print("입력>");

        while(!isValid) {
            String numOfInput = scanner.nextLine();
            if (numOfInput.contains(" ")) {
                System.out.println("입력된 계좌번호 앞 4자리 숫자 중 공백이 포함되었습니다. 다시 입력해주세요.");
                System.out.print("입력>");
                continue;
            }
            if (numOfInput.length() < 4) {
                System.out.println("입력된 계좌번호 앞자리 수가 4자리 미만입니다. 다시 입력해주세요.");
                System.out.print("입력>");
                continue;
            }
            if (numOfInput.length() > 4) {
                System.out.println("입력된 계좌번호 앞자리 수가 4자리를 초과했습니다. 다시 입력해주세요.");
                System.out.print("입력>");
                continue;
            }
            if (numOfInput.charAt(0) == '0') {
                System.out.println("맨 앞자리에 0을 적을 수 없습니다. 다시 입력해주세요.");
                System.out.print("입력>");
                continue;
            }

            int num = Integer.parseInt(numOfInput);
            acNum = num + "-" + virtualDate;

            if (isDuplicatedAcNum(acNum)) {
                System.out.println("입력된 계좌번호가 이미 존재합니다. 계좌번호의 앞 4자리를 다시 입력해주세요.");
                System.out.print("입력>");
                continue;
            }
            
            System.out.println("계좌번호 " + acNum + "가 정상적으로 생성되었습니다.\n");
            
            isValid = true;
        }

        return acNum;
    }

    private static boolean isDuplicatedAcNum(String acNum) {
        try {
            File file = new File("acnums_list.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader br = new BufferedReader(filereader);

            String line;
            boolean isDuplicated = false;
            while((line = br.readLine()) != null) {
                if (line.contains(acNum)) {
                    isDuplicated = true;
                    break;
                }
            }

            br.close();
            filereader.close();

            if (isDuplicated) return true;
            return false;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }

    public static void run(String name, String id) {
        // 가상일자 입력받기.(가상일자 입력프롬프트)
        int virtualDate = Integer.parseInt(VirtualDate.inputVirtualDate());

        // 계좌번호 앞 4자리 입력
        String acNum = createAccountNum(virtualDate);

        // 계좌번호 리스트 파일에 적기
        addAccountNumInListFile(acNum, name, id);

        // 해당 유저 디렉토리에 계좌이름의 파일 만들기
        makeAccountNumFileInUserDirectory(acNum, name, id);
    }

    //byte 단위로 쓰는 것이 편하다
    private static void addAccountNumInListFile(String acNum, String name, String id) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("acnums_list.txt", "rw");

            String line;
            boolean isExistedDifferentAcNum = false;
            while((line = randomAccessFile.readLine()) != null) {
            	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                if (line.contains(name + "_" + id)) {
                    isExistedDifferentAcNum = true;
                    randomAccessFile.seek(randomAccessFile.getFilePointer());
                    randomAccessFile.write(acNum.getBytes());
                    randomAccessFile.write(" ".getBytes());
                    break;
                }
            }
            
            randomAccessFile.seek(randomAccessFile.length());
            String userInfo;
            
            if (randomAccessFile.length() == 0) 
            	userInfo = name + "_" + id + " " + acNum;
            else
            	userInfo = "\n" + name + "_" + id + " " + acNum;
            
            if (!isExistedDifferentAcNum) {
                randomAccessFile.write(userInfo.getBytes());
                randomAccessFile.write(" ".getBytes());
            }
            
            randomAccessFile.close();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private static void makeAccountNumFileInUserDirectory(String acNum, String name, String id) {
        try {
        	// 회원가입, 로그인으로 이미 해당폴더 만들어져있는 상황
            String rootPath = "./members/" + name + "_" + id;
            File f = new File(rootPath);
            if (f.exists() && f.isDirectory()) {
                File f2 = new File(rootPath + "/" + acNum + ".txt");
                FileWriter fw = new FileWriter(f2, true);
                BufferedWriter bw = new BufferedWriter(fw);
                
                bw.write("0 0");

                bw.close();
                fw.close();
            }
        } catch (IOException ex) {
        	System.err.println(ex);
        }
    }

	
//	//임시로 적은 이름과 ID
//	public static void main(String[] args) {
//		String name = "김보규";
//		String id = "qhrb";
//		
//		run(name, id);
//	}
}
