package Package;

import Package.service.UserService;
import Package.service.fixOrderService;
import Package.service.impl.UserServiceImpl;
import Package.service.impl.fixOrderServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainEntry {

    //创建UserService和fixOrderService的实例
     static UserService us=new UserServiceImpl();
     static fixOrderService fos=new fixOrderServiceImpl();

    static void main() throws IOException {

        // 加载mybatis配置文件 创建sqlSessionFactory
        String resource = "mybatis.xml";
        InputStream inputStream=Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 从sqlSessionFactory中获取sqlSession
        SqlSession sqls=sqlSessionFactory.openSession(true);
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
                    Register();break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("请输入正确的选项");
            }
        }while (choice!=3);
    }

    private static void Register() {
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
                //进入学生注册界面
                System.out.println("请输入学号（前缀3125或3225，共10位数字）：");
                String number=sc.next();
                while(!number.startsWith("3125")&&!number.startsWith("3225")
                        ||!number.matches("\\d{10}")
                        || us.selectStudentByNumber(number)!=0){
                    System.out.println("请输入正确的学号格式或该学号已被注册");
                    System.out.println("请重新输入:");
                    number=sc.next();//重新获取用户输入的学号
                }
                System.out.println("请输入密码（至少一个数字和一个字母，长度不小于6位）：");
                String password=sc.next();
                while(!password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$")){
                    System.out.println("格式错误，请输入至少一个数字和一个字母，长度不小于6位的密码");
                    password=sc.next();//重新获取用户输入的密码
                }
                System.out.println("请确认密码：");
                String confirmPassword=sc.next();
                while(!confirmPassword.equals(password)){
                    System.out.println("两次密码不一致");
                    System.out.println("请确认密码：");
                    confirmPassword=sc.next();//重新获取用户输入的确认密码
                }
                us.registerStudent(number,password);
                System.out.println("注册成功！请返回主界面登录。");
                break;
            case 2:
                //进入维修人员注册界面
                System.out.println("请输入维修人员学号（前缀为0025，共10位数字）：");
                String adminNumber=sc.next();
                while(!adminNumber.startsWith("0025")||!adminNumber.matches("\\d{10}")||
                        us.selectAdminByNumber(adminNumber)!=0){
                    System.out.println("请输入正确的学号格式或该学号已被注册");
                    System.out.println("请重新输入:");
                    //重新获取用户输入的学号
                    adminNumber=sc.next();
                }
                System.out.println("请输入密码（至少一个数字和一个字母，长度不小于6位）：");
                String adminPassword=sc.next();
                while(!adminPassword.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$")){
                    System.out.println("格式错误，请输入至少一个数字和一个字母，长度不小于6位的密码");
                    adminPassword=sc.next();//重新获取用户输入的密码
                }
                System.out.println("请确认密码：");
                String adminConfirmPassword=sc.next();
                while(!adminConfirmPassword.equals(adminPassword)){
                    System.out.println("两次密码不一致");
                    System.out.println("请确认密码：");
                    //重新获取用户输入的确认密码
                    adminConfirmPassword=sc.next();
                }
                us.registerAdmin(adminNumber,adminPassword);
                System.out.println("注册成功！请返回主界面登录。");
                break;
        }
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
                StudentLogin();
                break;
            case 2:
                //进入维修人员登录界面
                AdminLogin();
                break;
        }
    }

    private static void AdminLogin() {
        Scanner sc=new Scanner(System.in);

        System.out.println("请输入您的工号：");
        String number = sc.next();
        System.out.println("请输入您的密码：");
        String password = sc.next();

        //验证用户登录信息
        while(!us.adminlogin(number,password)){
            System.out.println("登录失败！请重新输入");
            System.out.println("请输入您的工号：");
            number = sc.next();
            System.out.println("请输入您的密码：");
            password = sc.next();
        }
        System.out.println("登录成功！角色：管理员");

        //进入管理员操作界面
        int choice;
        do{
            System.out.println("===== 管理员菜单 =====");
            System.out.println("1. 查看所有报修单\n"+
                    "2. 更新报修单状态\n" +
                    "3. 删除报修单\n" +
                    "4. 修改密码\n" +
                    "5. 退出\n" +
                    "请选择操作（输入 1-6）：");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // 查看所有报修单
                    System.out.println("请您选择查看所有报修单、查看已处理报修单或查看未处理" +
                            "报修单（分别输入1、2、3）：");
                    int choice2=sc.nextInt();
                    while (choice2!=1&&choice2!=2&&choice2!=3) {
                        System.out.println("输入错误！请重新输入");
                        choice2=sc.nextInt();
                    }
                    if(choice2==1) fos.selectAllOrders();
                    else if(choice2==2) fos.selectProcessedOrders();
                    else if(choice2==3) fos.selectUnprocessedOrders();
                    break;
                case 2:
                    // 更新报修单状态
                    fos.updateOrderStatus();
                    break;
                case 3:
                    // 删除报修单
                    fos.delOrderById();
                    break;
                case 4:
                    // 修改密码
                    us.setAdminPassword(number);
                    break;
                case 5:
                    // 退出
                    System.exit(0);
                default:
                    System.out.println("请输入正确的选项");
            }
        }while (true);

    }





    private static void StudentLogin() {
        Scanner sc=new Scanner(System.in);

        System.out.println("请输入您的学号：");
        String number = sc.next();
        System.out.println("请输入您的密码：");
        String password = sc.next();

        //验证用户登录信息
        while(!us.studentlogin(number,password)){
            System.out.println("登录失败！请重新输入");
            System.out.println("请输入您的学号：");
            number = sc.next();
            System.out.println("请输入您的密码：");
            password = sc.next();
        }
        System.out.println("登录成功！角色：学生");

        //进入学生主界面
        int choice;
        do {
            System.out.println("===== 学生菜单 =====");
            System.out.println("1. 绑定/修改宿舍\n"+
                    "2. 创建报修单\n" +
                    "3. 查看我的报修记录\n" +
                    "4. 取消报修单\n" +
                    "5. 修改密码\n" +
                    "6. 评价报修单\n" +
                    "7. 退出\n" +
                    "请选择操作（输入 1-7）：");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // 绑定/修改宿舍
                    us.addOrSetRoom(number);
                    break;
                case 2:
                    // 创建报修单
                    fos.addOrder(number);
                    break;
                case 3:
                    // 查看我的报修记录
                    fos.searchStudentOrder(number);
                    break;
                case 4:
                    // 取消报修单
                    fos.delOrder(number);
                    break;
                case 5:
                    // 修改密码
                    us.setStudentPassword(number);
                    break;
                case 6:
                    // 评价报修单
                    fos.evaluationOrder(number);
                    break;
                case 7:
                    // 退出
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        } while (true);
    }
}
