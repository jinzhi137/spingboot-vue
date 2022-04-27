package com.bysj.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("user")
@Data

public class User {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Integer role;
}
