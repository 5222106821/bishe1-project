import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const VueRouterPush = Router.prototype.push

Router.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}
let router = new Router({
  routes: [
    {
      path: '/',
      name: 'login', //登录界面
      component: () => import('@/components/common/login')
    },
    {
      path: '/index', //部门管理员主页
      component: () => import('@/components/admin/index'),
      children: [
        {
          path: '/', //首页默认路由
          component: () => import('@/components/common/hello')
        },
        {
          path:'/grade', //员工成绩
          component: () => import('@/components/charts/grade')
        },
        {
          path: '/selectExamToPart', //员工分数段
          component: () => import('@/components/departmentManager/selectExamToPart')
        },
        {
          path: '/scorePart',
          component: () => import('@/components/charts/scorePart')
        },
        {
          path: '/allStudentsGrade', //所有员工成绩统计
          component: () => import('@/components/departmentManager/allEmployeesGrade')
        },
        // {
        //   path: '/examDescription', //考试管理功能描述
        //   component: () => import('@/components/teacher/examDescription')
        // },
        {
          path: '/selectExam', //查询所有考试
          component: () => import('@/components/departmentManager/selectExam')
        },
        {
          path: '/addExam', //添加考试
          component: () => import('@/components/departmentManager/addExam')
        },
        // {
        //   path: '/answerDescription', //题库管理功能介绍
        //   component: ()=> import('@/components/teacher/answerDescription')
        // },
        {
          path: '/selectAnswer', //查询所有题库
          component: () => import('@/components/departmentManager/selectAnswer')
        },
        {
          path: '/addAnswer', //增加题库主界面
          component: () => import('@/components/departmentManager/addAnswer')
        },
        {
          path: '/editAnswerChildren', //编辑题库主界面
          component: () => import('@/components/departmentManager/editAnswerChildren')
        },
        {
          path: '/addAnswerChildren', //点击试卷跳转到添加题库页面
          component: () => import('@/components/departmentManager/addAnswerChildren')
        },
        {
          path: '/employeeManage', //员工管理界面
          component: () => import('@/components/departmentManager/employeeManage')
        },
        {
          path: '/addEmployee', //添加员工
          component: () => import('@/components/departmentManager/addEmployee')
        },
        {
          path: '/departmentManagerManage',
          component: () => import('@/components/admin/departmentManagerManage')
        },
        {
          path: '/addDepartmentManager',
          component: () => import ('@/components/admin/addDepartmentManager')
        },
        {
          path: '/trainingCourseManage',
          component: () => import('@/components/departmentManager/trainingCourseManage')
        },
        {
          path: '/trainingPlanManage',
          component: () => import('@/components/departmentManager/trainingPlanManage')
        },
        {
          path: '/trainingRecordManage',
          component: () => import('@/components/departmentManager/trainingRecordManage')
        },
        // 兼容旧路径
        {
          path: '/studentManage',
          redirect: '/employeeManage'
        },
        {
          path: '/addStudent',
          redirect: '/addEmployee'
        },
        {
          path: '/teacherManage',
          redirect: '/departmentManagerManage'
        },
        {
          path: '/addTeacher',
          redirect: '/addDepartmentManager'
        }
      ]
    },
    {
      path: '/student',
      component: () => import('@/components/employee/index'),
      children: [
        {path:"/",component: ()=> import('@/components/employee/myExam')},
        {path:'/startExam', component: () => import('@/components/employee/startExam')},
        {path: '/manager', component: () => import('@/components/employee/manager')},
        {path: '/examMsg', component: () => import('@/components/employee/examMsg')},
        {path: '/message', component: () => import('@/components/employee/message')},
        {path: '/studentScore', component: () => import("@/components/employee/answerScore")},
        {path: '/scoreTable', component: () => import("@/components/employee/scoreTable")},
        {path: '/trainingCenter', component: () => import("@/components/employee/trainingCenter")}
      ]
    },
    {path: '/answer',component: () => import('@/components/employee/answer')}
  ]
})
router.beforeEach((to, from, next) => {
  // 判断是否是刷新操作
  if (from.path != '/examMsg' && to.path === '/answer') {
    // 执行回退操作
    next({ path: '/student' })
  } else if (!(from.path === '/student' || from.path === '/startExam') && to.path === '/examMsg') {
    // 执行回退操作
    next({ path: '/student' })
  } else {
    next()
  }
})
export default router