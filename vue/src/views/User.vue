<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="checkall">查看全部</el-button>
      <el-button type="primary" @click="checkadmin">查看管理员</el-button>
      <el-upload
          action="/api/user/import"
          :on-success="handleUploadSuccess"
          :show-file-list=false
          accept='.xlsx'
          style="display: inline-block; margin: 0 10px"
      >
        <el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="Export">导出</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="username" style="width: 20%" placeholder="请输入查询内容" clearable />
      <el-button type="primary" @click="load" style="margin-left: 5px"><el-icon ><search /></el-icon>查询</el-button>
    </div>
    <el-dialog v-model="dialogVisible" width="30%">
      <el-form :model="Form">
        用户名
        <el-input v-model="Form.username" style="width: 60%;" disabled placeholder="请输入用户名"></el-input><br><br>
        密码
        <el-input v-model="Form.password" style="width: 60%;" show-password placeholder="请输入密码"></el-input><br><br>
        权限
        <el-input v-model="Form.role" style="width: 60%;" placeholder="请输入权限"></el-input><br><br>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </el-form>
    </el-dialog>
    <el-table :data="tableData"
              border
              style="width: auto"
              stripe>
      <el-table-column
          prop="username"
          label="账户名"
          style="width: auto"/>
      <el-table-column label="密码">
        <template #default="scope">
          <span v-if='scope.row.role!==0'>管理员密码不予显示</span>
          <span v-else>{{scope.row.password}}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限">
        <template #default="scope">
          <span v-if='scope.row.role===1'>管理员</span>
          <span v-if='scope.row.role===0'>普通用户</span>
          <span v-if='scope.row.role===2'>超级管理员</span>
        </template>
      </el-table-column>
      <el-table-column align="left">
        <template #header  >
          操作
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认要删除吗"  @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button
                  class="buttonDiv"
                  size="small"
                  type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
<!--    页码-->
    <div style="margin: 10px 0">
      <div class="demo-pagination-block">
        <el-pagination
            v-model:currentPage="currentPage"
            :page-sizes="[5, 10, 20, 100]"
            :page-size=pageSize
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total=total
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return{
      user:{},
      username:"",
      password:"请在编辑后查看",
      role:10,
      tableData:[],
      currentPage:1,
      pageSize:5,
      total:100,
      dialogVisible:false,
      Form:{},
    }
  },
  created() {
    this.load()
    let userStr = sessionStorage.getItem("user") || "{}"
    if(this.user.role===null){
      this.user.role=0
    }
    this.user = JSON.parse(userStr)
    if(this.user!=={}){
      // 请求服务端，确认当前登录用户的合法信息
      request.get("/user/" + this.user.username).then(res => {
        if (res.code === '0') {
          this.user = res.data
        }
      })
    }
  },
  methods:{
    //加载数据
    load(){
      request.get("/user",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          username:this.username,
          role:this.role
        }
      }).then(res =>{
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    //导出
    Export(){
      location.href = "http://localhost:8081/user/export";
    },
    //导入
    handleUploadSuccess(res){
      if (res.code === "0") {
        this.$message.success("导入成功")
        this.load()
      }
      else {
        this.$message.error("导入失败，请检查Excel是否符合和规范")
      }
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()
    },
    //删除某一行
    handleDelete(row){
      if(this.user.role<=row.role){
        this.$message({
          type:"warning",
          message:"不能删除管理员"
        })
        return
      }
      request.delete("/user/"+row.username).then(res =>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else{
          this.$message({
            type:"error",
            message: res.msg
          })
        }
        this.load()
      })
    },
    checkall(){
      this.role=10
      this.load()
    },
    checkadmin(){
      this.role=1
      this.load()
    },
    handleEdit(row){
      this.Form=JSON.parse(JSON.stringify(row))
      if(this.user.role<=this.Form.role){
        this.$message({
          type:"warning",
          message:"不可以查看管理员信息"
        })
        return
      }
      this.dialogVisible=true
    },
    save(){
      this.dialogVisible=false
      if(this.Form.role>=2){
        this.$message({
          type:"warning",
          message:"只能有一个超级管理员"
        })
        return
      }
      request.post("/user",this.Form).then(res =>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"修改成功"
          })
        }else{
          this.$message({
            type:"error",
            message: res.msg
          })
        }
        this.load()
      })
    }
  }
}
</script>

<style scoped>

</style>