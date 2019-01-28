package su.wac.repository;

import su.wac.model.jpa.Course;
import su.wac.model.jpa.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Course getCourseById(Long id);
    void deleteCourseById(Long id);
    Set<Course> getCoursesByStudents(Student student);
    List<Course> findAll();
}