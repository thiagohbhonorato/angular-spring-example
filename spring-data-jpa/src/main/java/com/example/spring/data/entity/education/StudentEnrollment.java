package com.example.spring.data.entity.education;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class StudentEnrollment implements Serializable {

    @EmbeddedId
    private StudentEnrollmentPk id;

    private Date enrollmentDate;

    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "curriculum_grade_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CurriculumGrade curriculumGrade;

    public Long getEnrollmentNumber() {
        String dateNumber = new SimpleDateFormat("yyyyMMdd").format(this.enrollmentDate);
        return Long.parseLong(dateNumber + this.id.getStudentId() + "" + this.id.getCurriculumGrade());
    }

    public String getCourseName() {
        return this.curriculumGrade.getCourse().getName();
    }
}
