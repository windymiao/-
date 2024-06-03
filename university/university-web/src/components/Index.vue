<template>
    <div>
       <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside :width="aside_with" style="background-color:#545c64;height: 100vh; margin-left: -1px;">
          <Aside :isCollapse="isCollapse"></Aside>
        </el-aside>
  
        <el-container style="height: 100%">
          <el-header style="text-align: right; font-size: 12px; height: 100%; border-bottom: #B3C0D1 1px solid">
            <HeaderComponent @doCollapse="doCollapse" :icon="icon"></HeaderComponent>
          </el-header>
  
          <el-main style="height: 100%">
           <!-- <MainComponent></MainComponent> -->
           <router-view/>
          </el-main>
        </el-container>
      </el-container> 
    </div>
  </template>
  
  <script>
    import Aside from "@/components/Aside";
    import HeaderComponent from "@/components/Header";
    // import MainComponent from "@/components/Main";
    export default {
      name: "IndexComponent",
      components: {HeaderComponent, Aside},
      data(){
        return {
          isCollapse:false,
          aside_with:'200px',
          icon:'el-icon-s-fold',
        }
      },
      methods:{
        doCollapse(){
          this.isCollapse = !this.isCollapse
          if(!this.isCollapse){// 展开
            this.aside_with='200px'
            this.icon='el-icon-s-fold'
          }else{//关起、关闭、收起
            this.aside_with='64px'
            this.icon='el-icon-s-unfold'
          }
        }
      }
  
    }
  </script>
  
  <style scoped>
    .el-header {
      /*background-color: #B3C0D1;*/
      color: #333;
      line-height: 60px;
    }
    .el-main{
      padding: 5px;
    }
    .el-aside {
      color: #333;
    }
  </style>