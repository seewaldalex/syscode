package com.syscode.syscodedemo.rest.mapper;

import com.syscode.syscodedemo.entity.Student;
import com.syscode.syscodedemo.rest.dto.StudentCreateDto;
import com.syscode.syscodedemo.rest.dto.StudentDetailedDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    Student toEntity(StudentCreateDto dto);

    StudentDetailedDto toDetailedDto(Student entity);

}
