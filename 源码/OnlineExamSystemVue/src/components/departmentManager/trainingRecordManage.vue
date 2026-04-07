<template>
  <div class="all">
    <el-form :inline="true">
      <el-form-item label="培训计划">
        <el-select v-model="planId" placeholder="请选择培训计划" style="width: 320px;">
          <el-option v-for="p in planOptions" :key="p.id" :label="p.planName" :value="p.id" />
        </el-select>
      </el-form-item>
      <el-form-item><el-button type="primary" @click="query">查询</el-button></el-form-item>
    </el-form>

    <el-card v-if="stat">
      <span>参与人数：{{ stat.totalEmployees || 0 }}</span>
      <span style="margin-left:20px;">完成人数：{{ stat.completedEmployees || 0 }}</span>
      <span style="margin-left:20px;">平均完成率：{{ stat.avgCompletionPercent || 0 }}%</span>
      <span style="margin-left:20px;">累计学习时长：{{ stat.totalLearningDuration || 0 }} 分钟</span>
    </el-card>

    <el-table :data="pagination.records" border style="margin-top: 12px;">
      <el-table-column prop="employeeId" label="员工ID" width="100" />
      <el-table-column prop="studentName" label="员工姓名" width="120" />
      <el-table-column prop="institute" label="部门" width="140" />
      <el-table-column prop="major" label="岗位" width="140" />
      <el-table-column prop="learningDuration" label="学习时长(分钟)" width="140" />
      <el-table-column prop="completionPercent" label="完成率(%)" width="120" />
      <el-table-column label="完成状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.completed == 1 ? 'success' : 'warning'">
            {{ scope.row.completed == 1 ? '已完成' : '进行中' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="lastStudyTime" label="最近学习时间" />
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      planOptions: [],
      planId: null,
      stat: null,
      pagination: { current: 1, size: 6, total: 0, records: [] }
    };
  },
  created() {
    this.getPlans();
  },
  methods: {
    getPlans() {
      this.$axios("/api/training/plans/1/9999/@/@").then(res => {
        this.planOptions = (res.data.data && res.data.data.records) || [];
      });
    },
    query() {
      if (!this.planId) return this.$message.error("请选择培训计划");
      this.$axios(`/api/training/records/stat/${this.planId}`).then(res => { this.stat = res.data.data; });
      this.$axios(`/api/training/records/plan/${this.planId}/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      });
    },
    handleSizeChange(val) { this.pagination.size = val; this.query(); },
    handleCurrentChange(val) { this.pagination.current = val; this.query(); }
  }
};
</script>

<style scoped>
.all { padding: 0 40px; }
.page { margin-top: 16px; text-align: center; }
</style>

