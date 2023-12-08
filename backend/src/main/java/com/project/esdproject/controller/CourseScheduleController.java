package com.project.esdproject.controller;

import com.project.esdproject.model.CourseSchedule;
import com.project.esdproject.Service.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseSchedule") // Base path for this controller
public class CourseScheduleController {

    @Autowired
    private CourseScheduleService courseScheduleService;

    // working
    @GetMapping("/{emp_id}/get")
    public ResponseEntity<List<CourseSchedule>> getCourseSchedule(@PathVariable("emp_id") Integer id) {
        List<CourseSchedule> courseSchedule = courseScheduleService.getCourseSchedule(id);
        return ResponseEntity.ok().body(courseSchedule);
    }
}
