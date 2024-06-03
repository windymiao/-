<template>
    <div>
        <!-- 设置查询条件 -->
      <div style="margin-bottom: 5px">

        <el-select v-model="clazzId" placeholder="班级" style="margin-left: 5px;">
          <el-option
            v-for="item in classList"
            :key="item.clazzId"
            :label="item.clazzName"
            :value="item.clazzId">
          </el-option>
        </el-select>
        <el-button type="primary" style="margin-left: 5px;" @click="loadGet">查询</el-button>
        <el-button type="success" @click="resetParam">重置</el-button>
        <!-- <el-button size="small" type="success" @click="addSchedule">新增</el-button> -->
      </div>
      <!-- 课程表 -->
      <div class="panel">
        <el-table :data="timetable" :span-method="objectSpanMethod" border
                  :header-cell-style="{background:'#d9e5fd', color:'black', fontWeight: 1000}"
                  :cell-style="tableCellStyle"
                  @cell-click="show"
        >
          <el-table-column prop="sjd" label="时间段" width="80" align="center">
          </el-table-column>
          <el-table-column prop="jc" label="节次" width="80" align="center">
          </el-table-column>
          <el-table-column prop="mon" label="星期一" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.mon.title }} </h4>
              <!-- <h4>{{ scope.row.mon.clazzName }} </h4> -->
              <div v-html="scope.row.mon.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="tue" label="星期二" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.tue.title }}</h4>
              <div v-html="scope.row.tue.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="wed" label="星期三" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.wed.title }}</h4>
              <div v-html="scope.row.wed.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="thu" label="星期四" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.thu.title }}</h4>
              <div v-html="scope.row.thu.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="fri" label="星期五" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.fri.title }}</h4>
              <div v-html="scope.row.fri.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="sat" label="星期六" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.sat.title }}</h4>
              <div v-html="scope.row.sat.content"></div>
            </template>
          </el-table-column>
          <el-table-column prop="sun" label="星期日" align="center">
            <template slot-scope="scope">
              <h4>{{ scope.row.sun.title }}</h4>
              <div v-html="scope.row.sun.content"></div>
            </template>
          </el-table-column>
        </el-table>
      </div>
        <!-- 插入和修改课程表信息弹出的模态框 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form ref="scheduleForm" :model="scheduleForm"  label-width="80px">
        <!-- <el-form-item label="课程号" prop="cno">
            <el-col :span="13">
              <el-input v-model="scheduleForm.cno"></el-input>
            </el-col>  
        </el-form-item> -->
        <el-form-item label="课程名" prop="cno">
            <el-col :span="20">
              <el-select v-model="scheduleForm.cno" placeholder="请选择课程" style="margin-left: 5px;">
                <el-option
                  v-for="item in courseData"
                  :key="item.cno"
                  :label="item.cname"
                  :value="item.cno">
                </el-option>
              </el-select>
            </el-col>
        </el-form-item>
        <el-form-item label="星期" prop="xq">
            <el-col :span="13">
              <el-input v-model="scheduleForm.xq"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="上课时间" prop="start">
            <el-col :span="13">
              <el-input v-model="scheduleForm.start"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="下课时间" prop="end">
            <el-col :span="13">
              <el-input v-model="scheduleForm.end"></el-input>
            </el-col>
        </el-form-item>
        <!-- <el-form-item label="班级" prop="clazzId">
            <el-col :span="13">
              <el-input v-model="scheduleForm.clazzId"></el-input>
            </el-col>
        </el-form-item> -->
        <el-form-item label="简介" prop="content">
            <el-col :span="13">
              <el-input v-model="scheduleForm.content"></el-input>
            </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSchedule">确 定</el-button>
        <el-button type="danger" @click="deleteSchedule" v-if="scheduleForm.scheduleId">删除</el-button>
      </span>
    </el-dialog>
    </div>
  </template>
  <script>
  export default {
    props: {
      // 下午节次数
      afternoonLength: {
        type: [String, Number],
        default: 4
      },
      // 总节次
      length: {
        type: [String, Number],
        default: 12
      },
      //课表数据
    //   events: {
    //     type: Array,
    //     default: null
    //   }
    },
    data () {
      return {
        clazzId:"",
        // 所有的班级信息，
        classList:[],  
        // 登录的学生信息
        student:JSON.parse(sessionStorage.getItem("CurUser")),
        // 控制添加修改模态框的显示
        dialogVisible:false,
        // 为模式框设置初始的星期和上课时间
        xq:null,
        start:null,
        // 存放某个单元格的课程信息，用于修改信息时，将当前单元格的信息回显到模态框
        cellCourseData:{},
        // 添加或修改时，模态框显示的数据
        scheduleForm:{
            scheduleId:'',
            xq:null,
            start:null,
            end:null,
            cno:null,
            clazzId:'',
            content:'',
        },
        // 存放查询的课程数据
        courseData:[],
        // 课程表数据
        timetable: [],
        events: [],
        hoverOrderArr: [],
        weeks: ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun']
      }
    },
    mounted () {
      this.mergeData()
    },
    watch: {
      events: {
        handler (newVal, oldVal) {
            console.log(newVal,oldVal)
          this.mergeData()
        },
        deep: true
      }
    },
    created () {
      this.makeTimetable()
      this.loadGet()
      this.loadCourse()
      this.loadClass()
    },

    methods: {
        // 清空重置新增表单
        resetForm() {
          this.$refs.scheduleForm.resetFields();
        },
        // 点击单元格，弹出模态框
        show(row, column, cell){
            // 将scheduleForm的clazzId，设为当前clazzId
            console.log(this.clazzId)
            this.scheduleForm.clazzId=this.clazzId


            const property = column.property    // 列，第几周
            const rowIndex = this.timetable.indexOf(row);     // 行索引
            // alert(property.rowIndex)
            // console.log(property,rowIndex)
            // console.log(this.timetable[rowIndex])
            var info =  null;
            const date = this.timetable[rowIndex];     // 封装好的行信息，包含第几节课
            // console.log(date)
            this.start = date.jc;        // 为课程表模态框设置上课时间
            switch(property){   
                case "mon":
                    // info,当前单元格的具体信息，
                    info = date.mon;        // 周1的某一节课  date节课
                    this.xq = 1      // 为课程表模态框设置第几周
                    break;
                case "tue":
                    info = date.tue;        // 周2的某一节课  date节课
                    this.xq = 2      // 为课程表模态框设置第几周
                    break;
                case "wed":
                    info = date.wed;        // 周3的某一节课  date节课
                    this.xq = 3      // 为课程表模态框设置第几周
                    break;
                case "thu":
                    info = date.thu;        // 周4的某一节课  date节课
                    this.xq = 4      // 为课程表模态框设置第几周
                    break;
                case "fri":
                    info = date.fri;        // 周5的某一节课  date节课
                    this.xq = 5      // 为课程表模态框设置第几周
                    break;
                case "sat":
                    info = date.sat;        // 周6的某一节课  date节课
                    this.xq = 6      // 为课程表模态框设置第几周
                    break;
                case "sun":
                    info = date.mon;        // 周7的某一节课  date节课
                    this.xq = 7      // 为课程表模态框设置第几周
                    break;    
            }
            // alert(info)
            if(info!=null && this.clazzId!=''){
                if("clazzId" in info){
                    // alert("修改")
                    this.cellCourseData=info
                    this.mod();
                }else{
                    // alert("新增")
                    this.add();
                }
            }else{
                this.$message({
                        message:"请选择班级",
                        type:"error",
                });
            }

            // alert(this.timetable[row.jc,column.property])
            console.log(row, column, cell)
        },
        // 修改课表，第一步，打开模态框，初始化scheduleForm
        mod(){
            // 先打开模态框
            this.dialogVisible=true
            this.$nextTick(()=>{  // 异步执行，避免先编辑后新增出现模式框内容异常
                // this.resetForm()     // 没必要清空，后续初始值会覆盖原有值
                // 根据点击位置，设置初始的星期和上课时间
                this.scheduleForm.xq=this.cellCourseData.xq
                this.scheduleForm.start=this.cellCourseData.start
                this.scheduleForm.end=this.cellCourseData.end
                this.scheduleForm.cno=this.cellCourseData.cno
                this.scheduleForm.content=this.cellCourseData.content
                this.scheduleForm.scheduleId=this.cellCourseData.scheduleId
            })
        },
        // 修改课表第二步，第二步，将修改的信息传入到后端进行修改
        doMod(){
            this.$axios.post(this.$httpUrl+"/schedule/update",this.scheduleForm).then(res=>res.data).then(res=>{
                if(res.code == 200){
                    this.$message({
                        message:"修改信息成功",
                        type:"success",
                    });
                    // 关闭模态框，刷新课程表
                    this.dialogVisible=false;
                    this.loadGet();
                }else{
                    this.$message({
                        message:"修改信息失败",
                        type:"error",
                    });
                }
            })
        },
        // 添加课程表，第一步，打开模态框
        add(){
            // 先打开模态框
            this.dialogVisible=true
            this.scheduleForm.scheduleId=null
            // 清空模态框中原有的数据
            this.$nextTick(()=>{  // 异步执行
                this.resetForm()
                // 根据点击位置，设置初始的星期和上课时间
                this.scheduleForm.xq=this.xq
                this.scheduleForm.start=this.start
            })
        },
        // 添加课程表，第二步，将输入的课程表信息通过后端添加到数据库
        doAdd(){
            this.$axios.post(this.$httpUrl+"/schedule/add",this.scheduleForm).then(res=>res.data).then(res=>{
                if(res.code == 200){
                    this.$message({
                        message:"保存信息成功",
                        type:"success",
                    });
                    // 关闭模态框，刷新课程表
                    this.dialogVisible=false;
                    this.loadGet();
                }else{
                    this.$message({
                        message:"保存信息失败",
                        type:"error",
                    });
                }
            })
        },
        // 保存信息，根据id是否存在，判断时更新或新增
        saveSchedule(){
            if(this.scheduleForm.scheduleId){
                //修改
                this.doMod();
            }else{
                //新增
                this.doAdd();
            }
        },
        // 删除课表信息第一步，提示是否确认删除 （防止误操作导致的误删除，不是真正执行删除的代码）
        deleteSchedule(){
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.doDelete()
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        // 删除课表信息第二步，根据scheduleId删除课表中的课程  （真正执行删除的代码）
        doDelete(){
            this.$axios.get(this.$httpUrl+"/schedule/delete?scheduleId="+this.scheduleForm.scheduleId).then(res=>res.data).then(res=>{
                if(res.code == 200){
                    this.$message({
                        message:"修改信息成功",
                        type:"success",
                    });
                    // 关闭模态框，刷新课程表
                    this.dialogVisible=false;
                    this.loadGet();
                }else{
                    this.$message({
                        message:"修改信息失败",
                        type:"error",
                    });
                }
            })
        },

        // 加载所有的班级信息
        loadClass(){
            this.$axios.get(this.$httpUrl+"/clazz/list").then(res=>res.data).then(res=>{
                if(res.code == 200){
                    this.classList=res.data
                    console.log("classList")
                    console.log(this.classList)
                }else{
                    alert("班级信息查询失败")
                }
            })
        },
        // 像后端请求数据
        loadGet(){
            this.$axios.get(this.$httpUrl+"/schedule/list?clazzId="+this.clazzId).then(res=>res.data).then(res=>{
                console.log("=====================")
                console.log(res)
                if(res.code == 200){
                    this.events=[]
                    // 页面上显示的数据放在timetable上，所以要重新创建timetable，
                    // 否则events中数据被清空，但timetable上的数据仍然存在，导致旧的数据仍显示在页面上
                    this.makeTimetable()
                    this.$store.data=""
                    this.events=res.data
                    // console.log(this.events)
                }else{
                    this.events=[]
                    this.makeTimetable()
                    // console.log(this.events)
                }
            })
        },

        // 加载所有的course信息
        loadCourse(){
          this.$axios.get(this.$httpUrl+'/course/list').then(res=>res.data).then(res=>{
              console.log(res)
              this.courseData=res.data
          })
        },
        // 重置查询条件
        resetParam(){
          this.clazzId=""
        },
      // 单元格添加背景色
      tableCellStyle (row) {
        if (row.row[row.column.property].title !== undefined) {
          return 'background-color:rgb(24 144 255 / 80%);color: #fff; border-radius:10px'
        }
      },
      // 构造课程表完整数据
      makeTimetable () {
        this.timetable = []
        for (let i = 0; i < this.length; i++) {
          let one = {
            sjd: '',
            jc: i + 1,
            mon: {},
            tue: {},
            wed: {},
            thu: {},
            fri: {},
            sat: {},
            sun: {}
          }
          if (i < 4) {
            one.sjd = '上午'
          } else if (i > 3 && i < (this.afternoonLength + 4)) {
            one.sjd = '下午'
          } else {
            one.sjd = '晚上'
          }
          this.timetable.push(one)
        }
      },
      mergeData () {
        // 合并数据
        if (this.events.length > 0) {
          for (let i = 0; i < this.events.length; i++) {
            // 获取星期几
            let week = this.weeks[this.events[i].xq - 1]
            this.timetable[this.events[i].start - 1][week] = this.events[i]
          }
        }
      },
      objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
        console.log(column)
        if (columnIndex === 0) {
          if (rowIndex < 4) {
            if (rowIndex === 0) {
              return {
                rowspan: 4,
                colspan: 1
              }
            } else {
              return {
                rowspan: 0,
                colspan: 0
              }
            }
          } else if (rowIndex > 3 && rowIndex < (4 + this.afternoonLength)) {
            if (rowIndex === 4) {
              return {
                rowspan: this.afternoonLength,
                colspan: 1
              }
            } else {
              return {
                rowspan: 0,
                colspan: 0
              }
            }
          } else {
            if (rowIndex === (4 + this.afternoonLength)) {
              return {
                rowspan: this.length - 4 - this.afternoonLength,
                colspan: 1
              }
            } else {
              return {
                rowspan: 0,
                colspan: 0
              }
            }
          }
        }
        if (columnIndex === 2) {
          if (row.mon.title !== undefined) {
            return {
              rowspan: row.mon.end - row.mon.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 3) {
          if (row.tue.title !== undefined) {
            return {
              rowspan: row.tue.end - row.tue.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 4) {
          if (row.wed.title !== undefined) {
            return {
              rowspan: row.wed.end - row.wed.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 5) {
          if (row.thu.title !== undefined) {
            return {
              rowspan: row.thu.end - row.thu.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 6) {
          if (row.fri.title !== undefined) {
            return {
              rowspan: row.fri.end - row.fri.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 7) {
          if (row.sat.title !== undefined) {
            return {
              rowspan: row.sat.end - row.sat.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
        if (columnIndex === 8) {
          if (row.sun.title !== undefined) {
            return {
              rowspan: row.sun.end - row.sun.start + 1,
              colspan: 1
            }
          } else {
            return {
              rowspan: 1,
              colspan: 1
            }
          }
        }
      }
    }
  }
  </script>