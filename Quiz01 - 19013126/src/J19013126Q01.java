import java.util.Scanner;

public class J19013126Q01 {
    public static void main(String[] args) {
        String num = "19013126";
        String name = "�ڱټ�";
        char grade;
        Scanner scanner = new Scanner(System.in);

        System.out.println("�й� : " + num);
        System.out.println("�̸� : " + name);
        System.out.println("������ �Է��ϼ���. (0 ~ 100) : ");

        int score = scanner.nextInt();

        if (score >= 95) grade = 'A';
        else if (score >= 75) grade = 'B';
        else if (score >= 60) grade = 'C';
        else if (score >= 40) grade = 'D';
        else grade = 'F';

        System.out.println("������ " + grade + "�Դϴ�.");
        
        scanner.close();
    }
}