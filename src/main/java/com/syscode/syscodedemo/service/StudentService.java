package com.syscode.syscodedemo.service;

import com.syscode.syscodedemo.entity.Student;
import com.syscode.syscodedemo.remote.dto.StudentAddress;
import com.syscode.syscodedemo.remote.service.AddressServiceAdapter;
import com.syscode.syscodedemo.repository.StudentRepository;
import com.syscode.syscodedemo.rest.dto.PageResponse;
import com.syscode.syscodedemo.rest.dto.StudentCreateDto;
import com.syscode.syscodedemo.rest.dto.StudentDetailedDto;
import com.syscode.syscodedemo.rest.dto.StudentUpdateDto;
import com.syscode.syscodedemo.rest.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* Remote call will run outside the transactional context, because of autocommit settings in application.yml.
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final AddressServiceAdapter addressServiceAdapter;

    public StudentDetailedDto createStudent(StudentCreateDto studentCreateDto) {
        log.info("Saving student {}", studentCreateDto);
        return studentMapper.toDetailedDto(studentRepository.save(studentMapper.toEntity(studentCreateDto)));
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentDetailedDto> findStudents(Pageable pageable) {
        log.info("Finding students with pagination of {}", pageable);
        Page<StudentDetailedDto> repositoryResult = studentRepository.findAll(pageable)
                .map(studentMapper::toDetailedDto);
        return new PageResponse<>(
                repositoryResult.getContent(),
                repositoryResult.getPageable().getPageNumber(),
                repositoryResult.getPageable().getPageSize(),
                repositoryResult.getTotalElements());
    }

    @Transactional(readOnly = true)
    public StudentDetailedDto findStudent(Long id) {
        log.info("Finding student by id of {}", id);
        StudentAddress addressByOwnerId = addressServiceAdapter.getAddressByOwnerId(id);
        Student student = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return StudentDetailedDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .address(addressByOwnerId.getAddress())
                .build();
    }

    @Transactional
    public Student updateStudent(@NotNull Long id, StudentUpdateDto studentUpdateDto) {
        log.info("Finding student with id of {}, by updated date of: {}", id, studentUpdateDto);
        Student student = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        student.setName(studentUpdateDto.getName());
        student.setEmail(studentUpdateDto.getEmail());
        return student;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
