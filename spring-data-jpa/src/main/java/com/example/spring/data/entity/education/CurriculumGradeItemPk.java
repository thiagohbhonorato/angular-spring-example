package com.example.spring.data.entity.education;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CurriculumGradeItemPk implements Serializable {

    @Column(name = "curriculum_grade_id")
    private Long curriculumGradeId;

    @Column(name = "subject_id")
    private Long subject;

}
