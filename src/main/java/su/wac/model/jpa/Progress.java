package su.wac.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "progress")
@Getter
@Setter
@ToString
public class Progress extends CoursesObject {

    public Progress(){
    }

    public Progress(Long studentId,  List<Integer> marks) {
        this.studentId = studentId;
        this.marks = marks;
    }

    public Progress(Long id, Long studentId, List<Integer> marks) {
        super(id);
        this.studentId = studentId;
        this.marks = marks;
    }

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id",insertable = false,updatable = false)
    private Long courseId;

    @Column(name = "marks")
    @Type(type = "org.pflb.model.business.ShortArrayType")
    private List<Integer> marks;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("progresses")
    private Course course;

}
