package com.example.spring.batch.processor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.spring.batch.dto.PeopleDto;
import com.example.spring.batch.record.PeopleRecord;

@Component
public class ItemProcessorDatabaseToFile implements ItemProcessor<PeopleDto, PeopleRecord> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PeopleRecord process(PeopleDto item) throws Exception {
        PeopleRecord peopleRecord = modelMapper.map(item, PeopleRecord.class);
        peopleRecord.setProcessingDate(new Date());
        peopleRecord.setAge(Period.between(peopleRecord.getBirthDate(), LocalDate.now()).getYears());
        return peopleRecord;
    }

}
