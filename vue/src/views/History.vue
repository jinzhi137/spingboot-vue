<template>
  <div style="padding: 10px">
    <div style="padding: 10px">
      <el-button type="primary" @click="checkpass">查看已通过</el-button>
      <el-button type="primary" @click="checkall">查看全部</el-button>
      <el-dialog v-model="dialogVisible_add" title="提交申请" width="40%">
        <el-form :model="subForm"   >
          学号
          <span>{{subForm.stuId}}</span>
          <br><br>
          申请年份
          <span>{{subForm.year}}</span>
          <br><br>
          状态
          <span v-if='subForm.enable==="1"'>通过</span>
          <span v-if='subForm.enable==="0"'>审核中</span>
          <span v-if='subForm.enable==="-1"'>未通过</span>
          <br><br>
          <el-collapse>
            <el-collapse-item title="思想表现" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.ainfo" v-for="(item1,key) in AForm">
                  <el-checkbox :disabled="disabled" style="white-space: pre-line; height: fit-content;margin: 10px" :label=key>{{AForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessa"
                    :before-remove="handleRemovea"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.afile[0]"
                    v-if="subForm.afile.length>0"
                    :preview-src-list=subForm.afile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group   v-model="subForm.ainfo1" v-for="(item1,key) in AForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{AForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="文明守纪" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.binfo" v-for="(item1,key) in BForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{BForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessb"
                    :before-remove="handleRemoveb"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.bfile[0]"
                    v-if="subForm.bfile.length>0"
                    :preview-src-list=subForm.bfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.binfo1" v-for="(item1,key) in BForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{BForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                  <el-button v-if='key===1&&subForm.binfo1.includes(1)' type="text">请输入寝室卫生不合格次数</el-button><br>
                  <el-input v-model="subForm.subb2" v-if='key===1&&subForm.binfo1.includes(1)' style="width: 30%" placeholder="请输入寝室卫生不合格次数"></el-input>
                  <el-button v-if='key===3&&subForm.binfo1.includes(3)' type="text">请输入受到通报批评次数</el-button><br>
                  <el-input v-model="subForm.subb4" v-if='key===3&&subForm.binfo1.includes(3)' style="width: 30%" placeholder="请输入受到通报批评次数"></el-input>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="学习态度" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.cinfo" v-for="(item1,key) in CForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{CForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessc"
                    :before-remove="handleRemovec"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.cfile[0]"
                    v-if="subForm.cfile.length>0"
                    :preview-src-list=subForm.cfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.cinfo1" v-for="(item1,key) in CForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{CForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                  <el-button v-if='key===1&&subForm.cinfo1.includes(1)' type="text">请输入无故缺考次数</el-button><br>
                  <el-input v-model="subForm.subc2" v-if='key===1&&subForm.cinfo1.includes(1)' style="width: 30%" placeholder="请输入无故缺考次数"></el-input>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="社会工作" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.dinfo" v-for="(item1,key) in DForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{DForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessd"
                    :before-remove="handleRemoved"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.dfile[0]"
                    v-if="subForm.dfile.length>0"
                    :preview-src-list=subForm.dfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.dinfo1" v-for="(item1,key) in DForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{DForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="实践公益" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.einfo" v-for="(item1,key) in EForm">
                  <el-checkbox  :disabled="disabled" class="el-checkbox" :label=key>{{EForm[key].fxyysJjfInfo}}</el-checkbox><br>
                  <el-button v-if='key===5&&subForm.einfo.includes(5)' type="text">请输入献血次数</el-button><br>
                  <el-input :disabled="disabled" v-model="subForm.adde6" v-if='key===5&&subForm.einfo.includes(5)' style="width: 30%" placeholder="请输入献血次数"></el-input>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccesse"
                    :before-remove="handleRemovee"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.efile[0]"
                    v-if="subForm.efile.length>0"
                    :preview-src-list=subForm.efile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.einfo1" v-for="(item1,key) in EForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{EForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="团队精神" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.finfo" v-for="(item1,key) in FForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{FForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessf"
                    :before-remove="handleRemovef"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.ffile[0]"
                    v-if="subForm.ffile.length>0"
                    :preview-src-list=subForm.ffile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.finfo1" v-for="(item1,key) in FForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{FForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="科研创新" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.ginfo" v-for="(item1,key) in GForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{GForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessg"
                    :before-remove="handleRemoveg"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.gfile[0]"
                    v-if="subForm.gfile.length>0"
                    :preview-src-list=subForm.gfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.ginfo1" v-for="(item1,key) in GForm1">
                  <el-checkbox style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{GForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                  <el-button v-if='key===0&&subForm.ginfo1.includes(0)' type="text">请输入出现学术不端行为次数</el-button><br>
                  <el-input v-model="subForm.subg1" v-if='key===0&&subForm.ginfo1.includes(0)' style="width: 30%" placeholder="请输入出现学术不端行为次数"></el-input>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="文体素质" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.hinfo" v-for="(item1,key) in HForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{HForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessh"
                    :before-remove="handleRemoveh"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.hfile[0]"
                    v-if="subForm.hfile.length>0"
                    :preview-src-list=subForm.hfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.hinfo1" v-for="(item1,key) in HForm1">
                  <el-checkbox  style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{HForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="技能素质" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.iinfo" v-for="(item1,key) in IForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{IForm[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessi"
                    :before-remove="handleRemovei"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.ifile[0]"
                    v-if="subForm.ifile.length>0"
                    :preview-src-list=subForm.ifile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.iinfo1" v-for="(item1,key) in IForm1">
                  <el-checkbox  style="white-space: pre-line;height: fit-content;margin: 10px" :label=key>{{IForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
            <el-collapse-item title="特殊经历" class="el-collapse-item">
              <div title="加分项">
                加分项
                <el-checkbox-group  v-model="subForm.jinfo" v-for="(item1,key) in JForm">
                  <el-checkbox :disabled="disabled" class="el-checkbox" :label=key>{{JForm[key].fxyysJjfInfo}}</el-checkbox><br>
                  <el-button v-if='key===1&&subForm.jinfo.includes(1)' type="text">请输入获得市级以上个人荣誉的项数</el-button><br>
                  <el-input :disabled="disabled" v-model="subForm.addj2" v-if='key===1&&subForm.jinfo.includes(1)' style="width: 30%" placeholder="请输入获得市级以上个人荣誉的项数"></el-input>
                </el-checkbox-group>
                <el-upload
                    action="/api/files/upload"
                    :on-success="filesUploadSuccessj"
                    :before-remove="handleRemovej"
                    accept=".jpg,.png,.gif"
                    :before-upload="beforeAvatarUpload"
                    multiple
                >
                  <el-button type="primary">点击上传</el-button>
                  <template #tip>
                    <div class="el-upload__tip">
                      图片格式要求jpg,png或gif 最大不超过5MB
                    </div>
                  </template>
                </el-upload>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="subForm.jfile[0]"
                    v-if="subForm.jfile.length>0"
                    :preview-src-list=subForm.jfile
                    fit="cover"
                /><br>
              </div>
              <div title="减分项">
                减分项
                <el-checkbox-group  v-model="subForm.jinfo1" v-for="(item1,key) in JForm1">
                  <el-checkbox  style="white-space: pre-line;height: fit-content;margin: 10px"  :label=key>{{JForm1[key].fxyysJjfInfo}}</el-checkbox><br>
                </el-checkbox-group>
              </div>
            </el-collapse-item>
          </el-collapse>
          {{subForm}}
        </el-form>
        <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible_add = false">取消</el-button>
              <el-button type="primary" @click="save">保存修改</el-button>
            </span>
        </template>
      </el-dialog>
    </div>
    <el-table :data="tableData"
              border
              style="width: auto"
              stripe>
      <el-table-column
          prop="stuId"
          label="学号"/>
      <el-table-column
          prop="year"
          label="申请年份"/>
      <el-table-column label="状态">
        <template #default="scope">
          <span v-if='scope.row.enable==="1"'>通过</span>
          <span v-if='scope.row.enable==="0"'>审核中</span>
          <span v-if='scope.row.enable==="-1"'>未通过</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="total"
          label="总分"/>
      <el-table-column
          prop="apoint"
          label="思想表现"/>
      <el-table-column
          prop="bpoint"
          label="文明守纪"/>
      <el-table-column
          prop="cpoint"
          label="学习态度"/>
      <el-table-column
          prop="dpoint"
          label="社会工作"/>
      <el-table-column
          prop="epoint"
          label="实践公益"/>
      <el-table-column
          prop="fpoint"
          label="团队精神"/>
      <el-table-column
          prop="gpoint"
          label="科研创新"/>
      <el-table-column
          prop="hpoint"
          label="文体素质"/>
      <el-table-column
          prop="ipoint"
          label="技能素质"/>
      <el-table-column
          prop="jpoint"
          label="特殊经历"/>

      <el-table-column align="left">
        <template #header  >
          操作
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">详情</el-button>
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
import {ElMessage} from "element-plus";

export default {
  name: "History",
  data(){
    return{
      stuId:"",
      year:new Date().getFullYear(),
      enable:"",
      dialogVisible_add:false,
      disabled:false,
      AForm:{}, BForm:{}, CForm:{}, DForm:{}, EForm:{}, FForm:{}, GForm:{}, HForm:{}, IForm:{}, JForm:{},
      AForm1:{}, BForm1:{}, CForm1:{}, DForm1:{}, EForm1:{}, FForm1:{}, GForm1:{}, HForm1:{}, IForm1:{}, JForm1:{},
      subForm:{
        stuId:"186003611",year:999999,enable:0,
        ainfo:[],binfo:[],cinfo:[],dinfo:[],einfo:[],finfo:[],ginfo:[],hinfo:[],iinfo:[],jinfo:[],
        ainfo1:[],binfo1:[],cinfo1:[],dinfo1:[],einfo1:[],finfo1:[],ginfo1:[],hinfo1:[],iinfo1:[],jinfo1:[],
        afile:[],bfile:[],cfile:[],dfile:[],efile:[],ffile:[],gfile:[],hfile:[],ifile:[],jfile:[],
        apoint:0,bpoint:0,cpoint:0,dpoint:0,epoint:0,fpoint:0,gpoint:0,hpoint:0,ipoint:0,jpoint:0,
        adde6:1,addj2:1,subb2:1,subb4:1,subc2:1,subg1:1,
        total:0
      },
      Form:{
        stuId:"186003611",year:9999,enable:0,
        ainfo:[],binfo:[],cinfo:[],dinfo:[],einfo:[],finfo:[],ginfo:[],hinfo:[],iinfo:[],jinfo:[],
        afile:"",bfile:"",cfile:"",dfile:"",efile:"",ffile:"",gfile:"",hfile:"",ifile:"",jfile:"",
        apoint:0,bpoint:0,cpoint:0,dpoint:0,epoint:0,fpoint:0,gpoint:0,hpoint:0,ipoint:0,jpoint:0,
        total:0
      },
      qzForm:{},
      tableData: [],
      currentPage:1,
      pageSize:5,
      total:100
    }
  },
  created() {
    this.stuId=JSON.parse(sessionStorage.getItem("user")).username
    request.get("/fxyysQz").then(res =>{
      this.qzForm=res.data
    })
    this.chushihua()
    this.load()
  },
  methods:{
    //获取加分项减分项
    chushihua(){
      //获取加分项
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"A",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.AForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"B",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.BForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"C",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.CForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"D",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.DForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"E",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.EForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"F",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.FForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"G",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.GForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"H",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.HForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"I",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.IForm=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"J",
          fxyysZf:"1"
        }
      }).then(res =>{
        this.JForm=res.data
      })
      //获取减分项
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"A",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.AForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"B",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.BForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"C",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.CForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"D",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.DForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"E",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.EForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"F",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.FForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"G",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.GForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"H",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.HForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"I",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.IForm1=res.data
      })
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:"J",
          fxyysZf:"-1"
        }
      }).then(res =>{
        this.JForm1=res.data
      })
    },
    //更新表格
    load(){
      request.get("/fxyysSc",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          stuId:this.stuId,
          enable:this.enable
        }
      }).then(res =>{
        console.log("非学业因素申请情况")
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    //查看表格某一行
    handleEdit(row){
      console.log("开始编辑")
      this.subForm=JSON.parse(JSON.stringify(row))
      if (this.subForm.ainfo!==""&&this.subForm.ainfo!==null){
        this.subForm.ainfo=this.subForm.ainfo.split(",")
        this.subForm.ainfo=this.subForm.ainfo.map(Number)
      }
      else {
        this.subForm.ainfo=[]
      }
      if (this.subForm.binfo!==""&&this.subForm.binfo!==null){
        this.subForm.binfo=this.subForm.binfo.split(",")
        this.subForm.binfo=this.subForm.binfo.map(Number)
      }
      else {
        this.subForm.binfo=[]
      }
      if (this.subForm.cinfo!==""&&this.subForm.cinfo!==null){
        this.subForm.cinfo=this.subForm.cinfo.split(",")
        this.subForm.cinfo=this.subForm.cinfo.map(Number)
      }
      else {
        this.subForm.cinfo=[]
      }
      if (this.subForm.dinfo!==""&&this.subForm.dinfo!==null){
        this.subForm.dinfo=this.subForm.dinfo.split(",")
        this.subForm.dinfo=this.subForm.dinfo.map(Number)
      }
      else {
        this.subForm.dinfo=[]
      }
      if (this.subForm.einfo!==""&&this.subForm.einfo!==null){
        this.subForm.einfo=this.subForm.einfo.split(",")
        this.subForm.einfo=this.subForm.einfo.map(Number)
      }
      else {
        this.subForm.einfo=[]
      }
      if (this.subForm.finfo!==""&&this.subForm.finfo!==null){
        this.subForm.finfo=this.subForm.finfo.split(",")
        this.subForm.finfo=this.subForm.finfo.map(Number)
      }
      else {
        this.subForm.finfo=[]
      }
      if (this.subForm.ginfo!==""&&this.subForm.ginfo!==null){
        this.subForm.ginfo=this.subForm.ginfo.split(",")
        this.subForm.ginfo=this.subForm.ginfo.map(Number)
      }
      else {
        this.subForm.ginfo=[]
      }
      if (this.subForm.hinfo!==""&&this.subForm.hinfo!==null){
        this.subForm.hinfo=this.subForm.hinfo.split(",")
        this.subForm.hinfo=this.subForm.hinfo.map(Number)
      }
      else {
        this.subForm.hinfo=[]
      }
      if (this.subForm.iinfo!==""&&this.subForm.iinfo!==null){
        this.subForm.iinfo=this.subForm.iinfo.split(",")
        this.subForm.iinfo=this.subForm.iinfo.map(Number)
      }
      else {
        this.subForm.iinfo=[]
      }
      if (this.subForm.jinfo!==""&&this.subForm.jinfo!==null){
        this.subForm.jinfo=this.subForm.jinfo.split(",")
        this.subForm.jinfo=this.subForm.jinfo.map(Number)
      }
      else {
        this.subForm.jinfo=[]
      }

      if (this.subForm.ainfo1!==""&& this.subForm.ainfo1!==null){
        this.subForm.ainfo1=this.subForm.ainfo1.split(",")
        this.subForm.ainfo1=this.subForm.ainfo1.map(Number)
      }
      else {
        this.subForm.ainfo1=[]
      }
      if (this.subForm.binfo1!==""&& this.subForm.binfo1!==null){
        this.subForm.binfo1=this.subForm.binfo1.split(",")
        this.subForm.binfo1=this.subForm.binfo1.map(Number)
      }
      else {
        this.subForm.binfo1=[]
      }
      if (this.subForm.cinfo1!==""&& this.subForm.cinfo1!==null){
        this.subForm.cinfo1=this.subForm.cinfo1.split(",")
        this.subForm.cinfo1=this.subForm.cinfo1.map(Number)
      }
      else {
        this.subForm.cinfo1=[]
      }
      if (this.subForm.dinfo1!==""&& this.subForm.dinfo1!==null){
        this.subForm.dinfo1=this.subForm.dinfo1.split(",")
        this.subForm.dinfo1=this.subForm.dinfo1.map(Number)
      }
      else {
        this.subForm.dinfo1=[]
      }
      if (this.subForm.einfo1!==""&& this.subForm.einfo1!==null){
        this.subForm.einfo1=this.subForm.einfo1.split(",")
        this.subForm.einfo1=this.subForm.einfo1.map(Number)
      }
      else {
        this.subForm.einfo1=[]
      }
      if (this.subForm.finfo1!==""&& this.subForm.finfo1!==null){
        this.subForm.finfo1=this.subForm.finfo1.split(",")
        this.subForm.finfo1=this.subForm.finfo1.map(Number)
      }
      else {
        this.subForm.finfo1=[]
      }
      if (this.subForm.ginfo1!==""&& this.subForm.ginfo1!==null){
        this.subForm.ginfo1=this.subForm.ginfo1.split(",")
        this.subForm.ginfo1=this.subForm.ginfo1.map(Number)
      }
      else {
        this.subForm.ginfo1=[]
      }
      if (this.subForm.hinfo1!==""&& this.subForm.hinfo1!==null){
        this.subForm.hinfo1=this.subForm.hinfo1.split(",")
        this.subForm.hinfo1=this.subForm.hinfo1.map(Number)
      }
      else {
        this.subForm.hinfo1=[]
      }
      if (this.subForm.iinfo1!==""&& this.subForm.iinfo1!==null){
        this.subForm.iinfo1=this.subForm.iinfo1.split(",")
        this.subForm.iinfo1=this.subForm.iinfo1.map(Number)
      }
      else {
        this.subForm.iinfo1=[]
      }
      if (this.subForm.jinfo1!==""&& this.subForm.jinfo1!==null){
        this.subForm.jinfo1=this.subForm.jinfo1.split(",")
        this.subForm.jinfo1=this.subForm.jinfo1.map(Number)
      }
      else {
        this.subForm.jinfo1=[]
      }
      //a
      if(this.subForm.afile==null || this.subForm.afile===""){
        this.subForm.afile=[]
      }
      else if(this.subForm.afile!==""){
        this.subForm.afile=this.subForm.afile.split(",")
      }
      //b
      if(this.subForm.bfile==null|| this.subForm.bfile===""){
        this.subForm.bfile=[]
      }
      else if(this.subForm.bfile!==""){
        this.subForm.bfile=this.subForm.bfile.split(",")
      }
      //c
      if(this.subForm.cfile==null|| this.subForm.cfile===""){
        this.subForm.cfile=[]
      }
      else if(this.subForm.cfile!==""){
        this.subForm.cfile=this.subForm.cfile.split(",")
      }
      //d
      if(this.subForm.dfile==null|| this.subForm.dfile===""){
        this.subForm.dfile=[]
      }
      else if(this.subForm.dfile!==""){
        this.subForm.dfile=this.subForm.dfile.split(",")
      }
      //e
      if(this.subForm.efile==null|| this.subForm.efile===""){
        this.subForm.efile=[]
      }
      else if(this.subForm.efile!==""){
        this.subForm.efile=this.subForm.efile.split(",")
      }
      //f
      if(this.subForm.ffile==null|| this.subForm.ffile===""){
        this.subForm.ffile=[]
      }
      else if(this.subForm.ffile!==""){
        this.subForm.ffile=this.subForm.ffile.split(",")
      }
      //g
      if(this.subForm.gfile==null|| this.subForm.gfile===""){
        this.subForm.gfile=[]
      }
      else if(this.subForm.gfile!==""){
        this.subForm.gfile=this.subForm.gfile.split(",")
      }
      //h
      if(this.subForm.hfile==null|| this.subForm.hfile===""){
        this.subForm.hfile=[]
      }
      else if(this.subForm.hfile!==""){
        this.subForm.hfile=this.subForm.hfile.split(",")
      }
      //i
      if(this.subForm.ifile==null|| this.subForm.ifile===""){
        this.subForm.ifile=[]
      }
      else if(this.subForm.ifile!==""){
        this.subForm.ifile=this.subForm.ifile.split(",")
      }
      //j
      if(this.subForm.jfile==null|| this.subForm.jfile===""){
        this.subForm.jfile=[]
      }
      else if(this.subForm.jfile!==""){
        this.subForm.jfile=this.subForm.jfile.split(",")
      }
      console.log("subForm")
      console.log(this.subForm)
      this.dialogVisible_add=true

    },
    //删除某一行
    handleDelete(row){
      request.delete("/fxyysSc/"+row.stuId+"/"+row.year+"/"+row.enable).then(res =>{
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
    //查看已通过的非学业因素申请
    checkpass(){
      this.enable=1
      this.load()
    },
    checkall(){
      this.enable=""
      this.load()
    },
    //计算分数
    calculation(){
      console.log("开始计算")
      //初始化分数
      this.subForm.apoint=this.qzForm[0]["jbf"]
      this.subForm.bpoint=this.qzForm[1]["jbf"]
      this.subForm.cpoint=this.qzForm[2]["jbf"]
      this.subForm.dpoint=this.qzForm[3]["jbf"]
      this.subForm.epoint=this.qzForm[4]["jbf"]
      this.subForm.fpoint=this.qzForm[5]["jbf"]
      this.subForm.gpoint=this.qzForm[6]["jbf"]
      this.subForm.hpoint=this.qzForm[7]["jbf"]
      this.subForm.ipoint=this.qzForm[8]["jbf"]
      this.subForm.jpoint=this.qzForm[9]["jbf"]
      this.subForm.total=0
      var i=0;
      //计算各项非学业因素分数
      //特判项
      if(this.subForm.binfo1.includes(1)){
        this.subForm.bpoint-=(this.subForm.subb2-1)*10;
      }
      if(this.subForm.binfo1.includes(3)){
        this.subForm.bpoint-=(this.subForm.subb4-1)*10;
      }
      if(this.subForm.cinfo1.includes(1)){
        this.subForm.cpoint-=(this.subForm.subc2-1)*10;
      }
      if(this.subForm.einfo.includes(5)){
        this.subForm.epoint+=(this.subForm.adde6-1)*30;
      }
      if(this.subForm.ginfo1.includes(0)){
        this.subForm.gpoint-=(this.subForm.subg1-1)*50;
      }
      if(this.subForm.jinfo.includes(1)){
        this.subForm.jpoint+=(this.subForm.addj2-1)*5;
      }
      //a
      for(i=0;i<this.subForm.ainfo.length;i++){
        this.subForm.apoint+=this.AForm[this.subForm.ainfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.ainfo1.length;i++){
        this.subForm.apoint-=this.AForm1[this.subForm.ainfo1[i]]["fs"];
      }
      if(this.subForm.apoint>=100){this.subForm.apoint=100}
      else if(this.subForm.apoint<=0){this.subForm.apoint=0}
      //b
      for(i=0;i<this.subForm.binfo.length;i++){
        this.subForm.bpoint+=this.BForm[this.subForm.binfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.binfo1.length;i++){
        this.subForm.bpoint-=this.BForm1[this.subForm.binfo1[i]]["fs"];
      }
      if(this.subForm.bpoint>=100){this.subForm.bpoint=100}
      else if(this.subForm.bpoint<=0){this.subForm.bpoint=0}
      //c
      for(i=0;i<this.subForm.cinfo.length;i++){
        this.subForm.cpoint+=this.CForm[this.subForm.cinfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.cinfo1.length;i++){
        this.subForm.cpoint-=this.CForm1[this.subForm.cinfo1[i]]["fs"];
      }
      if(this.subForm.cpoint>=100){this.subForm.cpoint=100}
      if(this.subForm.cpoint<=0){this.subForm.cpoint=0}
      //d
      for(i=0;i<this.subForm.dinfo.length;i++){
        this.subForm.dpoint+=this.DForm[this.subForm.dinfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.dinfo1.length;i++){
        this.subForm.dpoint-=this.DForm1[this.subForm.dinfo1[i]]["fs"];
      }
      if(this.subForm.dpoint>=100){this.subForm.dpoint=100}
      if(this.subForm.dpoint<=0){this.subForm.dpoint=0}
      //e
      for(i=0;i<this.subForm.einfo.length;i++){
        this.subForm.epoint+=this.EForm[this.subForm.einfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.einfo1.length;i++){
        this.subForm.epoint-=this.EForm1[this.subForm.einfo1[i]]["fs"];
      }
      if(this.subForm.epoint>=100){this.subForm.epoint=100}
      if(this.subForm.epoint<=0){this.subForm.epoint=0}
      //f
      for(i=0;i<this.subForm.finfo.length;i++){
        this.subForm.fpoint+=this.FForm[this.subForm.finfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.finfo1.length;i++){
        this.subForm.fpoint-=this.FForm1[this.subForm.finfo1[i]]["fs"];
      }
      if(this.subForm.fpoint>=100){this.subForm.fpoint=100}
      if(this.subForm.fpoint<=0){this.subForm.fpoint=0}
      //g
      for(i=0;i<this.subForm.ginfo.length;i++){
        this.subForm.gpoint+=this.GForm[this.subForm.ginfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.ginfo1.length;i++){
        this.subForm.gpoint-=this.GForm1[this.subForm.ginfo1[i]]["fs"];
      }
      if(this.subForm.gpoint>=100){this.subForm.gpoint=100}
      if(this.subForm.gpoint<=0){this.subForm.gpoint=0}
      //h
      for(i=0;i<this.subForm.hinfo.length;i++){
        this.subForm.hpoint+=this.HForm[this.subForm.hinfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.hinfo1.length;i++){
        this.subForm.hpoint-=this.HForm1[this.subForm.hinfo1[i]]["fs"];
      }
      if(this.subForm.hpoint>=100){this.subForm.hpoint=100}
      if(this.subForm.hpoint<=0){this.subForm.hpoint=0}
      //i
      for(i=0;i<this.subForm.iinfo.length;i++){
        this.subForm.ipoint+=this.IForm[this.subForm.iinfo[i]]["fs"];
      }
      for(i=0;i<this.subForm.iinfo1.length;i++){
        this.subForm.ipoint-=this.IForm1[this.subForm.iinfo1[i]]["fs"];
      }
      if(this.subForm.ipoint>=100){this.subForm.ipoint=100}
      if(this.subForm.ipoint<=0){this.subForm.ipoint=0}
      //j
      for(i=0;i<this.subForm.jinfo.length;i++){
        this.subForm.jpoint+=this.JForm[this.subForm.jinfo[i]]["fs"];
      }

      this.subForm.total=this.qzForm[0]["qz"]*this.subForm.apoint+this.qzForm[1]["qz"]*this.subForm.bpoint+
          this.qzForm[2]["qz"]*this.subForm.cpoint+this.qzForm[3]["qz"]*this.subForm.dpoint+
          this.qzForm[4]["qz"]*this.subForm.epoint+this.qzForm[5]["qz"]*this.subForm.fpoint+
          this.qzForm[6]["qz"]*this.subForm.gpoint+this.qzForm[7]["qz"]*this.subForm.hpoint+
          this.qzForm[8]["qz"]*this.subForm.ipoint+this.qzForm[9]["qz"]*this.subForm.jpoint

    },
    //格式化数据
    geshihua(Form){
      Form.ainfo=Form.ainfo.toString()
      Form.binfo=Form.binfo.toString()
      Form.cinfo=Form.cinfo.toString()
      Form.dinfo=Form.dinfo.toString()
      Form.einfo=Form.einfo.toString()
      Form.finfo=Form.finfo.toString()
      Form.ginfo=Form.ginfo.toString()
      Form.hinfo=Form.hinfo.toString()
      Form.iinfo=Form.iinfo.toString()
      Form.jinfo=Form.jinfo.toString()
      Form.ainfo1=Form.ainfo1.toString()
      Form.binfo1=Form.binfo1.toString()
      Form.cinfo1=Form.cinfo1.toString()
      Form.dinfo1=Form.dinfo1.toString()
      Form.einfo1=Form.einfo1.toString()
      Form.finfo1=Form.finfo1.toString()
      Form.ginfo1=Form.ginfo1.toString()
      Form.hinfo1=Form.hinfo1.toString()
      Form.iinfo1=Form.iinfo1.toString()
      Form.jinfo1=Form.jinfo1.toString()
      Form.afile=Form.afile.join()
      Form.bfile=Form.bfile.join()
      Form.cfile=Form.cfile.join()
      Form.dfile=Form.dfile.join()
      Form.efile=Form.efile.join()
      Form.ffile=Form.ffile.join()
      Form.gfile=Form.gfile.join()
      Form.hfile=Form.hfile.join()
      Form.ifile=Form.ifile.join()
      Form.jfile=Form.jfile.join()
    },
    //更新修改数据
    save(){
      this.calculation()
      this.Form=JSON.parse(JSON.stringify(this.subForm))
      this.geshihua(this.Form)
      console.log("Form")
      console.log(this.Form)
      request.post("/fxyysSc",this.Form).then(res =>{
        console.log(res)
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:res.data
          })
        }else{
          this.$message({
            type:"error",
            message: res.msg
          })
        }
        this.load()
      })
      this.dialogVisible_add=false
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()
    },
    //限制图片格式和大小
    beforeAvatarUpload(rawFile){
      if (rawFile.type !== 'image/png'&&  rawFile.type !== 'image/jpeg'&&  rawFile.type !== 'image/gif') {
        ElMessage.error('图片要求格式为jpg,png或gif')
        return false
      } else if (rawFile.size / 1024 / 1024 > 5) {
        ElMessage.error('图片大小最大为5MB!')
        return false
      }
      return true
    },
    //上传删除图片
    //a
    handleRemovea(raw){
      for(var i=0;i<this.subForm.afile.length;i++){
        if(this.subForm.afile[i]===raw.response.data){
          this.subForm.afile.splice(i,1)
        }
      }
    },
    filesUploadSuccessa(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.afile.push(res.data)
    },
    //b
    handleRemoveb(raw){
      for(var i=0;i<this.subForm.bfile.length;i++){
        if(this.subForm.bfile[i]===raw.response.data){
          this.subForm.bfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessb(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.bfile.push(res.data)
    },
    //c
    handleRemovec(raw){
      for(var i=0;i<this.subForm.cfile.length;i++){
        if(this.subForm.cfile[i]===raw.response.data){
          this.subForm.cfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessc(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.cfile.push(res.data)
    },
    //d
    handleRemoved(raw){
      for(var i=0;i<this.subForm.dfile.length;i++){
        if(this.subForm.dfile[i]===raw.response.data){
          this.subForm.dfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessd(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.dfile.push(res.data)
    },
    //e
    handleRemovee(raw){
      for(var i=0;i<this.subForm.efile.length;i++){
        if(this.subForm.efile[i]===raw.response.data){
          this.subForm.efile.splice(i,1)
        }
      }
    },
    filesUploadSuccesse(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.efile.push(res.data)
    },
    //f
    handleRemovef(raw){
      for(var i=0;i<this.subForm.ffile.length;i++){
        if(this.subForm.ffile[i]===raw.response.data){
          this.subForm.ffile.splice(i,1)
        }
      }
    },
    filesUploadSuccessf(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.ffile.push(res.data)
    },
    //g
    handleRemoveg(raw){
      for(var i=0;i<this.subForm.gfile.length;i++){
        if(this.subForm.gfile[i]===raw.response.data){
          this.subForm.gfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessg(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.gfile.push(res.data)
    },
    //h
    handleRemoveh(raw){
      for(var i=0;i<this.subForm.hfile.length;i++){
        if(this.subForm.hfile[i]===raw.response.data){
          this.subForm.hfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessh(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.hfile.push(res.data)
    },
    //i
    handleRemovei(raw){
      for(var i=0;i<this.subForm.ifile.length;i++){
        if(this.subForm.ifile[i]===raw.response.data){
          this.subForm.ifile.splice(i,1)
        }
      }
    },
    filesUploadSuccessi(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.ifile.push(res.data)
    },
    //j
    handleRemovej(raw){
      for(var i=0;i<this.subForm.jfile.length;i++){
        if(this.subForm.jfile[i]===raw.response.data){
          this.subForm.jfile.splice(i,1)
        }
      }
    },
    filesUploadSuccessj(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"图片上传成功"
        })
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
      this.subForm.jfile.push(res.data)
    },
  }
}
</script>

<style scoped>
:deep(.el-collapse-item) {
  --el-collapse-header-font-size: 25px;
  --el-collapse-content-font-size: 20px;
}
:deep(.el-checkbox){
  height: fit-content;
  margin: 10px;
  white-space: pre-line;
  line-height: 20px;
}

</style>