<template>
  <div class="all">
    <el-form :inline="true">
      <el-form-item label="部门"><el-input v-model="condition.department" /></el-form-item>
      <el-form-item label="岗位"><el-input v-model="condition.position" /></el-form-item>
      <el-form-item label="课程"><el-input v-model="condition.keyword" /></el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCourses">查询</el-button>
        <el-button type="success" @click="openAdd">新增课程</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagination.records" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="fileType" label="课件类型" width="100" />
      <el-table-column prop="department" label="部门" width="140" />
      <el-table-column prop="position" label="岗位" width="140" />
      <el-table-column label="课件" width="120">
        <template slot-scope="scope">
          <el-link :href="scope.row.fileUrl" target="_blank" type="primary">查看</el-link>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="remove(scope.row.id)">删除</el-button>
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

    <el-dialog :title="form.id ? '编辑课程' : '新增课程'" :visible.sync="dialogVisible" width="520px">
      <el-form label-width="90px">
        <el-form-item label="课程名称"><el-input v-model="form.courseName" /></el-form-item>
        <el-form-item label="部门"><el-input v-model="form.department" /></el-form-item>
        <el-form-item label="岗位"><el-input v-model="form.position" /></el-form-item>
        <el-form-item label="课程简介"><el-input type="textarea" v-model="form.description" /></el-form-item>
        <el-form-item label="课件上传">
          <input type="file" @change="onFileChange" />
          <div v-if="form.fileName" style="margin-top:8px;">{{ form.fileName }}</div>
        </el-form-item>
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
      condition: { department: "", position: "", keyword: "" },
      dialogVisible: false,
      form: {},
      uploadFile: null
    };
  },
  created() {
    this.getCourses();
  },
  methods: {
    getCourses() {
      const d = this.condition.department.trim() || "@";
      const p = this.condition.position.trim() || "@";
      const k = this.condition.keyword.trim() || "@";
      this.$axios(`/api/training/courses/${this.pagination.current}/${this.pagination.size}/${d}/${p}/${k}`).then(res => {
        this.pagination = res.data.data;
      });
    },
    handleSizeChange(val) { this.pagination.size = val; this.getCourses(); },
    handleCurrentChange(val) { this.pagination.current = val; this.getCourses(); },
    openAdd() { this.form = {}; this.uploadFile = null; this.dialogVisible = true; },
    openEdit(row) { this.form = { ...row }; this.uploadFile = null; this.dialogVisible = true; },
    onFileChange(e) {
      const files = e.target.files;
      if (!files || !files.length) return;
      this.uploadFile = files[0];
      this.form.fileName = this.uploadFile.name;
    },
    async uploadIfNeeded() {
      if (!this.uploadFile) return;
      const fd = new FormData();
      fd.append("file", this.uploadFile);
      const res = await this.$axios.post("/api/training/course/upload", fd, {
        headers: { "Content-Type": "multipart/form-data" }
      });
      const data = res.data.data;
      this.form.fileName = data.fileName;
      this.form.fileUrl = data.fileUrl;
      this.form.fileType = data.fileType;
    },
    async submit() {
      if (!this.form.courseName) return this.$message.error("请输入课程名称");
      await this.uploadIfNeeded();
      const method = this.form.id ? "put" : "post";
      const url = "/api/training/course";
      this.$axios({ url, method, data: this.form }).then(() => {
        this.$message.success("保存成功");
        this.dialogVisible = false;
        this.getCourses();
      });
    },
    remove(id) {
      this.$confirm("确认删除该课程吗？", "提示").then(() => {
        this.$axios.delete(`/api/training/course/${id}`).then(() => {
          this.$message.success("删除成功");
          this.getCourses();
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

