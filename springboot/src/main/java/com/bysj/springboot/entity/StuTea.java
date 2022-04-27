package com.bysj.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stu_tea")
@Data
public class StuTea {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String phone;
    private String grade;
    private String maj;
    private String xy;
    private String level;
}
