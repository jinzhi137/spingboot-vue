<template>
  <div style="height: 100%;height: 100vh;background-color: darkslateblue;overflow: hidden">
    <div style="width: 300px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center;margin: 30px 0">欢迎注册</div>
      <el-form ref="loginForm" :model="loginForm" :rules="rules">
        <el-form-item  prop="username">
          <el-input prefix-icon="UserFilled" v-model="loginForm.username" placeholder="请输入用户名" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="lock" v-model="loginForm.password" placeholder="请输入密码"  show-password></el-input>
        </el-form-item>
        <el-form-item prop="checkPas">
          <el-input prefix-icon="checked" v-model="loginForm.checkPas" placeholder="请确认密码"  show-password></el-input>
        </el-form-item>
        <el-form-item >
          <el-button @click="register"   style="width: 100%">注册</el-button>
        </el-form-item>
        <el-form-item >
          <el-button @click="returnlogin" type="primary" style="width: 100%">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>


import request from "@/utils/request";

export default {
  name:"Register",
  data() {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'));
      } else if(value.length < 3){
        callback(new Error('长度在 3 到 15 个字符'));
      } else {
        callback();
      }
    }
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'));
      } else if(value.length < 3){
        callback(new Error('长度在 3 到 15 个字符'));
      } else {
        callback();
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.loginForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: '',
        password: '',
        checkPas:''
      },
      rules: {
        username:[
          { required: true, validator: validateUserName, trigger: 'blur'},
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPas: [
          {required: true, validator: validatePass2, trigger: 'blur'}
        ]
      },
      loading: false
    }
  },
  methods:{
    register(){
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          if(this.loginForm.username.length===9){
            this.loginForm.role=0
          }
          else{
            this.loginForm.role=1
          }
          request.post("/user/register",this.loginForm).then(res =>{
            if(res.code==='0'){
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")
            }
            else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
        else{
          this.$message({
            type:"error",
            message:"用户名或密码不符合规范"
          })
        }
      })
    },
    returnlogin(){
      this.$router.push("/login")
    }

  },


}
</script>
<style>


</style>