package org.example.week02.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private T data;
    private String msg;
    private String code;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, "success", "200");
    }

    public static <T> Result<T> error(String msg, String code) {
        return new Result<>(null, msg, code);
    }


}
