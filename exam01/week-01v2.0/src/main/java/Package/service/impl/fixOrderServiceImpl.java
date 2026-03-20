package Package.service.impl;

import Package.mapper.UserMapper;
import Package.mapper.fixOrderMapper;
import Package.pojo.Order;
import Package.service.fixOrderService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class fixOrderServiceImpl implements fixOrderService {

    private Scanner sc=new Scanner(System.in);
    /*
     * 没有依赖注入，便自己new一个UserMapper对象
     *
     * */
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
    SqlSession sqls=sqlSessionFactory.openSession(true);
    // 获取userMapper接口的代理实现类
    UserMapper um=sqls.getMapper(UserMapper.class);
    // 获取fixOrderMapper接口的代理实现类
    fixOrderMapper fom=sqls.getMapper(fixOrderMapper.class);
    @Override
    public void addOrder(String number) {
        System.out.println("请输入您的报修详情：");
        String detail = sc.next();
        int id=um.selectIdByNumber(number);
        String room=um.selectRoomByNumber(number);
        LocalDateTime last_time = LocalDateTime.now();
        fom.addOrder(detail,"未处理",id,room,number,last_time);
        System.out.println("申请报修成功！");
    }

    @Override
    public void searchStudentOrder(String number) {
        System.out.println("===== 查看我的报修记录 =====");
        int id=um.selectIdByNumber(number);
        List<Order> orders = fom.selectOrderByF_id(id);
        if(orders.size()==0) {
            System.out.println("您还未报修过任何单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单号："+order.getId());
                System.out.println("报修详情："+order.getDetail());
                System.out.println("报修状态："+order.getStatus());
                System.out.println("评价："+order.getEvaluation());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("-----------------");
            }
        }
    }

    @Override
    public void delOrder(String number) {
        Scanner sc=new Scanner(System.in);
        searchStudentOrder(number);
        System.out.println("请输入要取消的报修单号：");
        int id=sc.nextInt();
        fom.delOrder(id);
        System.out.println("取消报修成功！");
    }

    @Override
    public void evaluationOrder(String number) {
        searchStudentOrder(number);
        System.out.println("请输入要评价的报修单号：");
        int id=sc.nextInt();
        System.out.println("请输入您的评价：");
        String evaluation = sc.next();
        fom.updateEvaluation(id,evaluation);
        System.out.println("评价成功！");
    }

    @Override
    public void selectAllOrders() {
        System.out.println("===== 查看所有报修单 =====");
        List<Order> orders = fom.selectAllOrders();
        if(orders.isEmpty()) {
            System.out.println("暂无报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单号："+order.getId());
                System.out.println("报修详情："+order.getDetail());
                System.out.println("报修状态："+order.getStatus());
                System.out.println("评价："+order.getEvaluation());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("-----------------");
            }
        }
    }

    @Override
    public void selectProcessedOrders() {
        System.out.println("===== 查看已处理报修单 =====");
        List<Order> orders = fom.selectProcessedOrders();
        if(orders.isEmpty()) {
            System.out.println("暂无已处理报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单号："+order.getId());
                System.out.println("报修详情："+order.getDetail());
                System.out.println("报修状态："+order.getStatus());
                System.out.println("评价："+order.getEvaluation());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("-----------------");
            }
        }
    }

    @Override
    public void selectUnprocessedOrders() {
        System.out.println("===== 查看未处理报修单 =====");
        List<Order> orders = fom.selectUnprocessedOrders();
        if(orders.isEmpty()) {
            System.out.println("暂无未处理报修单");
        } else {
            for (Order order : orders) {
                System.out.println("报修单号："+order.getId());
                System.out.println("报修详情："+order.getDetail());
                System.out.println("报修状态："+order.getStatus());
                System.out.println("评价："+order.getEvaluation());
                System.out.println("更新时间："+order.getLast_time());
                System.out.println("-----------------");
            }
        }

    }

    @Override
    public void updateOrderStatus() {
        Scanner sc=new Scanner(System.in);
        selectAllOrders();
        System.out.println("请输入要更新的报修单ID：");
        int id=sc.nextInt();
        System.out.println("请输入新的报修单状态（已处理/未处理）：");
        String status=sc.next();
        LocalDateTime last_time=LocalDateTime.now();
        um.updateOrderStatus(id,status,last_time);
        System.out.println("报修单状态更新成功！");

    }

    @Override
    public void delOrderById() {
        Scanner sc=new Scanner(System.in);
        selectAllOrders();
        System.out.println("请输入要删除的报修单ID：");
        int id=sc.nextInt();
        while (!fom.selectOrderById(id)){
            System.out.println("该报修单不存在！请重新输入");
            System.out.println("请输入要删除的报修单ID：");
            id=sc.nextInt();
        }
        fom.delOrder(id);
        System.out.println("报修单删除成功！");
    }
}
