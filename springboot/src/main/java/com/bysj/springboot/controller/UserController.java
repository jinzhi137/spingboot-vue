package com.bysj.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.springboot.common.Result;
import com.bysj.springboot.entity.*;

import com.bysj.springboot.mapper.StuTeaMapper;
import com.bysj.springboot.mapper.UserMapper;


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
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;
    @Resource
    StuTeaMapper stuTeaMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if(res==null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res1=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        StuTea res2=stuTeaMapper.selectOne(Wrappers.<StuTea>lambdaQuery().eq(StuTea::getId,user.getUsername()));
        if(res1!=null){
            return Result.error("-1","该用户名已注册");
        }
        if(res2==null){
            return Result.error("-1","该用户名不在本校学生或老师内");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping
    public Result<?> update(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper= Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getUsername,user.getUsername());
        User res1=userMapper.selectOne(wrapper);
        if(res1==null){
            return Result.error("-1","该用户名不存在");
        }
        userMapper.update(user,wrapper);
        return Result.success("修改成功");
    }
    //根据id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable String id) {
        return Result.success(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,id)));
    }
    //分页查询，可根据用户名和权限查询
    @GetMapping
    public  Result<?> getall(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "10") Integer role){

        LambdaQueryWrapper<User> wrapper= Wrappers.<User>lambdaQuery();
        if(role.equals(10)){
            wrapper.like(User::getUsername,username);
        }
        else {
            wrapper.like(User::getUsername,username).eq(User::getRole,role);
            wrapper.or().eq(User::getRole,2);
        }
        Page<User> userPage=userMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(userPage);
    }
    //删除用户
    @DeleteMapping("/{username}")
    public Result<?> deleteuser(@PathVariable String username){
        LambdaQueryWrapper<User> wrapper= Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getUsername,username);
        User res=userMapper.selectOne(wrapper);
        if(res!=null){
            userMapper.delete(wrapper);
        }
        else{
            return Result.error("-1","删除用户不存在");
        }
        return Result.success();
    }
    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<User> all = userMapper.selectList(null);
        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", user.getUsername());
            row1.put("密码", user.getPassword());
            row1.put("权限", user.getRole());
            list.add(row1);
        }
        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户登录信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
    //导入
    @PostMapping("/import")
    public Result<?> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setRole(Integer.valueOf(row.get(2).toString()));
            saveList.add(user);
        }
        for (User user : saveList) {
            userMapper.insert(user);
        }
        return Result.success();
    }




}
