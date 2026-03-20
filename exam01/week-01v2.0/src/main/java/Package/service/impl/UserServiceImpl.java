package Package.service.impl;

import Package.mapper.UserMapper;
import Package.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class UserServiceImpl implements UserService {

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

    @Override
    public boolean studentlogin(String number, String password) {
        return um.studentLogin(number,password)==1;
    }

    @Override
    public void addOrSetRoom(String number) {
        if(um.selectRoomByNumber(number)==null) {
            System.out.println("您还未绑定宿舍，请先绑定宿舍");
        }else{
            System.out.println("原宿舍号是："+um.selectRoomByNumber(number)+",您修改为：");
            String room = sc.next();
            um.updateRoom(number,room);
            System.out.println("修改宿舍成功！");
        }
    }

    @Override
    public void setStudentPassword(String number) {
        System.out.println("请输入您的新密码：");
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
        um.updatePassword(number,password);
        System.out.println("修改密码成功！");
    }

    @Override
    public void setAdminPassword(String number) {
        System.out.println("请输入您的新密码：");
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
        um.updateAdminPassword(number,adminPassword);
        System.out.println("修改密码成功！");
    }

    @Override
    public Integer selectStudentByNumber(String number) {
        return um.selectByNumber(number);
    }

    @Override
    public void registerStudent(String number, String password) {
        um.registerStudent(number,password);
    }

    @Override
    public int selectAdminByNumber(String number) {
        return um.selectAdminByNumber(number);
    }

    @Override
    public void registerAdmin(String number, String password) {
        um.registerAdmin(number,password);
    }

    @Override
    public boolean adminlogin(String number, String password) {
        return um.adminLogin(number,password)==1;
    }


}
