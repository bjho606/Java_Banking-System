import java.util.Scanner;

// �˻� ���� ���� �޴�
public class SearchTypeSelectMenu {
    /*
        main �޼ҵ�
     */
    public static void main() {
        Scanner sc=new Scanner(System.in);
        int menuSelect=-1;
        System.out.println("[�˻� ���� ���� �޴�]");
        System.out.println("0. ���� �޴��� ���ư���");
        System.out.println("1. ������ �˻��ϱ�");
        System.out.println("2. ���¹�ȣ �˻��ϱ�");
        while(true) {
            System.out.print("�Է� > ");
            
            if ((menuSelect=sc.nextInt())==0){
                System.out.println("�޴��� ���ư���");//�޴��� ���ư��� �޼ҵ� ���
                User.mainMenu();
                break;
            }else if (menuSelect==1){
				SearchAcholderName.searchAcholderName(); 
                break;
            }else if(menuSelect==2){
                SearchAcnum.main();
                break;
            }else {
                System.out.println("0�̻� 2������ ���ڷ� �Է����ּ���.");
                continue;
            }
            

        }
    }
}