<template>
  <div class="all">
    <el-form :inline="true">
      <el-form-item label="部门"><el-input v-model="condition.department" /></el-form-item>
      <el-form-item label="岗位"><el-input v-model="condition.position" /></el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getPlans">查询</el-button>
        <el-button type="success" @click="openAdd">新增计划</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagination.records" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="planName" label="计划名称" />
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="department" label="部门" width="140" />
      <el-table-column prop="position" label="岗位" width="140" />
      <el-table-column prop="startTime" label="开始时间" width="170" />
      <el-table-column prop="endTime" label="结束时间" width="170" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="openEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="page"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6,10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next"
      :total="pagination.total" />

    <el-dialog :title="form.id ? '编辑计划' : '新增计划'" :visible.sync="dialogVisible" width="540px">
      <el-form label-width="95px">
        <el-form-item label="计划名称"><el-input v-model="form.planName" /></el-form-item>
        <el-form-item label="培训课程">
          <el-select v-model="form.courseId" placeholder="请选择课程" style="width:100%;">
            <el-option v-for="c in courses" :key="c.id" :label="c.courseName" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="部门"><el-input v-model="form.department" /></el-form-item>
        <el-form-item label="岗位"><el-input v-model="form.position" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" style="width:100%;" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" style="width:100%;" /></el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: { current: 1, size: 6, total: 0, records: [] },
      condition: { department: "", position: "" },
      dialogVisible: false,
      form: {},
      courses: []
    };
  },
  created() {
    this.getPlans();
    this.getCourses();
  },
  methods: {
    getCourses() {
      this.$axios("/api/training/courses").then(res => { this.courses = res.data.data || []; });
    },
    getPlans() {
      const d = this.condition.department.trim() || "@";
      const p = this.condition.position.trim() || "@";
      this.$axios(`/api/training/plans/${this.pagination.current}/${this.pagination.size}/${d}/${p}`).then(res => {
        this.pagination = res.data.data;
      });
    },
    handleSizeChange(val) { this.pagination.size = val; this.getPlans(); },
    handleCurrentChange(val) { this.pagination.current = val; this.getPlans(); },
    openAdd() { this.form = {}; this.dialogVisible = true; },
    openEdit(row) { this.form = { ...row }; this.dialogVisible = true; },
    submit() {
      if (!this.form.planName || !this.form.courseId) return this.$message.error("请完善计划信息");
      const method = this.form.id ? "put" : "post";
      this.$axios({ url: "/api/training/plan", method, data: this.form }).then(() => {
        this.$message.success("保存成功");
        this.dialogVisible = false;
        this.getPlans();
      });
    },
    remove(id) {
      this.$confirm("确认删除该计划吗？", "提示").then(() => {
        this.$axios.delete(`/api/training/plan/${id}`).then(() => {
          this.$message.success("删除成功");
          this.getPlans();
        });
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
.all { padding: 0 40px; }
.page { margin-top: 16px; text-align: center; }
</style>

