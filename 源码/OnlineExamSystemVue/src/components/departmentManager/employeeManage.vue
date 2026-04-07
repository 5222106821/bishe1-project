// 员工管理页面
<template>
  <div class="all">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="condition.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="所属部门">
        <el-input v-model="condition.institute" placeholder="所属部门"></el-input>
      </el-form-item>
      <el-form-item label="岗位名称">
        <el-input v-model="condition.major" placeholder="岗位名称"></el-input>
      </el-form-item>
      <el-form-item label="岗位等级">
        <el-input v-model="condition.grade" placeholder="岗位等级"></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱">
        <el-input v-model="condition.clazz" placeholder="电子邮箱"></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="condition.tel" placeholder="联系方式"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getEmployeeList"  style="font-size: 20px;"> 查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagination.records" border>
      <el-table-column prop="studentId" label="ID" width="120"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="institute" label="所属部门" width="120"></el-table-column>
      <el-table-column prop="major" label="岗位名称" width="140"></el-table-column>
      <el-table-column prop="grade" label="岗位等级" width="200"></el-table-column>
      <el-table-column prop="clazz" label="电子邮箱" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="240"></el-table-column>
      <el-table-column fixed="right" label="操作" width="260">
        <template slot-scope="scope">
          <el-button @click="editEmployee(scope.row.studentId)" type="primary" > 编辑</el-button>
          <el-button @click="deleteById(scope.row.studentId)" type="danger" > 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑员工信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="form.studentName"></el-input>
          </el-form-item>
          <el-form-item label="所属部门">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="岗位名称">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="岗位等级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱">
            <el-input v-model="form.clazz"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.sex"></el-input>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="form.tel"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" style="font-size: 20px;"> 取 消</el-button>
        <el-button type="primary" @click="submit()"> 确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {}, //保存点击以后当前试卷的信息,
      condition: {
        name: "",
        tel: "",
        grade: "",
        clazz: "",
        major: "",
        institute: "",
      }
    };
  },
  created() {
    this.getEmployeeList();
  },
  methods: {
    getEmployeeList() {
      // 根据条件获取员工列表
      var name = (this.condition.name.trim() == "" ? "@" : this.condition.name);
      var grade = (this.condition.grade.trim() == "" ? "@" : this.condition.grade);
      var tel = (this.condition.tel.trim() == "" ? "@" : this.condition.tel);
      var institute = (this.condition.institute.trim() == "" ? "@" : this.condition.institute);
      var major = (this.condition.major.trim() == "" ? "@" : this.condition.major);
      var clazz = (this.condition.clazz.trim() == "" ? "@" : this.condition.clazz);
      this.$axios(`/api/employees/${this.pagination.current}/${this.pagination.size}/${name}/${grade}/${tel}/${institute}/${major}/${clazz}`).then(res => {
        this.pagination = res.data.data;
        console.log(this.pagination)
      }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getEmployeeList();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getEmployeeList();
    },
    editEmployee(employeeId) { //修改员工信息
      this.dialogVisible = true
      this.$axios(`/api/employee/${employeeId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(employeeId) { //删除当前员工
      var _this = this;
      this.$confirm("确定删除当前员工吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/employee/${employeeId}`,
          method: 'delete',
        }).then(res => {
          this.getEmployeeList()
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      this.$axios({
        url: '/api/employee',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code ==200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getEmployeeList()
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
  }
};
</script>
<style lang="less" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
