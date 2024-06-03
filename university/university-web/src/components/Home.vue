<template>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
      <h1 style="font-size: 30px;">个人中心</h1>
      <!-- 管理员信息页面 -->
      <el-descriptions  :title="'欢迎您！'+ user.username" :column="2" size="40" v-if="user.roleId==0" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            ID
          </template>
          {{user.id}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            密码
          </template>
          {{user.password}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-files"></i>
            邮箱
          </template>
          {{user.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            角色
          </template>
          <el-tag
              type="success"
              disable-transitions>{{user.roleId==0?"管理员":(user.roleId==1?"教师":"学生")}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
  
      <!-- 学生信息页面 -->
      <el-descriptions  :title="'欢迎您！'+ user.sname" :column="2" size="40" v-if="user.roleId==2" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            学号
          </template>
          {{user.sno}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            年龄
          </template>
          {{user.age}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-files"></i>
            年级
          </template>
          {{user.grade}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            性别
          </template>
          {{user.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            系别
          </template>
          {{dept.deptName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            专业
          </template>
          {{major.majorName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            角色
          </template>
          <el-tag
              type="success"
              disable-transitions>{{user.roleId==0?"管理员":(user.roleId==1?"教师":"学生")}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <DateUtils></DateUtils>
    </div>
  </template>
  
  <script>
  import DateUtils from "./DateUtils";
  export default {
      name: "HomeComponent",
      components: {DateUtils},
      data() {
        return {
          user:{},
          dept:{},
          major:{},
        }
      },
      methods:{
        init(){
          this.user = JSON.parse(sessionStorage.getItem('CurUser'))
        },
        // 根据id,查询对应的deptName
        loadDept(){
            this.$axios.get(this.$httpUrl+'/dept/one?deptId='+this.user.deptId).then(res=>res.data).then(res=>{
                console.log(res)
                this.dept=res.data
            })
        },
        // 根据id,查询对应的majorName
        loadMajor(){
            this.$axios.get(this.$httpUrl+'/major/one?majorId='+this.user.majorId).then(res=>res.data).then(res=>{
            console.log(res)
            this.major=res.data
            })
        },
      },
      created(){
        this.init()
      },
      beforeMount() {
        if(this.user.roleId == 2){
            this.loadDept();
            this.loadMajor();
        }
      },
    }
  </script>
  
  <style scoped>
    .el-descriptions{
      width:90%;
  
      margin: 0 auto;
      text-align: center;
    }
  </style>