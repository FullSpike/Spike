package Package.service;

public interface UserService {
    boolean studentlogin(String number, String password);

    void addOrSetRoom(String number);

    void setStudentPassword(String number);

    void setAdminPassword(String number);

    Integer selectStudentByNumber(String number);

    void registerStudent(String number, String password);

    int selectAdminByNumber(String number);

    void registerAdmin(String number, String password);

    boolean adminlogin(String number, String password);
}
