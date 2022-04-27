package com.bysj.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fxyys_jjf")
@Data
public class FxyysJjf {
    private static final long serialVersionUID = 1L;

    private String fxyysJjfId;

    private String fxyysZf;

    private Integer jjfId;

    private Double fs;

    private String fxyysJjfInfo;

}
