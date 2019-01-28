package su.wac.controller;

import su.wac.model.jpa.Student;
import su.wac.service.StudentManagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudentManagingService studentManagingService;

    @Autowired
    private void setStudentManagingService(StudentManagingService studentManagingService){
        this.studentManagingService = studentManagingService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id){
        return studentManagingService.getStudentById(id);
    }

    @PostMapping(value = "add",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addStudent(@RequestBody Student student){
        studentManagingService.saveStudent(student);
    }


    @PutMapping(value = "{id}")
    public void editStudent(@PathVariable("id") Long id, @RequestBody Student student){
        studentManagingService.udpateStudent(id,student);
    }

    @DeleteMapping(value = "{id}")
    public void deleteStudent(@PathVariable("id") Long id){
        studentManagingService.deleteStudentById(id);
    }


    @GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getAllStudents(){
        return studentManagingService.getAllStudents();
    }

}
