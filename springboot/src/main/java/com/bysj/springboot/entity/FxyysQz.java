package com.bysj.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fxyys_qz")
@Data
public class FxyysQz {
    private static final long serialVersionUID = 1L;

    private String fxyysId;

    private String fxyysIdInfo;

    private Double qz;

    private Double jbf;
}
