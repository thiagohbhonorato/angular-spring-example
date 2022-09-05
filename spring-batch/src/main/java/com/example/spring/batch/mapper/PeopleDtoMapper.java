package com.example.spring.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.spring.batch.dto.PeopleDto;

public class PeopleDtoMapper implements RowMapper<PeopleDto> {

    @Override
    public PeopleDto mapRow(ResultSet rs, int rowIndex) throws SQLException {
        return PeopleDto.builder()
                .id(rs.getLong("ID"))
                .name(rs.getString("NAME"))
                .birthDate(rs.getDate("BIRTH_DATE").toLocalDate())
                .document(rs.getString("DOCUMENT"))
                .maleOrFemale(rs.getString("MALE_OR_FEMALE"))
                .build();
    }

}
