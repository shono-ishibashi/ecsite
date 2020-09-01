package com.ecsite.demo.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserForm {
    @NotBlank(message = "名前を入力してください")
    private String name;

    @NotBlank(message = "Ｅメールアドレスを入力してください")
    @Email(message = "Ｅメールアドレスを入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min=8, max=16, message = "パスワードは8文字以上16文字以下で入力してください")
    private String password;

    @NotBlank(message = "確認用パスワードを入力してください")
    private String confirmationPassword;

    @NotBlank(message = "郵便番号を入力してください")
    @Pattern(message = "郵便番号はXXX-XXXの形式で入力してください", regexp = "[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")
    private String zipcode;

    @NotBlank(message = "住所を入力してください")
    private String address;

    @NotBlank(message = "電話番号を入力してください")
    @Pattern(message = "有効な電話番号をXXX-XXX-XXXXの形式で入力してください", regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}")
    private String telephone;
}
