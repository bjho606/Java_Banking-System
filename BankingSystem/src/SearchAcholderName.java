import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SearchAcholderName {
    public static void searchAcholderName() {
        Scanner sc = new Scanner(System.in);
        String AcholderName = "";
        System.out.println("�۱��� ����� �̸��� �Է��� �ּ���");

        do {
            System.out.print("�Է� > ");
            AcholderName = sc.nextLine().replaceAll(" ", "");
        } while (!nameIsRight(AcholderName));

        searchAcInfo(AcholderName);
    }

    public static boolean nameIsRight(String name) {
        if (!Pattern.matches("^[��-�R]*$", name)) {
            System.out.println("�����ִ� ������ �ѱ� ���ڸ� �Է� �����մϴ�");
            return false;
        }
        if (name.length() < 2 || name.length() > 5) {
            System.out.println("�����ִ� 2���� �̻� 5���� ���Ϸ� �Է����ּ���");
            return false;
        }
        return true;
    }

    /*
        �ش� �������� �������� Ȯ��
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
            System.out.println("�˻� ����� �����ϴ�. ���θ޴��� �̵��մϴ�.");
            // ���� �޴��� �̵�
            return;
        }
        System.out.println("[�۱��� ����� �ε��� ��ȣ�� �Է��� �ּ���]");

        List<String> files = new ArrayList<String>();
        files.add("���� �޴��� ���ư���");

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
            System.out.print("�Է� > ");
            input = sc.nextLine();
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        if (selectNum == 0) {
            // �������� �̵�
        } else {
            searchAcnum(files.get(selectNum)); // �ش� �������� ���¹�ȣ �������� �̵�
        }
    }

    public static int selectNumCheck(String selectNum, int max) {
        try {
            int result = Integer.parseInt(selectNum);
            if (result >= 0 && result < max) {
                return result;
            }
        } catch (Exception e) {
            System.out.println("�߸��� �Է��Դϴ�.");
            return -1;
        }
        System.out.println("�߸��� �Է��Դϴ�.");
        return -1;
    }

    /*
        �ش� �������� ���¹�ȣ Ȯ��
     */
    public static void searchAcnum(String name_id) {
        File dir = new File("./"+name_id);

        String[] acnums = dir.list();
        if (acnums.length == 0) {
            System.out.println("�۱ݿ� �����Ͽ����ϴ�. ���� �޴��� ���ư��ϴ�.");
            // �������� �̵�
            return;
        }

        System.out.println("["+name_id+" ���� ���¹�ȣ�Դϴ�. �۱� ����ϴ� ���¹�ȣ�� �ε��� ��ȣ�� �Է��� �ּ���]");
        List<String> files = new ArrayList<String>();
        files.add("���� �޴��� ���ư���");

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
            System.out.print("�Է� > ");
            input = sc.nextLine();
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        if (selectNum == 0) {
            // �������� �̵�
        } else {
            System.out.println("�۱��� �����մϴ�");
            // �ش� ���¹�ȣ�� �۱��ϱ� ���Ͽ� �������� �Է� ������Ʈ�� �̵�
        }
    }
}
