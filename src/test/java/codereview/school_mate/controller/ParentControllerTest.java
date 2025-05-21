package codereview.school_mate.controller;

import codereview.school_mate.config.JwtRequestFilter;
import codereview.school_mate.dto.responce.ParentResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.service.ParentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.Mockito.when;

import codereview.school_mate.dto.request.ParentRequestDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        value = ParentController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtRequestFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
class ParentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParentService parentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById_ShouldReturnParent() throws Exception {
        when(parentService.findByIdParent(1L)).thenReturn(createParentResponseDto());

        mockMvc.perform(get("/api/parents/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Testov"));
    }

    @Test
    void findById_ShouldReturnNotFound() throws Exception {
        when(parentService.findByIdParent(1L))
                .thenThrow(new NotFoundException("Parent not found with id: 1"));

        mockMvc.perform(get("/api/parents/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Parent not found with id: 1")));
    }

    @Test
    void findAll_ShouldReturnParentsList() throws Exception {
        List<ParentResponseDto> parents = List.of(
                createParentResponseDto(),
                createParentResponseDto()
        );

        when(parentService.findAllParent()).thenReturn(parents);

        mockMvc.perform(get("/api/parents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Testov"));
    }

    @Test
    void update_ShouldReturnUpdatedParent() throws Exception {
        when(parentService.updateParent(eq(1L), any(ParentRequestDto.class))).thenReturn(createParentResponseDto());

        mockMvc.perform(put("/api/parents/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createParentRequestDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(parentService).deleteParent(1L);

        mockMvc.perform(delete("/api/parents/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(parentService, times(1)).deleteParent(1L);
    }

    private ParentRequestDto createParentRequestDto() {
        ParentRequestDto parentRequestDto = new ParentRequestDto();
        parentRequestDto.setPatronymic("Testovich");
        parentRequestDto.setName("Testov");
        parentRequestDto.setSurname("Test");
        return parentRequestDto;
    }

    private ParentResponseDto createParentResponseDto() {
        ParentResponseDto parentResponseDto = new ParentResponseDto();
        parentResponseDto.setId(1L);
        parentResponseDto.setPatronymic("Testov");
        parentResponseDto.setName("Testov");
        parentResponseDto.setSurname("Test");
        return parentResponseDto;
    }
}