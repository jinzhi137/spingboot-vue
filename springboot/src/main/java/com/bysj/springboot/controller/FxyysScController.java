package com.bysj.springboot.controller;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.springboot.common.Result;
import com.bysj.springboot.entity.FxyysSc;

import com.bysj.springboot.mapper.FxyysScMapper;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/fxyysSc")
public class FxyysScController {
    @Resource
    FxyysScMapper fxyysScMapper;
//    分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "") String stuId,
                              @RequestParam(defaultValue = "") String enable){
        LambdaQueryWrapper<FxyysSc> wrapper= Wrappers.<FxyysSc>lambdaQuery();
        if(enable.equals("")){
            wrapper.like(FxyysSc::getStuId,stuId).like(FxyysSc::getEnable,enable);
        }else{
            wrapper.like(FxyysSc::getStuId,stuId).eq(FxyysSc::getEnable,enable);
        }

        Page<FxyysSc> stuTeaPage=fxyysScMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(stuTeaPage);
    }
//    上传
    @PostMapping()
    public Result<?> save(@RequestBody FxyysSc fxyysSc) {
        LambdaQueryWrapper<FxyysSc> wrapper= Wrappers.<FxyysSc>lambdaQuery();
        wrapper.eq(FxyysSc::getStuId,fxyysSc.getStuId()).eq(FxyysSc::getYear,fxyysSc.getYear()).eq(FxyysSc::getEnable,fxyysSc.getEnable());
        FxyysSc res= fxyysScMapper.selectOne(wrapper);
        if(res==null){
            fxyysScMapper.insert(fxyysSc);
            return Result.success("上传成功");
        }
        else{
            fxyysScMapper.update(fxyysSc,wrapper);
            return Result.success("更新成功");
        }
    }
//更新
//    @PutMapping()
//    public Result<?> sc(@RequestBody FxyysSc fxyysSc){
//        LambdaQueryWrapper<FxyysSc> wrapper= Wrappers.<FxyysSc>lambdaQuery();
//        wrapper.eq(FxyysSc::getStuId,fxyysSc.getStuId()).eq(FxyysSc::getYear,fxyysSc.getYear()).eq(FxyysSc::getEnable,fxyysSc.getEnable());
//        fxyysScMapper.update(fxyysSc,wrapper);
//        return Result.success();
//    }
//    删除
    @DeleteMapping("/{stuId}/{year}/{enable}")
    public Result<?> delete(@PathVariable String stuId,@PathVariable String year,@PathVariable String enable){
        LambdaQueryWrapper<FxyysSc> wrapper= Wrappers.<FxyysSc>lambdaQuery();
        wrapper.eq(FxyysSc::getStuId,stuId).eq(FxyysSc::getYear,year).eq(FxyysSc::getEnable,enable);
        FxyysSc res=fxyysScMapper.selectOne(wrapper);
        if(res!=null){
            fxyysScMapper.delete(wrapper);
        }
        else{
            return Result.error("-1","删除用户不存在");
        }
        return Result.success();
    }

//    excel导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<FxyysSc> all = fxyysScMapper.selectList(null);
        for (FxyysSc fxyysSc : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("学号", fxyysSc.getStuId());
            row1.put("申请年份", fxyysSc.getYear());
            row1.put("状态", fxyysSc.getEnable());
            row1.put("总分", fxyysSc.getTotal());
            row1.put("思想表现", fxyysSc.getApoint());
            row1.put("文明守纪", fxyysSc.getBpoint());
            row1.put("学习态度", fxyysSc.getCpoint());
            row1.put("社会工作", fxyysSc.getDpoint());
            row1.put("实践公益", fxyysSc.getEpoint());
            row1.put("团队精神", fxyysSc.getFpoint());
            row1.put("科研创新", fxyysSc.getGpoint());
            row1.put("文体素质", fxyysSc.getHpoint());
            row1.put("技能素质", fxyysSc.getIpoint());
            row1.put("特殊经历", fxyysSc.getGpoint());
            list.add(row1);
        }
        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("非学业因素信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);

    }

    //excel 导入
    @PostMapping("/import")
    public Result<?> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<FxyysSc> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            FxyysSc fxyysSc = new FxyysSc();
            fxyysSc.setStuId(row.get(0).toString());
            fxyysSc.setYear(row.get(1).toString());
            fxyysSc.setEnable(row.get(2).toString());
            fxyysSc.setTotal(Double.valueOf(row.get(3).toString()));
            fxyysSc.setApoint(Integer.valueOf(row.get(4).toString()));
            fxyysSc.setBpoint(Integer.valueOf(row.get(5).toString()));
            fxyysSc.setCpoint(Integer.valueOf(row.get(6).toString()));
            fxyysSc.setDpoint(Integer.valueOf(row.get(7).toString()));
            fxyysSc.setEpoint(Integer.valueOf(row.get(8).toString()));
            fxyysSc.setFpoint(Integer.valueOf(row.get(9).toString()));
            fxyysSc.setGpoint(Integer.valueOf(row.get(10).toString()));
            fxyysSc.setHpoint(Integer.valueOf(row.get(11).toString()));
            fxyysSc.setIpoint(Integer.valueOf(row.get(12).toString()));
            fxyysSc.setJpoint(Integer.valueOf(row.get(13).toString()));
            saveList.add(fxyysSc);
        }
        for (FxyysSc fxyysSc : saveList) {
            fxyysScMapper.insert(fxyysSc);
        }
        return Result.success();
    }
}
