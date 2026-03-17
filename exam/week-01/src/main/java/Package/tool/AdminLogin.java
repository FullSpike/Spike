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

public class AdminLogin {

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

    public AdminLogin(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您的工号：");
        String number = sc.next();
        System.out.println("请输入您的密码：");
        String password = sc.next();

        while (um.adminLogin(number,password)!=1){
            System.out.println("登录失败！请重新输入");
            System.out.println("请输入您的工号：");
            number = sc.next();
            System.out.println("请输入您的密码：");
            password = sc.next();
        }
        //登录成功
        System.out.println("登录成功！角色：管理员");
        aframe(number);


    }

    private void aframe(String number) {
        // 管理员操作界面
        System.out.println("===== 管理员菜单 =====");
        int choice;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("1. 查看所有报修单\n"+
                    "2. 更新报修单状态\n" +
                    "3. 删除报修单\n" +
                    "4. 修改密码\n" +
                    "5. 退出\n" +
                    "请选择操作（输入 1-6）：");
            choice = sc.nextInt();
            switch (choice){
                case 1:
                    // 查看所有报修单
                    System.out.println("请您选择查看所有报修单、查看已处理报修单或查看未处理报修单（分别输入1、2、3）：");
                    int choice2=sc.nextInt();
                    while (choice2!=1&&choice2!=2&&choice2!=3) {
                        System.out.println("输入错误！请重新输入");
                        choice2=sc.nextInt();
                    }
                    if(choice2==1) {
                        selectAllOrders();
                    } else if(choice2==2) {
                        selectProcessedOrders();
                    } else if(choice2==3) {
                        selectUnprocessedOrders();
                    }
                    break;
                case 2:
                    // 更新报修单状态
                    updateOrderStatus();
                    break;
                case 3:
                    // 删除报修单
                    delAdminOrder();
                    break;
                case 4:
                    // 修改密码
                    updateAdminPassword(number);
                    break;
                case 5:
                    // 退出
                    System.exit(0);
                    break;
            }
        }while(choice!=5);
    }

    private void selectUnprocessedOrders() {
        //查看未处理报修单
        List<Order> orders=um.selectUnprocessedOrders();
        System.out.println("===== 未处理报修单 =====");
        if(orders.size()==0) {
            System.out.println("暂无未处理报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单ID："+order.getId());
                System.out.println("报修单宿舍："+order.getRoom());
                System.out.println("保修学生学号："+order.getNumber());
                System.out.println("保修单详情："+order.getDetail());
                System.out.println("报修单状态："+order.getStatus());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("评价："+order.getEvaluation());
            }
        }
    }

    private void selectProcessedOrders() {
        // 查询已处理报修单
        List<Order> orders=um.selectProcessedOrders();
        System.out.println("===== 已处理报修单 =====");
        if(orders.size()==0) {
            System.out.println("暂无已处理报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单ID："+order.getId());
                System.out.println("报修单宿舍："+order.getRoom());
                System.out.println("保修学生学号："+order.getNumber());
                System.out.println("保修单详情："+order.getDetail());
                System.out.println("报修单状态："+order.getStatus());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("评价："+order.getEvaluation());
            }
        }
    }

    private void updateAdminPassword(String number) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入新密码：");
        String password=sc.next();
        System.out.println("请重新输入确认密码：");
        String confirmPassword=sc.next();
        while (!confirmPassword.equals(password)) {
            System.out.println("两次密码输入不一致！请重新输入");
            System.out.println("请输入新密码：");
            password=sc.next();
            System.out.println("请重新输入确认密码：");
            confirmPassword=sc.next();
        }
        um.updateAdminPassword(number,password);
        sqls.commit();
        System.out.println("密码更新成功！");
    }

    private void delAdminOrder() {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要删除的报修单ID：");
        int id=sc.nextInt();
        while (um.selectAdminOrderById(id)==null){
            System.out.println("该报修单不存在！请重新输入");
            System.out.println("请输入要删除的报修单ID：");
            id=sc.nextInt();
        }
        um.delAdminOrder(id);
        System.out.println("报修单删除成功！");
    }

    private void updateOrderStatus() {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要更新的报修单ID：");
        int id=sc.nextInt();
        System.out.println("请输入新的报修单状态（已处理/未处理）：");
        String status=sc.next();
        LocalDateTime last_time=LocalDateTime.now();
        um.updateOrderStatus(id,status,last_time);
        sqls.commit();
        System.out.println("报修单状态更新成功！");
    }

    private void selectAllOrders() {
        // 查看所有报修单
        List<Order> orders=um.selectOrderAll();
        System.out.println("===== 所有报修单 =====");
        if (orders.size()==0) {
            System.out.println("暂无报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单ID："+order.getId());
                System.out.println("报修单宿舍："+order.getRoom());
                System.out.println("保修学生学号："+order.getNumber());
                System.out.println("保修单详情："+order.getDetail());
                System.out.println("报修单状态："+order.getStatus());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("报修单评价："+order.getEvaluation());
                System.out.println("------------------------------");
            }
        }
    }

}
