package su.wac.controller;

import su.wac.model.jpa.Course;
import su.wac.model.jpa.Progress;
import su.wac.service.CourseManagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("course")
public class CourseController {

    private CourseManagingService courseManagingService;

    @Autowired
    private void setCourseManagingService(CourseManagingService courseManagingService){
        this.courseManagingService= courseManagingService;
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE )
    public Course getCourseById(@PathVariable("id") Long id){
        return courseManagingService.getCourseById(id);
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCourse(@RequestBody Course course){
        courseManagingService.saveCourse(course);
    }

    @PutMapping(value = "update/{course_id},{student_id}" )
    public void updateCourse(@PathVariable("course_id") Long courseId,@PathVariable("student_id") Long studentId){
        courseManagingService.updateStudents(courseId,studentId);
    }

    @PutMapping(value = "delete_from/{course_id},{student_id}" )
    public void deleteStudentsFromCourses(@PathVariable("course_id") Long courseId,@PathVariable("student_id") Long studentId){
        courseManagingService.deleteStudentsFromCourses(courseId,studentId);
    }

    @DeleteMapping(value = "{id}")
    public void deleteCourse(@PathVariable("id") Long id){
        courseManagingService.deleteCourseById(id);
    }

    @GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getAllCourses(){
        return courseManagingService.getAllCourses();
    }

    @PutMapping(value = "update_marks/{course_id}" )
    public void updateMarks(@PathVariable("course_id") Long courseId,@RequestBody Set<Progress> progresses){
        courseManagingService.updateMarks(courseId, progresses);
    }

}