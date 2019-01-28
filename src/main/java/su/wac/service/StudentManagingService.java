package su.wac.service;

import su.wac.model.jpa.Course;
import su.wac.model.jpa.Progress;
import su.wac.model.jpa.Student;
import su.wac.repository.CourseRepository;
import su.wac.repository.ProgressRepository;
import su.wac.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class StudentManagingService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ProgressRepository progressRepository;

    @Autowired
    private void setProgressRepository(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Autowired
    private void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    private void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Transactional
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.getStudentById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStudentById(Long id) {

        Student student = studentRepository.getStudentById(id);
        Set<Course> courses = courseRepository.getCoursesByStudents(student);

        for (Course c : courses) {
            Progress progress = progressRepository.findProgressByStudentIdAndCourseId(student.getId(),c.getId());
            c.getProgresses().remove(progress);
            c.getStudents().remove(student);
            progressRepository.delete(progress);
        }
        studentRepository.deleteStudentById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void udpateStudent(Long id,Student newStudent){
        Student oldStudent = studentRepository.getStudentById(id);
        oldStudent.setFullName(newStudent.getFullName());
        oldStudent.setEMail(newStudent.getEMail());
        oldStudent.setTelNumber(newStudent.getTelNumber());
        studentRepository.saveAndFlush(oldStudent);
    }

}
