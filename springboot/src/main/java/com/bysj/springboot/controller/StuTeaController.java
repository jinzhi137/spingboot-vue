package com.bysj.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.springboot.common.Result;
import com.bysj.springboot.entity.FxyysQz;
import com.bysj.springboot.entity.StuTea;
import com.bysj.springboot.mapper.StuTeaMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/stuTea")
public class StuTeaController {
    @Resource
    StuTeaMapper stuTeaMapper;
    @GetMapping("/get")
    public Result<?> getStuTea(@RequestParam(defaultValue = "") String Id){
        LambdaQueryWrapper<StuTea> wrapper=Wrappers.<StuTea>lambdaQuery();
        wrapper.like(StuTea::getId,Id);
        List<StuTea> all=stuTeaMapper.selectList(wrapper);
        return Result.success(all);
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody StuTea stuTea){
        LambdaQueryWrapper<StuTea> wrapper=Wrappers.<StuTea>lambdaQuery();
        wrapper.like(StuTea::getId,stuTea.getId());
        StuTea res=stuTeaMapper.selectOne(wrapper);
        if(res==null){
            stuTeaMapper.insert(stuTea);
            return Result.success("上传成功");
        }
        else{
            stuTeaMapper.update(stuTea,wrapper);
            return Result.success("修改成功");
        }
    }
}
