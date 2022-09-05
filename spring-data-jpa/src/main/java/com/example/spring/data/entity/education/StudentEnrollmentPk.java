package com.example.spring.data.entity.education;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class StudentEnrollmentPk implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "curriculum_grade_id")
    private Long curriculumGrade;
}
