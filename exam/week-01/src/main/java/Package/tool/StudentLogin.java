package Package.tool;

import Package.mapper.userMapper;
import Package.pojo.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class StudentLogin {

    // 加载mybatis配置文件 创建sqlSessionFactory
    String resource = "mybatis.xml";
    InputStream inputStream;
    {
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    // 从sqlSessionFactory中获取sqlSession
    SqlSession sqls=sqlSessionFactory.openSession();
    // 获取userMapper接口的代理实现类
    userMapper um=sqls.getMapper(userMapper.class);

    public StudentLogin()  {

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您的学号：");
        String number = sc.nextLine();
        System.out.println("请输入您的密码：");
        String password = sc.nextLine();

        while (um.studentLogin(number,password)!=1){
            System.out.println("登录失败！请重新输入");
            System.out.println("请输入您的学号：");
            number = sc.nextLine();
            System.out.println("请输入您的密码：");
            password = sc.nextLine();
        }
        //登录成功
        System.out.println("登录成功！角色：学生");
        sframe(number);



    }

    private void sframe(String number) {
        // 登录成功后，进入学生功能界面
        Scanner sc=new Scanner(System.in);
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
                    addOrSetDormitory(number);
                    break;
                case 2:
                    // 创建报修单
                    addOrder(number);
                    break;
                case 3:
                    // 查看我的报修记录
                    searchOrder(number);
                    break;
                case 4:
                    // 取消报修单
                    delOrder(number);
                    break;
                case 5:
                    // 修改密码
                    setPassword(number);
                    break;
                case 6:
                    // 评价报修单
                    evaluationOrder(number);
                    break;
                case 7:
                    // 退出
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        } while (choice != 7);
    }

    private void evaluationOrder(String number) {
        Scanner sc=new Scanner(System.in);
        searchOrder(number);
        System.out.println("请输入您要评价的报修单的详情：");
        String detail = sc.next();
        System.out.println("请输入您的评价：");
        String evaluation = sc.next();
        um.updateEvaluation(detail,evaluation);
        sqls.commit();
        System.out.println("评价报修成功！");
    }

    private void setPassword(String number) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您的新密码：");
        String newPassword = sc.next();
        System.out.println("请确认新密码：");
        String confirmPassword = sc.next();
        while(!newPassword.equals(confirmPassword)){
            System.out.println("两次密码输入不一致，请重新输入");
            System.out.println("请输入您的新密码：");
            newPassword = sc.next();
            System.out.println("请确认新密码：");
            confirmPassword = sc.next();
        }
        um.updatePassword(number,newPassword);
        sqls.commit();
        System.out.println("修改密码成功！");
    }

    private void delOrder(String number) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您要取消的报修单的：");
        searchOrder(number);
        System.out.println("请输入您要取消的报修单的详情：");
        String detail = sc.next();
        int id=um.selectIdByNumber(number);
        um.delOrder(detail,id);
        sqls.commit();
        System.out.println("取消报修成功！");
    }

    private void searchOrder(String number) {
        System.out.println("===== 查看我的报修记录 =====");
        int id=um.selectIdByNumber(number);
        List<Order> orders = um.selectOrderByF_id(id);
        if(orders==null) {
            System.out.println("您还未报修过任何单");
        } else {
            for (Order order : orders) {
                System.out.println("报修详情："+order.getDetail());
                System.out.println("报修状态："+order.getStatus());
                System.out.println("评价："+order.getEvaluation());
                System.out.println("更新时间："+order.getLast_time());
            }
        }

    }

    private void addOrder(String number) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您的报修详情：");
        String detail = sc.next();
        int id=um.selectIdByNumber(number);
        String room=um.selectDormitoryByNumber(number);
        LocalDateTime last_time = LocalDateTime.now();
        um.addOrder(detail,"未处理",id,room,number,last_time);
        sqls.commit();
        System.out.println("申请报修成功！");

    }

    private void addOrSetDormitory(String number) {
        int choice;
        Scanner sc=new Scanner(System.in);
        System.out.println("===== 绑定/修改宿舍 =====");
        if(um.selectDormitoryByNumber(number)==null) {
            System.out.println("您还未绑定宿舍，请先绑定宿舍");
            System.out.println("请输入您的宿舍号：");
            String dormitory = sc.next();
            um.updateDormitory(number,dormitory);
            sqls.commit();
            System.out.println("修改宿舍成功！");
        } else  {
            System.out.println("原宿舍号是："+um.selectDormitoryByNumber(number)+",您修改为：");
            String dormitory = sc.next();
            um.updateDormitory(number,dormitory);
            sqls.commit();
            System.out.println("修改宿舍成功！");
        }
    }
}
