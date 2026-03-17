package Package;

import Package.tool.AdminLogin;
import Package.tool.Register;
import Package.tool.StudentLogin;

import java.util.Scanner;

public class MainEntry {

    static void main() {
        Scanner sc=new Scanner(System.in);
        int choice;


        do {
            System.out.println("===========================");
            System.out.println("宿舍报修管理系统");
            System.out.println("===========================");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.退出");
            System.out.println("请输入您的选择：");
            choice=sc.nextInt();
            switch (choice){
                case 1:
                    Login();break;
                case 2:
                    new Register();break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("请输入正确的选项");
            }
        }while (choice!=3);
    }

    private static void Login() {
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择角色（1-学生，2-维修人员）：");
        int role=sc.nextInt();
        while(role!=1&&role!=2){
            System.out.println("请输入正确的角色");
            //重新获取用户输入的角色
            role=sc.nextInt();
        }
        switch (role){
            case 1:
                //进入学生登录界
                new StudentLogin();
                break;
            case 2:
                //进入维修人员登录界面
                new AdminLogin();
                break;
        }

    }
}
