<!-- 考试管理功能介绍 -->
<template>
  <section class="description">
    <p class="title">考试管理功能介绍</p>
    <p class="content">管理员发布了考试,员工才可以在主页面看到相应的考试信息。
      有考试安排表以后,才能给该次考试添加题目,对应数据表是exammanage。
      该表保存该次考试,试卷名称,考试时间,岗位名称,所属部门等等信息。
    </p>
  </section>
</template>

<style lang="less" scoped>
.description {
  margin-left: 40px;
  .title {
    font-size: 22px;
    font-weight: 400;
    color: rgb(31, 47, 61);
  }
  .content {
    width: 600px;
    background-color: rgb(236, 248, 255);
    padding: 16px 32px;
    border-radius: 4px;
    border-left: 5px solid rgb(80, 191, 255);
    margin: 20px 0px;
  }
}
</style>
