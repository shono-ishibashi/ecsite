package com.ecsite.demo.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class OrderConfirmForm {

    private Integer orderId;

    @NotBlank(message = "名前を入力して下さい")
    private String name;

    @NotBlank(message = "メールアドレスを入力して下さい")
    @Email(message = "メールアドレスの形式が不正です")
    private String email;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$",message = "郵便番号はXXX-XXXXの形式で入力してください")
    private String zipcode;

    @NotBlank(message = "住所を入力して下さい")
    private String address;

    @NotBlank(message = "電話番号を入力して下さい")
    @Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}" , message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
    private String telephone;

    @NotNull(message = "配達日程を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;

    @NotNull(message = "配達時刻を入力してください")
    private Integer deliveryTimeHour;

    @NotNull(message = "お支払い方法を入力してください")
    private Integer paymentMethod;
}
