<template>
  <div style="height: 100%;height: 100vh;background-color: darkslateblue;overflow: hidden">
    <div style="width: 300px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center;margin: 30px 0">欢迎登录</div>
      <el-form ref="loginForm" :model="loginForm" :rules="rules">
        <el-form-item  prop="username">
          <el-input prefix-icon="UserFilled" v-model="loginForm.username" placeholder="请输入用户名" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="lock" v-model="loginForm.password" placeholder="请输入密码"  show-password></el-input>
        </el-form-item>
        <el-form-item >
          <el-button @click="login"   style="width: 100%">登录</el-button>
        </el-form-item>
        <el-form-item >
          <el-button @click="regClick" type="primary" style="width: 100%">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>


import request from "@/utils/request";

export default {
  name:"Login",
  data() {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'));
      } else if(value.length < 3){
        callback(new Error('长度在 3 到 15 个字符'));
      } else {
        callback();
      }
    };
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'));
      } else if(value.length < 3){
        callback(new Error('长度在 3 到 15 个字符'));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: '',
        password: '',
      },
      rules: {
        username:[
          { required: true, validator: validateUserName, trigger: 'blur'},
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ]
      },
      loading: false
    }
  },
  created() {
    sessionStorage.removeItem("user")
  },
  methods:{
    login(){
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          request.post("/user/login",this.loginForm).then(res =>{
            if(res.code==='0'){
              this.$message({
                type:"success",
                message:"登陆成功"
              })
              let x=res.data
              x.password="不告诉你"
              x=JSON.stringify(x)
              sessionStorage.setItem("user",x)
              this.$router.push("/")
            }
            else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          }).catch(resp => {
            this.loading = false
            this.$message({type: "error", message: '用户名或密码错误！'})
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
    regClick() {
      this.$router.push('/register')
    }
  },



}
</script>
<style>


</style>