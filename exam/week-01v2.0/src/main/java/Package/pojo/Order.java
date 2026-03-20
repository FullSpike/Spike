package Package.pojo;

import java.time.LocalDateTime;

public class Order {
    private Integer id;
    private String detail;
    private String status;
    private Integer f_id;
    private String evaluation;
    private String room;
    private String number;
    private LocalDateTime last_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getF_id() {
        return f_id;
    }

    public void setF_id(Integer f_id) {
        this.f_id = f_id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getLast_time() {
        return last_time;
    }

    public void setLast_time(LocalDateTime last_time) {
        this.last_time = last_time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", status='" + status + '\'' +
                ", f_id=" + f_id +
                ", evaluation='" + evaluation + '\'' +
                ", room='" + room + '\'' +
                ", number='" + number + '\'' +
                ", time='" + last_time + '\'' +
                '}';
    }
}
