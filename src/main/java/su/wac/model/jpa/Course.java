package su.wac.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;

@Entity
@Table(name = "course")
@Getter
@Setter
@ToString
public class Course extends CoursesObject {

    public Course(){
    }

    public Course(String courseTitle, Date beginning, Date ending, Integer daysCount) {
        this.courseTitle = courseTitle;
        this.beginning = beginning;
        this.ending = ending;
        this.daysCount = daysCount;
    }

    public Course(Long id, String courseTitle, Date beginning, Date ending, Integer daysCount) {
        super(id);
        this.courseTitle = courseTitle;
        this.beginning = beginning;
        this.ending = ending;
        this.daysCount = daysCount;
    }

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "beginning")
    private Date beginning;

    @Column(name = "ending")
    private Date ending;

    @Column(name = "days_count")
    private Integer daysCount;

    @ManyToMany(mappedBy="courses", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnoreProperties("courses")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties("course")
    private Set<Progress> progresses = new HashSet<>();

}