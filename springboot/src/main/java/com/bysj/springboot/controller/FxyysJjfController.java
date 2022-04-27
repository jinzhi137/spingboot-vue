package com.bysj.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bysj.springboot.common.Result;
import com.bysj.springboot.entity.FxyysJjf;
import com.bysj.springboot.entity.FxyysQz;
import com.bysj.springboot.entity.User;
import com.bysj.springboot.mapper.FxyysJjfMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fxyysJjf")
public class FxyysJjfController {
    @Resource
    FxyysJjfMapper fxyysJjfMapper;
    @GetMapping
    public Result<?> getJjf(@RequestParam(defaultValue = "") String fxyysJjfId,
                            @RequestParam(defaultValue = "") String fxyysZf){
        LambdaQueryWrapper<FxyysJjf> wrapper= Wrappers.<FxyysJjf>lambdaQuery();
        if(fxyysZf.equals("")){
            wrapper.like(FxyysJjf::getFxyysJjfId,fxyysJjfId).like(FxyysJjf::getFxyysZf,fxyysZf);
        }
        else{
            wrapper.like(FxyysJjf::getFxyysJjfId,fxyysJjfId).eq(FxyysJjf::getFxyysZf,fxyysZf);
        }

        List<FxyysJjf> hhh=fxyysJjfMapper.selectList(wrapper);
        return Result.success(hhh);
    }
    @PostMapping()
    public Result<?> insert(@RequestBody FxyysJjf fxyysJjf){
        LambdaQueryWrapper<FxyysJjf> wrapper= Wrappers.<FxyysJjf>lambdaQuery();
        wrapper.eq(FxyysJjf::getFxyysJjfId,fxyysJjf.getFxyysJjfId()).eq(FxyysJjf::getJjfId,fxyysJjf.getJjfId())
                .eq(FxyysJjf::getFxyysZf,fxyysJjf.getFxyysZf());
        if(fxyysJjfMapper.selectOne(wrapper)!=null){
            fxyysJjfMapper.update(fxyysJjf,wrapper);
            return Result.success("更新成功");
        }
        fxyysJjfMapper.insert(fxyysJjf);
        return Result.success("新增成功");
    }
    @DeleteMapping("/{FxyysJjfId}/{FxyysZf}/{JjfId}")
    public Result<?> deleteuser(@PathVariable String FxyysJjfId,
                                @PathVariable String FxyysZf,
                                @PathVariable String JjfId){
        LambdaQueryWrapper<FxyysJjf> wrapper= Wrappers.<FxyysJjf>lambdaQuery();
        wrapper.eq(FxyysJjf::getFxyysJjfId,FxyysJjfId).eq(FxyysJjf::getJjfId,JjfId)
                .eq(FxyysJjf::getFxyysZf,FxyysZf);
        fxyysJjfMapper.delete(wrapper);
        return Result.success();
    }

}
