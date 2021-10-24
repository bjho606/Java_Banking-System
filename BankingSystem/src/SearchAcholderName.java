import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SearchAcholderName {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String AcholderName = "";
        System.out.println("송금할 사람의 이름을 입력해 주세요");

        do {
            System.out.print("입력 > ");
            AcholderName = sc.nextLine().replaceAll(" ", "");
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

        File dir = new File("./");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.startsWith(acholderName+"_");
            }
        };

        String[] dirs = dir.list(filter);
        if (dirs.length == 0) {
            System.out.println("검색 결과가 없습니다. 메인메뉴로 이동합니다.");
            // 메인 메뉴로 이동
            return;
        }
        System.out.println("[송금할 사람의 인덱스 번호를 입력해 주세요]");

        List<String> files = new ArrayList<String>();
        files.add("메인메뉴로 돌아가기");

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

        if (selectNum == 0) {
            // 메인으로 이동
        } else {
            searchAcnum(files.get(selectNum)); // 해당 계좌주의 계좌번호 선택으로 이동
        }
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
        File dir = new File("./"+name_id);

        String[] acnums = dir.list();
        if (acnums.length == 0) {
            System.out.println("해당 계좌주는 계좌를 소유하고 있지 않아, 송금에 실패하였습니다. 메인메뉴로 돌아갑니다.");
            // 메인으로 이동
            return;
        }

        System.out.println("["+name_id+" 님의 계좌번호입니다. 송금 희망하는 계좌번호의 인덱스 번호를 입력해 주세요]");
        List<String> files = new ArrayList<String>();
        files.add("메인메뉴로 돌아가기");

        for (String i : acnums)
            files.add(i);

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
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        if (selectNum == 0) {
            // 메인으로 이동
        } else {
            System.out.println("송금을 시작합니다");
            // 해당 계좌번호에 송금하기 위해, 가상일자 입력 프롬프트로 이동
        }
    }
}