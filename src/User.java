import java.util.Scanner;

public class User {
	String name, id;
	Scanner scan = new Scanner(System.in);

	public User(String name, String id) {
		this.name = name;
		this.id = id;

		while(true) {
			System.out.println("[���Ͻô� �޴��� �����ϼ���]");
			System.out.println("0. �α׾ƿ�");
			System.out.println("1. ���� ����");
			System.out.println("2. ���� ����");
			System.out.print("�Է� > ");

			String mainMenu = scan.nextLine();
			int mainMenuNum;

			if(mainMenu.matches(MainFlow.regExpMain)) {
				mainMenuNum = Integer.parseInt(mainMenu);
				if(mainMenuNum == 0) {
					// �α׾ƿ�
					System.out.println("\n�α׾ƿ� �մϴ�.\n");
					break;
				} else if(mainMenuNum == 1) {
					// ���� ����
/* ------------------- ������� �Ͻø� �˴ϴ� -----------------------------------------*/

				} else if(mainMenuNum == 2) {
					// ���� ����
/* ------------------- ������� �Ͻø� �˴ϴ� -----------------------------------------*/
					SearchAcholderName searchAcholderName = new SearchAcholderName();
					searchAcholderName.searchAcholderName(null);

				} 
				System.out.println();

			} else {
				System.out.println("\n0 �̻� 2 ������ ���ڷ� �Է����ּ���.\n");
			}
		}
	}

	public void printCurrentUser() {
		System.out.println(name + " " + id);
	}

}