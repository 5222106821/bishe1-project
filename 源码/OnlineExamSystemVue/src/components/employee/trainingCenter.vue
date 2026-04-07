<template>
  <div class="table">
    <p class="top-title">培训中心</p>
    <section class="content-el">
      <el-table :data="pagination.records" border>
        <el-table-column prop="planName" label="培训计划" />
        <el-table-column prop="courseName" label="课程" />
        <el-table-column prop="department" label="部门" width="120" />
        <el-table-column prop="position" label="岗位" width="120" />
        <el-table-column prop="learningDuration" label="学习时长(分钟)" width="130" />
        <el-table-column prop="completionPercent" label="完成率(%)" width="110" />
        <el-table-column label="课件" width="100">
          <template slot-scope="scope">
            <el-link :href="scope.row.fileUrl" target="_blank" type="primary">查看</el-link>
          </template>
        </el-table-column>
        <el-table-column label="参与培训" width="240">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row._duration" :min="0" :max="300" :step="10" size="mini" />
            <el-input-number v-model="scope.row._percent" :min="0" :max="100" :step="5" size="mini" style="margin-left:6px;" />
            <el-button type="primary" size="mini" @click="save(scope.row)" style="margin-left:6px;">提交</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center" class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6,10]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next"
          :total="pagination.total" />
      </el-row>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: { current: 1, size: 6, total: 0, records: [] }
    };
  },
  created() {
    this.getPlans();
  },
  methods: {
    getPlans() {
      const employeeId = this.$cookies.get("cid");
      this.$axios(`/api/training/employee/plans/${this.pagination.current}/${this.pagination.size}/${employeeId}`).then(res => {
        const pageData = res.data.data;
        pageData.records = (pageData.records || []).map(it => ({
          ...it,
          _duration: 30,
          _percent: Number(it.completionPercent || 0)
        }));
        this.pagination = pageData;
      });
    },
    handleSizeChange(val) { this.pagination.size = val; this.getPlans(); },
    handleCurrentChange(val) { this.pagination.current = val; this.getPlans(); },
    save(row) {
      const employeeId = this.$cookies.get("cid");
      this.$axios({
        url: `/api/training/record/progress/${row._duration || 0}`,
        method: "put",
        data: {
          planId: row.planId,
          courseId: row.courseId,
          employeeId: employeeId,
          completionPercent: row._percent || 0
        }
      }).then(() => {
        this.$message.success("学习记录已保存");
        this.getPlans();
      });
    }
  }
};
</script>

<style scoped>
.table { width: 980px; margin: 0 auto; }
.top-title { margin: 20px; font-size: 22px; }
.content-el { background: #fff; padding: 20px; }
.pagination { padding-top: 16px; }
</style>

