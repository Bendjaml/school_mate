package codereview.school_mate.controller;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.service.HomeworkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.hasSize;



@WebMvcTest(HomeworkController.class)
class HomeworkControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HomeworkService homeworkService;

    private final LocalDateTime testDate = LocalDateTime.now();

    @Test
    void createHomework_ShouldReturnCreated_WhenValidRequest() throws Exception {
        HomeworkRequestDto request = new HomeworkRequestDto(
                testDate, "Math homework", 1L, 1L
        );
        HomeworkResponseDto response = new HomeworkResponseDto(
                1L, testDate, "Math homework", null, null
        );

        when(homeworkService.createHomework(any(HomeworkRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/homeworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descriptionHomeworks").value("Math homework"));
    }

    @Test
    void createHomework_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        HomeworkRequestDto invalidRequest = new HomeworkRequestDto(
                null, "", null, null
        );

        mockMvc.perform(post("/api/homeworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getHomeworkById_ShouldReturnHomework_WhenExists() throws Exception {
        HomeworkResponseDto response = new HomeworkResponseDto(
                1L, testDate, "Math HW", null, null
        );

        when(homeworkService.findHomeworkById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/homeworks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getHomeworkById_ShouldReturnNotFound_WhenNotExists() throws Exception {
        when(homeworkService.findHomeworkById(999L))
                .thenThrow(new NotFoundException("Homework not found"));

        mockMvc.perform(get("/api/homeworks/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllHomeworks_ShouldReturnList() throws Exception {
        List<HomeworkResponseDto> homeworks = List.of(
                new HomeworkResponseDto(1L, testDate, "Math", null, null),
                new HomeworkResponseDto(2L, testDate, "Physics", null, null)
        );

        when(homeworkService.findAllHomework()).thenReturn(homeworks);

        mockMvc.perform(get("/api/homeworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getHomeworksByClassId_ShouldReturnFilteredList() throws Exception {
        List<HomeworkResponseDto> homeworks = List.of(
                new HomeworkResponseDto(1L, testDate, "Math", null, null)
        );

        when(homeworkService.findHomeworkByClassId(1L)).thenReturn(homeworks);

        mockMvc.perform(get("/api/homeworks/class/{classId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void updateHomework_ShouldReturnUpdated_WhenValid() throws Exception {
        HomeworkResponseDto response = new HomeworkResponseDto(
                1L, testDate, "Updated", null, null
        );

        when(homeworkService.updateHomework(eq(1L), any(HomeworkRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/homeworks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new HomeworkRequestDto(testDate, "Updated", 1L, 1L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descriptionHomeworks").value("Updated"));
    }

    @Test
    void deleteHomework_ShouldReturnNoContent() throws Exception {
        doNothing().when(homeworkService).deleteHomework(1L);

        mockMvc.perform(delete("/api/homeworks/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(homeworkService, times(1)).deleteHomework(1L);
    }
}