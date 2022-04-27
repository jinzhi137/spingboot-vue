package com.bysj.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.springboot.common.Result;
import com.bysj.springboot.entity.FxyysQz;
import com.bysj.springboot.mapper.FxyysQzMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/fxyysQz")
public class FxyysQzController {
    @Resource
    FxyysQzMapper fxyysQzMapper;
    @GetMapping()
    public Result<?> getQz(@RequestParam(defaultValue = "") String fxyysId){
        LambdaQueryWrapper<FxyysQz> wrapper= Wrappers.<FxyysQz>lambdaQuery();
        wrapper.like(FxyysQz::getFxyysId,fxyysId);
        List<FxyysQz> hhh=fxyysQzMapper.selectList(wrapper);
        return Result.success(hhh);

    }
    @PostMapping()
    public Result<?> update(@RequestBody FxyysQz fxyysQz){
        LambdaQueryWrapper<FxyysQz> wrapper= Wrappers.<FxyysQz>lambdaQuery();
        wrapper.eq(FxyysQz::getFxyysId,fxyysQz.getFxyysId());
        fxyysQzMapper.update(fxyysQz,wrapper);
        return Result.success();
    }
}
