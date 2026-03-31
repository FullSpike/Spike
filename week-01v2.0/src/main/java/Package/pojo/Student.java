package Package.pojo;

public class Student {
    private String number;
    private String room;
    private String password;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Student{" +
                "number='" + number + '\'' +
                ", room='" + room + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
