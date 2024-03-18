package com.syscode.syscodedemo.rest.controller;

import com.syscode.syscodedemo.entity.Student;
import com.syscode.syscodedemo.rest.dto.PageResponse;
import com.syscode.syscodedemo.rest.dto.StudentCreateDto;
import com.syscode.syscodedemo.rest.dto.StudentDetailedDto;
import com.syscode.syscodedemo.rest.dto.StudentUpdateDto;
import com.syscode.syscodedemo.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student")
@Tag(name = "Login Violations Endpoint")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    ResponseEntity<StudentDetailedDto> createStudent(@RequestBody StudentCreateDto studentCreateDto) {
        log.info("POST /student");
        return ResponseEntity.ok(studentService.createStudent(studentCreateDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PageableAsQueryParam
    ResponseEntity<PageResponse<StudentDetailedDto>> getStudents(@Parameter(hidden = true) Pageable pageable) {
        log.info("GET /student");
        return ResponseEntity.ok(studentService.findStudents(pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<StudentDetailedDto> getStudent(@PathVariable Long id) {
        log.info("GET /student/{}", id);
        return ResponseEntity.ok(studentService.findStudent(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Student> updateStudent(@PathVariable @NotNull Long id,
                                       @RequestBody StudentUpdateDto studentUpdateDto) {
        log.info("PUT /student/{}", id);
        return ResponseEntity.ok(studentService.updateStudent(id, studentUpdateDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteStudent(@PathVariable @NotNull Long id) {
        log.info("DELETE /student/{}", id);
        studentService.deleteStudent(id);
    }

}
