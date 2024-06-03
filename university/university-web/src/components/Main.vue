<template>
  <div>
    <!-- 设置查询条件 -->
    <div style="margin-bottom: 5px">
      <el-input v-model="sname" placeholder="请输入姓名" suffix-icon="el-icon-search"
       style="width: 200px;" @keydown.enter.native="loadPost"></el-input>
       <el-select v-model="gender" placeholder="请选择性别" style="margin-left: 5px;">
        <el-option
          v-for="item in genders"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="grade" placeholder="请选择年级" style="margin-left: 5px;">
        <el-option
          v-for="item in grades"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="deptId" placeholder="请选择系别" style="margin-left: 5px;">
        <el-option
          v-for="item in deptData"
          :key="item.deptId"
          :label="item.deptName"
          :value="item.deptId">
        </el-option>
      </el-select>
      <el-select v-model="majorId" placeholder="请选择专业" style="margin-left: 5px;">
        <el-option
          v-for="item in majorData"
          :key="item.majorId"
          :label="item.majorName"
          :value="item.majorId">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button size="small" type="success" @click="addStudent">新增</el-button>
    </div>
    <el-table :data="tableData" 
    :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border>
    <el-table-column prop="sno" label="学号" width="140">
    </el-table-column>
    <el-table-column prop="sname" label="姓名" width="120">
    </el-table-column>
    <el-table-column prop="gender" label="性别" width="80">
    </el-table-column>
    <el-table-column prop="age" label="年龄" width="80">
    </el-table-column>
    <el-table-column prop="grade" label="年级" width="80">
    </el-table-column>
    <el-table-column prop="deptId" label="系别" :formatter="formatDept" width="180">
    </el-table-column>
    <el-table-column prop="majorId" label="专业" :formatter="formatMajor">
    </el-table-column>
    <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="modStu(scope.row)">编辑</el-button>
          <el-popconfirm
              title="这是一段内容确定删除吗？"
              @confirm="delStu(scope.row.sno)">
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
    layout="total, sizes, prev, pager, next, jumper"
    :total=total>
  </el-pagination>
<!-- 插入和修改学生信息弹出的模态框 -->
  <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
    width="30%">
    <el-form ref="stuForm" :model="stuForm" :rules="stuRules" label-width="80px">
      <el-form-item label="学号" prop="sno">
          <el-col :span="13">
            <el-input v-model="stuForm.sno"></el-input>
          </el-col>
      </el-form-item>
      <el-form-item label="姓名" prop="sname">
          <el-col :span="13">
            <el-input v-model="stuForm.sname"></el-input>
          </el-col>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
          <el-col :span="13">
            <el-radio-group v-model="stuForm.gender">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-col>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
          <el-col :span="13">
            <el-input v-model="stuForm.age"></el-input>
          </el-col>
      </el-form-item>
      
      <el-form-item label="年级" prop="grade">
          <el-col :span="20">
            <el-select v-model="stuForm.grade" placeholder="请选择年级" style="margin-left: 5px;">
              <el-option
                v-for="item in grades"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
      </el-form-item>

      <el-form-item label="系别" prop="deptId">
          <el-col :span="20">
            <el-select v-model="stuForm.deptId" placeholder="请选择系别" style="margin-left: 5px;">
              <el-option
                v-for="item in deptData"
                :key="item.deptId"
                :label="item.deptName"
                :value="item.deptId">
              </el-option>
            </el-select>
          </el-col>
      </el-form-item>
      
      <el-form-item label="专业" prop="majorId">
          <el-col :span="20">
            <el-select v-model="stuForm.majorId" placeholder="请选择专业" style="margin-left: 5px;">
              <el-option
                v-for="item in majorData"
                :key="item.majorId"
                :label="item.majorName"
                :value="item.majorId">
              </el-option>
            </el-select>
          </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveStu">确 定</el-button>
    </span>
  </el-dialog>
  </div>
</template>

<script>
export default {
    name:"MainComponent",
    data() {
    // 年龄rule校验
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输⼊过⼤'));
      }else{
        callback();
      }
    };
    // 账号唯一性校验
    let checkDuplicate =(rule,value,callback)=>{
      if(this.stuForm.sno){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/student/findByNo?sno="+this.stuForm.sno).then(res=>res.data).then(res=>{
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
        sname:'',
        gender:'',
        grade:'',
        deptId:null,
        majorId:'',
        dialogVisible: false,   // 控制学生模态框的显示
        // 性别下拉框
        genders:[
          {
            value: '男',
            label: '男'
          },
          {
            value: '女',
            label: '女'
          },
        ],
        // 年级下拉框
        grades:[
          {
            value: '大一',
            label: '大一'
          },
          {
            value: '大二',
            label: '大二'
          },
          {
            value: '大三',
            label: '大三'
          },
          {
            value: '大四',
            label: '大四'
          },
        ],
        deptData:[],  // 存放查询到的所有dept
        majorData:[],  // 存放查询到的所有major
        total:0,   // 总记录数
        tableData: [],   // 存放后端返回的数据
        stuForm:{
          sno:'',
          sname:'',
          age:'',
          grade:'',
          gender:'',
          deptId:'',
          majorId:''
        },
        stuRules:{
          sno: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'},
            {validator:checkDuplicate,trigger: 'blur'}
          ],
          sname: [
            {required: true, message: '请输入名字', trigger: 'blur'}
          ],
          age: [
            {required: true, message: '请输入年龄', trigger: 'blur'},
            // {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
            {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
            {validator:checkAge,trigger: 'blur'}
          ]
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
        this.sname=""
        this.gender=""
        this.grade=""
        this.deptId=""
        this.majorId=""
      },
        // 清空重置新增表单
      resetForm() {
        this.$refs.stuForm.resetFields();
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

      // 将major名字，回显到页面
      // 记载所有的major信息
      formatMajor(row){
          let res = this.majorData.find(item=>{
            return item.majorId == row.majorId
          })
          return res && res.majorName
      },
      loadMajor(){
        this.$axios.get(this.$httpUrl+'/major/list').then(res=>res.data).then(res=>{
          console.log(res)
          this.majorData=res.data
        })
      },

      // 加载所有的学生信息
      loadPost(){
        this.$axios.post(this.$httpUrl+'/student/page',{
          pageSize : this.pageSize,
          pageNum : this.pageNum,
          param:{
          name:this.sname,
          gender:this.gender,
          grade:this.grade,
          deptId:this.deptId,
          majorId:this.majorId
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

      // 新增学生  1. 打开一个模态框，填写新增学生数据
      addStudent(){
        this.dialogVisible=true
        this.$nextTick(()=>{  // 异步执行
          this.resetForm()
        })
      },
      // 新增学生 2. 将数据传递给后端，执行添加操作
      toAdd(){
        this.$axios.post(this.$httpUrl+"/student/add",this.stuForm).then(res=>res.data).then(res=>{
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

      // 修改学生 1. 打开模态框，将要修改的学生信息回显到模态框
      modStu(row){
        console.log(row)
        // 弹出模式框
        this.dialogVisible=true
        this.$nextTick(()=>{    // 异步执行，避免先编辑后新增出现模式框内容异常
          // 赋值给表单
          this.stuForm.sno=row.sno
          this.stuForm.sname=row.sname
          this.stuForm.gender=row.gender
          this.stuForm.age=row.age
          this.stuForm.grade=row.grade
          this.stuForm.deptId=row.deptId
          this.stuForm.majorId=row.majorId
        })
      },
      // 修改学生 2. 将数据传递给后端，根据id执行修改操作
      toMod(){
        this.$axios.post(this.$httpUrl+"/student/update",this.stuForm).then(res=>res.data).then(res=>{
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
      // 新增学生和修改学生  2.保存新增或修改的学生信息，通过sno是否存在来判断是新增或修改（后端代码判断） 
      saveStu(){
        // 判断是否通过了rules规则
        this.$refs.stuForm.validate((valid) => {
          if (valid) {
            // 此处存疑，应该有更为准确的解决方法
            // 如果传入的sno不为空，则执行更新操作，否则为新增
            if(this.stuForm.sno){
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

      // 删除学生
      delStu(sno){
        this.$axios.get(this.$httpUrl+"/student/delete?sno="+sno).then(res=>res.data).then(res=>{
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
      this.loadMajor();
      this.loadPost();
  }
}
</script>