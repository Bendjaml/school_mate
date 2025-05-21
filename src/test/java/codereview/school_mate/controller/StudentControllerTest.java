package codereview.school_mate.controller;

import codereview.school_mate.config.JwtRequestFilter;
import codereview.school_mate.dto.request.StudentRequestDto;
import codereview.school_mate.dto.responce.StudentResponseDto;
import codereview.school_mate.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

@WebMvcTest(
        value = StudentController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtRequestFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @Test
    void findById_ShouldReturnStudent() throws Exception {
        StudentResponseDto responseDto = new StudentResponseDto(
                1L, "Maria", "Petrova", "Sergeevna", null, null
        );

        when(studentService.findByIdStudent(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/students/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname").value("Petrova"));
    }

    @Test
    void findAll_ShouldReturnStudentsList() throws Exception {
        List<StudentResponseDto> students = List.of(
                new StudentResponseDto(1L, "Ivan", "Ivanov", "Ivanovich", null, null),
                new StudentResponseDto(2L, "Maria", "Petrova", "Sergeevna", null, null)
        );

        when(studentService.findAllStudent()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].patronymic").value("Ivanovich"));
    }

    @Test
    void update_ShouldReturnUpdatedStudent() throws Exception {
        StudentResponseDto responseDto = new StudentResponseDto(
                1L, "Updated", "Name", "Patronymic", null, null
        );

        when(studentService.updateStudent(eq(1L), any(StudentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/students/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new StudentRequestDto("New", "Name", "Patronymic", 1L, 1L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudent(1L);
    }
}