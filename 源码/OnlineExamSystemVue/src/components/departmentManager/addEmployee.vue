<!--
 * @Description: 
 * @Author: 
 * @Date: 2024-03-08 20:38:49
-->
<!-- 新增员工 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="form.studentName"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex"></el-input>
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
      <el-form-item label="电话号码">
        <el-input v-model="form.tel"></el-input>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="form.cardId"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.pwd"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()"> 立即创建</el-button>
        <el-button type="danger" @click="cancel()"> 取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: { //表单数据初始化
        studentName: null,
        grade: null,
        major: null,
        clazz: null,
        institute: null,
        tel: null,
        email: null,
        pwd: null,
        cardId: null,
        sex: null,
        role: 2
      }
    };
  },
  methods: {
    onSubmit() { //数据提交
      if (this.form.studentName == null || this.form.studentName.trim() == "") {
        this.$message({
          message: '请输入姓名',
          type: 'error'
        })
        return
      }
      if (this.form.grade == null || this.form.grade.trim() == "") {
        this.$message({
          message: '请输入岗位等级',
          type: 'error'
        })
        return
      }
      if (this.form.major == null || this.form.major.trim() == "") {
        this.$message({
          message: '请输入岗位名称',
          type: 'error'
        })
        return
      }
      if (this.form.clazz == null || this.form.clazz.trim() == "") {
        this.$message({
          message: '请输入电子邮箱',
          type: 'error'
        })
        return
      }
      if (this.form.institute == null || this.form.institute.trim() == "") {
        this.$message({
          message: '请输入所属部门',
          type: 'error'
        })
        return
      }
      if (this.form.tel == null || this.form.tel.trim() == "") {
        this.$message({
          message: '请输入手机号码',
          type: 'error'
        })
        return
      }
      if (this.form.email == null || this.form.email.trim() == "") {
        this.$message({
          message: '请输入邮箱',
          type: 'error'
        })
        return
      }
      if (this.form.pwd == null || this.form.pwd.trim() == "") {
        this.$message({
          message: '请输入密码',
          type: 'error'
        })
        return
      }
      if (this.form.sex == null || this.form.sex.trim() == "") {
        this.$message({
          message: '请输入性别',
          type: 'error'
        })
        return
      }
      this.$axios({
        url: '/api/employee',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        if (res.data.code == 200) {
          this.$message({
            message: '数据添加成功',
            type: 'success'
          })
          this.$router.push({ path: '/employeeManage' })
        }
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },

  }
};
</script>
<style lang="less" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

