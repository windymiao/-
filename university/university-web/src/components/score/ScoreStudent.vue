<template>
    <div>
      <!-- 设置查询条件 -->
      <div style="margin-bottom: 5px">
        <el-input v-model="cname" placeholder="请输入课程名" suffix-icon="el-icon-search"
         style="width: 200px;" @keydown.enter.native="loadPost"></el-input>
        
        <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
        <el-button type="success" @click="resetParam">重置</el-button>
      </div>
      <el-table :data="tableData" 
        :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border>
        <el-table-column prop="cno" label="课程号" width="140">
        </el-table-column>
        <el-table-column prop="cno" label="课程名" width="120" :formatter="formatCourse">
        </el-table-column> 
        <el-table-column prop="deptId" label="开课学院" width="180">
            {{this.deptName}}
        </el-table-column> 
        <el-table-column prop="score" label="成绩" width="180">
        </el-table-column>
      </el-table>
  <!-- 分页导航栏 -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="pageNum"  
            :page-sizes="[5,10,20,30]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total=total>
        </el-pagination>
    </div>
</template>
  
<script>
  export default {
      name:"ScoreStudent",
      data() {
        return {
          pageNum:1,   // 当前分页页码数
          pageSize:5,   // 每页的记录数
          // 用于向后端传送数据，（查询条件）
          student:JSON.parse(sessionStorage.getItem("CurUser")),
          cno:'',
          cname:'',
          deptId:null,
          deptName:'',
          courseData:[],    // 存放查询到的所有course
          deptData:[],  // 存放查询到的所有dept
          total:0,   // 总记录数
          tableData: [],   // 存放后端返回的数据
        }
      },
      methods:{
        // 分页信息改变时的方法
        handleSizeChange(val) {
          console.log(`每页 ${val} 条`);
          this.pageNum=1
          this.pageSize = val
          this.loadPost()
        },
        handleCurrentChange(val) {
          console.log(`当前页: ${val}`);
          this.pageNum=val
          this.loadPost()
        },
  
        // 重置查询条件
        resetParam(){
          this.cname=""
          this.deptId=""
        },
        // 将对应的course名字，回显到页面
        formatCourse(row){
            // console.log(this.courseData)
            // console.log(row.cno)
            let res = this.courseData.find(item=>{
                return item.cno == row.cno
            })
            return res && res.cname
        },
        // 加载所有的dept信息
        loadCourse(){
          this.$axios.get(this.$httpUrl+'/course/list').then(res=>res.data).then(res=>{
              console.log(res)
              this.courseData=res.data
          })
        },

        // 根据id，查询对应的dept信息
        loadDept(){
          this.$axios.get(this.$httpUrl+'/dept/one?deptId='+this.student.deptId).then(res=>res.data).then(res=>{
              this.deptName=res.data.deptName
          })
        },
  
        // 加载所有的课程成绩信息
        loadPost(){
          this.$axios.post(this.$httpUrl+'/sc/page',{
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
                sno:this.student.sno,
                cno:this.cno,
            }
          }).then(res=>res.data).then(res=>{
            console.log(res)
            if(res.code == 200){
              this.total=res.total
              this.tableData=res.data
            // this.tableData.deptId=this.student.deptId
              // console.log(this.tableData)
            }else{
              alert("查询数据失败")
            }
          })
        },
      },
      beforeMount() {
        this.loadCourse();
        this.loadDept();
        this.loadPost();
      }
  }
</script>