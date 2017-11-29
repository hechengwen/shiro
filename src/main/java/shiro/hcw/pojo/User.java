package shiro.hcw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 13:34
 * Description:
 * Others:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    private String userId;
    private String userName;
    private String password;
    private String mobile;
    private String realName;
    private String email;
    private String company;
    private String address;
    private String idCard;
    private String sex;
    private String createTime;
}
