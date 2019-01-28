package su.wac.repository;

import su.wac.model.jpa.Course;
import su.wac.model.jpa.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository  extends JpaRepository<Student,Long> {
    Student getStudentById(Long id);
    void deleteStudentById(Long id);
    Set<Student> getStudentsByCourses(Course course);
    List<Student> findAll();
    Student findStudentByEMail(String email);
}

