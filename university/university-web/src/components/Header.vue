<template>
    <div style="display: flex;line-height: 60px;">
      <div style="margin-top: 1px;">
        <i :class="icon" style="font-size: 20px;"  @click="collapse"></i>
      </div>
      <div style="flex: 1;text-align: center;font-size: 34px;">
        <span>欢迎来到教务系统</span>
      </div>
      <el-dropdown>
        <div style="height: 0px;">
          <!-- 不包div，直接使用span，会报错，错误信息:
            ERROR
            this.triggerElm.setAttribute is not a function
            TypeError: this.triggerElm.setAttribute is not a function 
          -->
          <span v-if="user.roleId==0">{{user.username}}</span> 
          <span v-if="user.roleId==2">{{user.sname}}</span> 
          <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </template>
  
  <script>
  export default {
    name: "HeaderComponent",
    data() {
  
      return {
        user:JSON.parse(sessionStorage.getItem("CurUser"))
      }
    },
    props:{
      icon:String
    },
    methods:{
      toUser(){
        console.log("toUser");
        this.$router.push("/Home")
      },
      logout(){
        console.log("logout")
        this.$confirm('您确定要退出登录吗?', '提示', {
          confirmButtonText: '确定',  //确认按钮的文字显示
          type: 'warning',
          center: true, //文字居中显示
  
        })
            .then(() => {     // 确认操作
              this.$message({
                type:'success',
                message:'退出登录成功'
              })
              this.$router.push("/")    // 路由跳转，跳转到登录页
              sessionStorage.clear()    // 清空session
            })
            .catch(() => {    // 取消操作
              this.$message({
                type:'info',
                message:'已取消退出登录'
              })
            })
      },
      collapse(){
        this.$emit('doCollapse')
      }
    },
    created() {
      // 父级路由/Index直接加载子路由/Home
      this.$router.push("/Home")
    }
  }
  </script>
  
  <style scoped>
  
  </style>