import java.io.*;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Scanner;

//계좌번호 검색
public class SearchAcnum {
    /*
        main 메소드
     */
    public static void main() {
        String name_id="";
        int menuSelect=-1;
        Scanner sc=new Scanner(System.in);
        System.out.println("검색할 계좌번호를 입력하세요.");
        if ((name_id=filterInput()).equals("No result")){
            System.out.println("검색결과가 없습니다.");
            User.mainMenu();//메인메뉴로 돌아가기
        }else{
            StringTokenizer st=new StringTokenizer(name_id,"_");
            String name=st.nextToken();
            System.out.printf("[계좌번호 검색 결과]\n이름:%s\n\n",name);
            System.out.println("[수행할 동작을 결정하세요]");
            System.out.println("0. 메인 메뉴로 돌아가기");
            System.out.println("1. 송금 가상일자 입력하기");
            while(true) {
                System.out.print("입력 > ");
                
                if ((menuSelect=sc.nextInt())==0){
                    System.out.println("메뉴로 돌아가기");//메뉴로 돌아가는 메소드 사용
                    User.mainMenu();
                    break;
                }else if (menuSelect==1){
                    System.out.println("송금 가상일자 입력하기");// 송금 파트 메소드 사용
                    break;
                }else{
                    System.out.println("0이상 1이하의 숫자로 입력해주세요.");
                    continue;
                }
                                   
            }

        }

    }
    /*
        입력값으로 받은 계좌번호가 acnums_list.txt파일에 존재하는지 확인하는 메소드
        만약 존재한다면 해당 계좌주의 이름_아이디 return
        존재하지 않는다면 "No result"문자열 리턴
     */
    public static String matchingAcnum(String account){
        try{
            File file = new File("acnums_list.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                StringTokenizer st=new StringTokenizer(line," ");
                String name_id=st.nextToken();
                while(st.hasMoreTokens()){
                    if (account.equals(st.nextToken())){
                        return name_id;
                    }
                }
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
        return "No result";
    }
    /*
        입력 받은 문자열이 문법형식과, 의미규칙을 충족하는 지 확인하는 메소드
     */
    public static String filterInput(){
        int hypenCount=-1;
        System.out.print("입력 > ");
        Scanner sc=new Scanner(System.in);
        String input=sc.nextLine();
        String acnum=input.trim().replaceAll("[ ]","");
        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd");
        if((hypenCount=isHypenError(acnum))==-1){
            System.out.println("올바른 계좌번호 형식이 아닙니다.");
            return filterInput();
        }else if (hypenCount==0){//하이픈이 0개 존재하는 경우
            //문법 형식 확인
            if(acnum.length()==12&&isDigitAccount(acnum)&&acnum.charAt(0)!='0'){
                //의미 규칙 확인
                if (checkDate(acnum.substring(4))){
                    StringBuffer sb=new StringBuffer(acnum);
                    return matchingAcnum(sb.insert(4,"-").toString());
                }else{
                    System.out.println("계좌 번호의 뒤 8자리는 계좌 생션 년도, 월, 일로 구성되어 있습니다.");
                    return filterInput();
                }
            }else{
                System.out.println("올바른 계좌번호 형식이 아닙니다.");
                return filterInput();
            }
        }else{//하이픈이 1개 존재하는 경우
            try {
                StringTokenizer st = new StringTokenizer(acnum, "-");
                String preAcnum = st.nextToken();
                String postAcnum = st.nextToken();
                //문법형식 확인
                if (preAcnum.length()==4&&isDigitAccount(preAcnum)&&postAcnum.length()==8&&isDigitAccount(postAcnum)&&preAcnum.charAt(0)!='0'){
                    //의미규칙 확인
                    if (checkDate(postAcnum)){
                        return matchingAcnum(acnum);
                    }else{
                        System.out.println("계좌 번호의 뒤 8자리는 계좌 생션 년도, 월, 일로 구성되어 있습니다.");
                        return filterInput();
                    }
                }else{
                    System.out.println("올바른 계좌번호 형식이 아닙니다.");
                    return filterInput();
                }
            }catch (Exception e){
                System.out.println("올바른 계좌번호 형식이 아닙니다.");
                return filterInput();
            }
        }
    }

    /*
        문자열에 hypen('-')이 0개 또는 한개만 있는 지 확인하는 메소드, 하이픈이 0개 이면 0 리턴, 1개이면 1 리턴, error 라면 -1리턴
     */
    public static int isHypenError (String str){

        int count=0;
        for (int i=0 ;i<str.length();i++) {
            if('-'==(str.charAt(i))) {
                count += count + 1;
            }
        }
        if(count==0)
            return 0;

        if (count ==1)
            return 1;

        return -1;
    }
    /*
        계좌번호 문자열이 숫자로만 구성되어있는 지 확인하는 메소드, 숫자들로만 구성되어 있으면 true 리턴 숫자가 아닌 다른 문자가 포함되어 있으면 false 리턴
     */
    public static boolean isDigitAccount(String str){
        char tmp;
        for( int i=0;i<str.length();i++){
            tmp=str.charAt(i);
            if(!Character.isDigit(tmp)){
                return false;
            }
        }
        return true;
    }
    /*
        날짜 형식인지 체크하는 메소드, 날짜 형식이라면 true 리턴, 날짜 형식이 아니라면 false 리턴
     */
    public static boolean checkDate(String date) {
        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd");
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}