package com.example.spring.data.entity.education;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class CurriculumGradeItem implements Serializable {

    @EmbeddedId
    private CurriculumGradeItemPk id;

    private Integer workload;

    private Integer stage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "curriculum_grade_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CurriculumGrade curriculumGrade;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Subject subject;

}
