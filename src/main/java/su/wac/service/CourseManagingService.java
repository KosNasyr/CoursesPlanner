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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CourseManagingService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private ProgressRepository progressRepository;

    @Autowired
    private void setProgressRepository(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Autowired
    private void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    private void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.getCourseById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStudents(Long courseId, Long studentId) {
        Student student = studentRepository.getStudentById(studentId);
        Course course = courseRepository.getCourseById(courseId);
        boolean sign = false;
        for (Student s : course.getStudents()) {
            if (s.getId() == studentId) {
                System.out.println("A Student has already signed up for a course");
                sign = true;
            }
        }
        System.out.println("SIGN +++" + sign);
        if (!sign) {
            course.getStudents().add(student);
            student.getCourses().add(course);
            Integer d = course.getDaysCount();
            List<Integer> marks = new ArrayList<>();
            for (int i = 0; i < d; i++) {
                marks.add(0);
            }
            Progress p = new Progress(studentId, marks);
            p.setCourse(course);
            course.getProgresses().add(p);
            progressRepository.save(p);
            courseRepository.saveAndFlush(course);
            studentRepository.saveAndFlush(student);
            System.out.println("Student ADD!!!!");
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStudentsFromCourses(Long courseId, Long studentId) {
        Student student = studentRepository.getStudentById(studentId);
        Course course = courseRepository.getCourseById(courseId);
        Progress progress = progressRepository.findProgressByStudentIdAndCourseId(studentId, course.getId());

        for (Student s : course.getStudents()) {
            if (s.getId() == studentId) {
                course.getStudents().remove(student);
                course.getProgresses().remove(progress);
                student.getCourses().remove(course);
                progressRepository.delete(progress);
                courseRepository.saveAndFlush(course);
                studentRepository.saveAndFlush(student);
            } else {
                System.out.println("There is no such a student signed up for the course");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCourseById(Long id) {
        Course course = courseRepository.getCourseById(id);
        Set<Student> students = studentRepository.getStudentsByCourses(course);
        for (Student s : students) {
            Progress progress = progressRepository.findProgressByStudentIdAndCourseId(s.getId(), course.getId());
            s.getCourses().remove(course);
            progressRepository.delete(progress);
        }
        courseRepository.deleteCourseById(id);
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMarks(Long courseId, Set<Progress> progresses) {
        Course course = courseRepository.getCourseById(courseId);
        for (Progress m : progresses) {
            Progress oldProg = progressRepository.findProgressByStudentIdAndCourseId(m.getStudentId(), courseId);
            if (oldProg != null) {
                course.getProgresses().remove(oldProg);
                System.out.println("courseId " + oldProg.getCourseId());
                oldProg.setMarks(m.getMarks());
                course.getProgresses().add(oldProg);
                progressRepository.saveAndFlush(oldProg);
            } else {
                m.setCourse(course);
                course.getProgresses().add(m);
                progressRepository.save(m);
            }
        }
        courseRepository.saveAndFlush(course);
    }
}
