package su.wac.repository;

import su.wac.model.jpa.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Long> {
    List<Progress> findAll();
    Progress findProgressByStudentIdAndCourseId(Long studentId,Long courseId);
}
