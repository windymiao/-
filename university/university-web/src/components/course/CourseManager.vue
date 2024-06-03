<template>
    <div>
      <!-- 设置查询条件 -->
      <div style="margin-bottom: 5px">
        <el-input v-model="cname" placeholder="请输入课程名" suffix-icon="el-icon-search"
         style="width: 200px;" @keydown.enter.native="loadPost"></el-input>
        
        
        <el-select v-model="deptId" placeholder="请选择系别" style="margin-left: 5px;">
          <el-option
            v-for="item in deptData"
            :key="item.deptId"
            :label="item.deptName"
            :value="item.deptId">
          </el-option>
        </el-select>

        <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
        <el-button type="success" @click="resetParam">重置</el-button>
        <el-button size="small" type="success" @click="addcourse">新增</el-button>
      </div>
      <el-table :data="tableData" 
      :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border>
      <el-table-column prop="cno" label="课程号" width="140">
      </el-table-column>
      <el-table-column prop="cname" label="课程名" width="120">
      </el-table-column>
      <el-table-column prop="deptId" label="系别" :formatter="formatDept" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="modCourse(scope.row)">编辑</el-button>
            <el-popconfirm
                title="这是一段内容确定删除吗？"
                @confirm="delCourse(scope.row.cno)">
              <el-button slot="reference" size="small" type="danger" style="margin-left: 5px;">删除</el-button>
            </el-popconfirm>
          </template>
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
  <!-- 插入和修改课程信息弹出的模态框 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form ref="courseForm" :model="courseForm" :rules="courseRules" label-width="80px">
        <el-form-item label="课程号" prop="cno">
            <el-col :span="13">
              <el-input v-model="courseForm.cno"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="课程名" prop="cname">
            <el-col :span="13">
              <el-input v-model="courseForm.cname"></el-input>
            </el-col>
        </el-form-item>
 
        <el-form-item label="系别" prop="deptId">
            <el-col :span="20">
              <el-select v-model="courseForm.deptId" placeholder="请选择系别" style="margin-left: 5px;">
                <el-option
                  v-for="item in deptData"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item.deptId">
                </el-option>
              </el-select>
            </el-col>
        </el-form-item>
    
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCourse">确 定</el-button>
      </span>
    </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
      name:"CourseManager",
      data() {
      // 账号唯一性校验
      let checkDuplicate =(rule,value,callback)=>{
        // 因为采用的课程号和学号作为主键，所以不能使用课程号或学号作为表示判断该记录是否存在，
        // 因为插入时，在模态框输入，导致学号或课程号存在，跳过账号唯一性校验
        // if(this.courseForm.cno){
        //   return callback();
        // }

        if(this.isMod){
            return callback();
        }
        this.$axios.get(this.$httpUrl+"/course/findByNo?cno="+this.courseForm.cno).then(res=>res.data).then(res=>{
          console.log(res)
          if(res.code!==200){
            callback()
          }else{
            callback(new Error('账号已经存在'));
          }
        })
      };
        return {
          pageNum:1,   // 当前分页页码数
          pageSize:5,   // 每页的记录数
          // 用于向后端传送数据，（查询条件）
          cname:'',
          deptId:null,
          dialogVisible: false,   // 控制课程模态框的显示
          isMod:false,      // 用于判断执行修改还是添加
          deptData:[],  // 存放查询到的所有dept
          total:0,   // 总记录数
          tableData: [],   // 存放后端返回的数据
          courseForm:{
            cno:'',
            cname:'',
            deptId:'',
          },
          courseRules:{
            cno: [
              {required: true, message: '请输入账号', trigger: 'blur'},
            //   {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'},
              {validator:checkDuplicate,trigger: 'blur'}
            ],
            cname: [
              {required: true, message: '请输入名字', trigger: 'blur'}
            ],
          }
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
          // console.log(this.filteredMajorData)
        },
          // 清空重置新增表单
        resetForm() {
          this.$refs.courseForm.resetFields();
        },
        // 将对应的dept名字，回显到页面
        formatDept(row){
            let res = this.deptData.find(item=>{
              return item.deptId == row.deptId
              
            })
            return res && res.deptName
        },
        // 加载所有的dept信息
        loadDept(){
          this.$axios.get(this.$httpUrl+'/dept/list').then(res=>res.data).then(res=>{
              console.log(res)
              this.deptData=res.data
          })
        },
  
        // 加载所有的课程信息
        loadPost(){
          this.$axios.post(this.$httpUrl+'/course/page',{
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
            cname:this.cname,
            deptId:this.deptId,
          }
          }).then(res=>res.data).then(res=>{
            console.log(res)
            if(res.code == 200){
              this.total=res.total
              this.tableData=res.data
            }else{
              alert("查询数据失败")
            }
          })
        },
  
        // 新增课程  1. 打开一个模态框，填写新增课程数据
        addcourse(){
          this.dialogVisible=true
          this.isMod=false
          this.$nextTick(()=>{  // 异步执行
            this.resetForm()
          })
        },
        // 新增课程 2. 将数据传递给后端，执行添加操作
        toAdd(){
          this.$axios.post(this.$httpUrl+"/course/add",this.courseForm).then(res=>res.data).then(res=>{
            console.log("add")
            console.log(res)
            if(res.code == 200){
              this.$message({
                message:"保存信息成功",
                type:"success",
              });
              // 关闭模态框，刷新页面
              this.dialogVisible=false;
              this.loadPost();
            }else{
              this.$message({
                message:"保存信息失败",
                type:"error",
              });
            }
          })
        },
  
        // （合在一起写，存在判断账号是否存在有误的问题）
  
        // 修改课程 1. 打开模态框，将要修改的课程信息回显到模态框
        modCourse(row){
          console.log(row)
          // 弹出模式框
          this.dialogVisible=true
          this.isMod=true
          this.$nextTick(()=>{    // 异步执行，避免先编辑后新增出现模式框内容异常
            // 赋值给表单
            this.courseForm.cno=row.cno
            this.courseForm.cname=row.cname
            this.courseForm.deptId=row.deptId
          })
        },
        // 修改课程 2. 将数据传递给后端，根据id执行修改操作
        toMod(){
          this.$axios.post(this.$httpUrl+"/course/update",this.courseForm).then(res=>res.data).then(res=>{
            console.log("update")
            console.log(res)
            if(res.code == 200){
              this.$message({
                message:"修改信息成功",
                type:"success",
              });
              // 关闭模态框，刷新页面
              this.dialogVisible=false;
              this.loadPost();
            }else{
              this.$message({
                message:"修改信息失败",
                type:"error",
              });
            }
          })
        },
        // 新增课程和修改课程  2.保存新增或修改的课程信息，通过cno是否存在来判断是新增或修改（后端代码判断） 
        saveCourse(){
          // 判断是否通过了rules规则
          this.$refs.courseForm.validate((valid) => {
            if (valid) {
                console.log(this.courseForm.cno)
              // 此处存疑，应该有更为准确的解决方法
              // 如果传入的cno不为空，则执行更新操作，否则为新增
              if(this.isMod){
                this.toMod();
              }else{
                this.toAdd();
              }
            }else{
              console.log('error submit!!');
              return false;
            }
          })
        },
  
        // 删除课程
        delCourse(cno){
          this.$axios.get(this.$httpUrl+"/course/delete?cno="+cno).then(res=>res.data).then(res=>{
            console.log(res)
          if(res.code === 200){
            this.$message({
              message:"删除成功",
              type:"success",
            });
            this.loadPost();
          }else{
            this.$message({
              message:"删除失败",
              type:"error",
            });
          }
          })
        },
      },
  
      beforeMount() {
        this.loadDept();
        this.loadPost();
    }
  }
  </script>