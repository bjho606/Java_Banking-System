import java.util.Scanner;

// 검색 종류 선택 메뉴
public class SearchTypeSelectMenu {
    /*
        main 메소드
     */
    public static void main() {
        Scanner sc=new Scanner(System.in);
        int menuSelect=-1;
        System.out.println("[검색 종류 선택 메뉴]");
        System.out.println("0. 메인 메뉴로 돌아가기");
        System.out.println("1. 계좌주 검색하기");
        System.out.println("2. 계좌번호 검색하기");
        while(true) {
            System.out.print("입력 > ");
            
            if ((menuSelect=sc.nextInt())==0){
                System.out.println("메뉴로 돌아가기");//메뉴로 돌아가는 메소드 사용
                User.mainMenu();
                break;
            }else if (menuSelect==1){
				SearchAcholderName.searchAcholderName(); 
                break;
            }else if(menuSelect==2){
                SearchAcnum.main();
                break;
            }else {
                System.out.println("0이상 2이하의 숫자로 입력해주세요.");
                continue;
            }
            

        }
    }
}