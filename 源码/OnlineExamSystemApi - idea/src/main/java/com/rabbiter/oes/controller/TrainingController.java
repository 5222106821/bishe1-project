package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.TrainingCourse;
import com.rabbiter.oes.entity.TrainingPlan;
import com.rabbiter.oes.entity.TrainingRecord;
import com.rabbiter.oes.serviceimpl.TrainingServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class TrainingController {
    @Autowired
    private TrainingServiceImpl trainingService;

    @GetMapping("/training/courses/{page}/{size}/{department}/{position}/{keyword}")
    public ApiResult<?> findCoursePage(
            @PathVariable Integer page,
            @PathVariable Integer size,
            @PathVariable String department,
            @PathVariable String position,
            @PathVariable String keyword) {
        Page<TrainingCourse> p = new Page<>(page, size);
        IPage<TrainingCourse> res = trainingService.findCoursePage(p, department, position, keyword);
        return ApiResultHandler.buildApiResult(200, "查询课程成功", res);
    }

    @GetMapping("/training/courses")
    public ApiResult<?> findAllCourses() {
        List<TrainingCourse> res = trainingService.findAllCourses();
        return ApiResultHandler.buildApiResult(200, "查询课程成功", res);
    }

    @PostMapping("/training/course")
    public ApiResult<?> addCourse(@RequestBody TrainingCourse course) {
        int res = trainingService.addCourse(course);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "添加课程成功" : "添加课程失败", res);
    }

    @PutMapping("/training/course")
    public ApiResult<?> updateCourse(@RequestBody TrainingCourse course) {
        int res = trainingService.updateCourse(course);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "更新课程成功" : "更新课程失败", res);
    }

    @DeleteMapping("/training/course/{id}")
    public ApiResult<?> deleteCourse(@PathVariable Long id) {
        int res = trainingService.deleteCourse(id);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "删除课程成功" : "删除课程失败", res);
    }

    @PostMapping("/training/course/upload")
    public ApiResult<?> uploadCourseFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ApiResultHandler.buildApiResult(400, "文件不能为空", null);
        }
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String uniqueName = UUID.randomUUID().toString().replace("-", "") + extension;
        Path uploadRoot = Paths.get(System.getProperty("user.dir"), "training-upload");
        if (!Files.exists(uploadRoot)) {
            Files.createDirectories(uploadRoot);
        }
        Path target = uploadRoot.resolve(uniqueName);
        file.transferTo(target.toFile());

        String lower = extension.toLowerCase();
        String fileType = "文档";
        if (".mp4".equals(lower) || ".avi".equals(lower) || ".mov".equals(lower) || ".mkv".equals(lower)) {
            fileType = "视频";
        } else if (".mp3".equals(lower) || ".wav".equals(lower) || ".aac".equals(lower)) {
            fileType = "音频";
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("fileName", originalName == null ? uniqueName : originalName);
        payload.put("fileUrl", "/api/training/files/" + uniqueName);
        payload.put("fileType", fileType);
        return ApiResultHandler.buildApiResult(200, "上传成功", payload);
    }

    @GetMapping("/training/files/{fileName}")
    public ResponseEntity<Resource> readTrainingFile(@PathVariable String fileName) {
        File file = Paths.get(System.getProperty("user.dir"), "training-upload", fileName).toFile();
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/training/plans/{page}/{size}/{department}/{position}")
    public ApiResult<?> findPlanPage(
            @PathVariable Integer page,
            @PathVariable Integer size,
            @PathVariable String department,
            @PathVariable String position) {
        Page<Map<String, Object>> p = new Page<>(page, size);
        IPage<Map<String, Object>> res = trainingService.findPlanPage(p, department, position);
        return ApiResultHandler.buildApiResult(200, "查询培训计划成功", res);
    }

    @PostMapping("/training/plan")
    public ApiResult<?> addPlan(@RequestBody TrainingPlan plan) {
        int res = trainingService.addPlan(plan);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "添加计划成功" : "添加计划失败", res);
    }

    @PutMapping("/training/plan")
    public ApiResult<?> updatePlan(@RequestBody TrainingPlan plan) {
        int res = trainingService.updatePlan(plan);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "更新计划成功" : "更新计划失败", res);
    }

    @DeleteMapping("/training/plan/{id}")
    public ApiResult<?> deletePlan(@PathVariable Long id) {
        int res = trainingService.deletePlan(id);
        return ApiResultHandler.buildApiResult(res > 0 ? 200 : 400, res > 0 ? "删除计划成功" : "删除计划失败", res);
    }

    @GetMapping("/training/employee/plans/{page}/{size}/{employeeId}")
    public ApiResult<?> findEmployeePlans(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer employeeId) {
        Page<Map<String, Object>> p = new Page<>(page, size);
        IPage<Map<String, Object>> res = trainingService.findEmployeePlans(p, employeeId);
        return ApiResultHandler.buildApiResult(200, "查询员工培训计划成功", res);
    }

    @PutMapping("/training/record/progress/{durationIncrement}")
    public ApiResult<?> saveProgress(@RequestBody TrainingRecord record, @PathVariable Integer durationIncrement) {
        TrainingRecord res = trainingService.saveOrUpdateRecord(record, durationIncrement);
        return ApiResultHandler.buildApiResult(200, "保存学习记录成功", res);
    }

    @GetMapping("/training/records/{page}/{size}/{employeeId}")
    public ApiResult<?> findEmployeeRecords(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer employeeId) {
        Page<Map<String, Object>> p = new Page<>(page, size);
        IPage<Map<String, Object>> res = trainingService.findEmployeeRecords(p, employeeId);
        return ApiResultHandler.buildApiResult(200, "查询学习记录成功", res);
    }

    @GetMapping("/training/records/plan/{planId}/{page}/{size}")
    public ApiResult<?> findPlanRecords(@PathVariable Long planId, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Map<String, Object>> p = new Page<>(page, size);
        IPage<Map<String, Object>> res = trainingService.findPlanRecords(p, planId);
        return ApiResultHandler.buildApiResult(200, "查询计划学习记录成功", res);
    }

    @GetMapping("/training/records/stat/{planId}")
    public ApiResult<?> findPlanStat(@PathVariable Long planId) {
        Map<String, Object> stat = trainingService.statByPlan(planId);
        return ApiResultHandler.buildApiResult(200, "查询统计成功", stat);
    }
}

