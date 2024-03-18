package com.syscode.syscodedemo.rest;

import com.google.gson.reflect.TypeToken;
import com.syscode.syscodedemo.entity.Student;
import com.syscode.syscodedemo.integration.util.EndpointTest;
import com.syscode.syscodedemo.repository.StudentRepository;
import com.syscode.syscodedemo.rest.dto.PageResponse;
import com.syscode.syscodedemo.rest.dto.StudentCreateDto;
import com.syscode.syscodedemo.rest.dto.StudentDetailedDto;
import com.syscode.syscodedemo.rest.dto.StudentUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileIntegrationTest extends EndpointTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DirtiesContext
    void createStudent() throws Exception {
        final var studentCreateDto = StudentCreateDto.builder().name("Test Name").email("test@gmail.com").build();

        MockHttpServletResponse mvcResult =
                mockMvc.perform(
                    post("/student")
                        .content(toJson(studentCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var result = readResponse(mvcResult, StudentDetailedDto.class);

        assertThat(result)
            .returns(studentCreateDto.getName(), StudentDetailedDto::getName)
            .returns(studentCreateDto.getEmail(), StudentDetailedDto::getEmail)
            .extracting(StudentDetailedDto::getId)
            .isNotNull()
            .isInstanceOf(Long.class);
    }

    @Test
    @DirtiesContext
    void getAllStudents() throws Exception {
        final var student1 = Student.builder().name("Test Name 1").email("test1@gmail.com").build();
        final var student2 = Student.builder().name("Test Name 2").email("test2@gmail.com").build();
        final var student3 = Student.builder().name("Test Name 3").email("test3@gmail.com").build();
        final var student4 = Student.builder().name("Test Name 4").email("test4@gmail.com").build();
        final var student5 = Student.builder().name("Test Name 5").email("test5@gmail.com").build();

        studentRepository.saveAll(List.of(student1, student2, student3, student4, student5));

        var mvcResult =
            mockMvc.perform(
                    get("/student").queryParam("page", "1").queryParam("size", "2")
                ).andExpect(status().isOk()
                ).andReturn()
                .getResponse();

        PageResponse<StudentDetailedDto> result =
                readResponse(mvcResult, new TypeToken<PageResponse<StudentDetailedDto>>(){}.getType());

        assertAll(
            () -> assertEquals(5, result.getTotal()),
            () -> assertEquals(2, result.getContent().size()),
            () -> assertThat(result.getContent())
                    .filteredOn(filterByEmail(student3.getEmail()))
                    .first()
                    .returns(student3.getName(), StudentDetailedDto::getName)
                    .returns(student3.getEmail(), StudentDetailedDto::getEmail)
                    .extracting(StudentDetailedDto::getId)
                    .isNotNull()
                    .isInstanceOf(Long.class),
            () -> assertThat(result.getContent())
                    .filteredOn(filterByEmail(student4.getEmail()))
                    .first()
                    .returns(student4.getName(), StudentDetailedDto::getName)
                    .returns(student4.getEmail(), StudentDetailedDto::getEmail)
                    .extracting(StudentDetailedDto::getId)
                    .isNotNull()
                    .isInstanceOf(Long.class)
        );

    }

    @Test
    @DirtiesContext
    void getStudentById() throws Exception {
        final var student = Student.builder().name("Test Name 1").email("test1@gmail.com").build();
        studentRepository.save(student);

        var mvcResult =
            mockMvc.perform(
                    get("/student/{id}", "1")
                ).andExpect(status().isOk()
                ).andReturn()
                .getResponse();

        StudentDetailedDto result = readResponse(mvcResult, new TypeToken<StudentDetailedDto>() {}.getType());

        assertThat(result)
            .returns(student.getName(), StudentDetailedDto::getName)
            .returns(student.getEmail(), StudentDetailedDto::getEmail)
            .extracting(StudentDetailedDto::getId)
            .isNotNull()
            .isInstanceOf(Long.class);
    }

    @Test
    @DirtiesContext
    void updateStudentById() throws Exception {
        final var beforeUpdateName = "Test Name 1";
        final var beforeUpdateEmail = "test1@gmail.com";
        var persistedStudent = studentRepository.save(
                Student.builder()
                    .name(beforeUpdateName)
                    .email(beforeUpdateEmail)
                    .build()
        );

        // asserting precondition
        assertThat(studentRepository.findById(1L).orElseThrow())
                .returns(persistedStudent.getName(), Student::getName)
                .returns(persistedStudent.getEmail(), Student::getEmail)
                .returns(persistedStudent.getId(), Student::getId);

        final var studentUpdateDto = StudentUpdateDto.builder()
                .name("New Name")
                .email("test_new@gmail.com")
                .build();

        var mvcResult =
            mockMvc.perform(
                    put("/student/{id}", "1")
                        .content(toJson(studentUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()
                ).andReturn()
                .getResponse();

        StudentDetailedDto result = readResponse(mvcResult, new TypeToken<StudentDetailedDto>() {}.getType());

        assertAll(
            () -> assertThat(result)
                .returns(studentUpdateDto.getName(), StudentDetailedDto::getName)
                .returns(studentUpdateDto.getEmail(), StudentDetailedDto::getEmail)
                .extracting(StudentDetailedDto::getId)
                .isNotNull()
                .isInstanceOf(Long.class),
            () -> assertThat(studentRepository.findById(1L).orElseThrow())
                .returns(studentUpdateDto.getName(), Student::getName)
                .returns(studentUpdateDto.getEmail(), Student::getEmail)
                .extracting(Student::getId)
                .isNotNull()
                .isInstanceOf(Long.class)
        );

    }

    @Test
    @DirtiesContext
    void deleteStudentById() throws Exception {
        final var student = Student.builder().name("Test Name 1").email("test1@gmail.com").build();
        studentRepository.save(student);

        // asserting precondition
        assertThat(studentRepository.findById(1L).orElseThrow())
                .returns(student.getName(), Student::getName)
                .returns(student.getEmail(), Student::getEmail)
                .extracting(Student::getId)
                .isNotNull()
                .isInstanceOf(Long.class);

        mockMvc.perform(
                delete("/student/{id}", "1")
            ).andExpect(status().is2xxSuccessful()
            ).andReturn()
            .getResponse();

        assertThat(studentRepository.findById(1L)).isEmpty();
    }

    private static Predicate<StudentDetailedDto> filterByEmail(String email) {
        return studentDetailedDto -> studentDetailedDto.getEmail().equals(email);
    }

}
