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
        System.out.println("¼Û±İÇÒ »ç¶÷ÀÇ ÀÌ¸§À» ÀÔ·ÂÇØ ÁÖ¼¼¿ä");

        do {
            System.out.print("ÀÔ·Â > ");
            AcholderName = sc.nextLine().replaceAll(" ", "");
        } while (!nameIsRight(AcholderName));

        searchAcInfo(AcholderName);
    }

    public static boolean nameIsRight(String name) {
        if (!Pattern.matches("^[°¡-ÆR]*$", name)) {
            System.out.println("°èÁÂÁÖ´Â ¿ÏÀüÇÑ ÇÑ±Û ¹®ÀÚ¸¸ ÀÔ·Â °¡´ÉÇÕ´Ï´Ù");
            return false;
        }
        if (name.length() < 2 || name.length() > 5) {
            System.out.println("°èÁÂÁÖ´Â 2±ÛÀÚ ÀÌ»ó 5±ÛÀÚ ÀÌÇÏ·Î ÀÔ·ÂÇØÁÖ¼¼¿ä");
            return false;
        }
        return true;
    }

    /*
        ÇØ´ç °èÁÂÁÖÀÇ °èÁ¤Á¤º¸ È®ÀÎ
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
            System.out.println("°Ë»ö °á°ú°¡ ¾ø½À´Ï´Ù. ¸ŞÀÎ¸Ş´º·Î ÀÌµ¿ÇÕ´Ï´Ù.");
            // ¸ŞÀÎ ¸Ş´º·Î ÀÌµ¿
            return;
        }
        System.out.println("[¼Û±İÇÒ »ç¶÷ÀÇ ÀÎµ¦½º ¹øÈ£¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä]");

        List<String> files = new ArrayList<String>();
        files.add("¸ŞÀÎ ¸Ş´º·Î µ¹¾Æ°¡±â");

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
            System.out.print("ÀÔ·Â > ");
            input = sc.nextLine();
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        if (selectNum == 0) {
            // ¸ŞÀÎÀ¸·Î ÀÌµ¿
        } else {
            searchAcnum(files.get(selectNum)); // ÇØ´ç °èÁÂÁÖÀÇ °èÁÂ¹øÈ£ ¼±ÅÃÀ¸·Î ÀÌµ¿
        }
    }

    public static int selectNumCheck(String selectNum, int max) {
        try {
            int result = Integer.parseInt(selectNum);
            if (result >= 0 && result < max) {
                return result;
            }
        } catch (Exception e) {
            System.out.println("Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.");
            return -1;
        }
        System.out.println("Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.");
        return -1;
    }

    /*
        ÇØ´ç °èÁÂÁÖÀÇ °èÁÂ¹øÈ£ È®ÀÎ
     */
    public static void searchAcnum(String name_id) {
        File dir = new File("./"+name_id);

        String[] acnums = dir.list();
        if (acnums.length == 0) {
            System.out.println("¼Û±İ¿¡ ½ÇÆĞÇÏ¿´½À´Ï´Ù. ¸ŞÀÎ ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.");
            // ¸ŞÀÎÀ¸·Î ÀÌµ¿
            return;
        }

        System.out.println("["+name_id+" ´ÔÀÇ °èÁÂ¹øÈ£ÀÔ´Ï´Ù. ¼Û±İ Èñ¸ÁÇÏ´Â °èÁÂ¹øÈ£ÀÇ ÀÎµ¦½º ¹øÈ£¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä]");
        List<String> files = new ArrayList<String>();
        files.add("¸ŞÀÎ ¸Ş´º·Î µ¹¾Æ°¡±â");

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
            System.out.print("ÀÔ·Â > ");
            input = sc.nextLine();
            selectNum = selectNumCheck(input, files.size());
        } while (selectNum == -1);

        if (selectNum == 0) {
            // ¸ŞÀÎÀ¸·Î ÀÌµ¿
        } else {
            System.out.println("¼Û±İÀ» ½ÃÀÛÇÕ´Ï´Ù");
            // ÇØ´ç °èÁÂ¹øÈ£¿¡ ¼Û±İÇÏ±â À§ÇÏ¿© °¡»óÀÏÀÚ ÀÔ·Â ÇÁ·ÒÇÁÆ®·Î ÀÌµ¿
        }
    }
}
