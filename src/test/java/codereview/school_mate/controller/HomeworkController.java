package codereview.school_mate.controller;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeworkController.class)
class HomeworkControllerIntegrationTest {

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
                testDate, "Do exercises", 1L, 1L
        );

        HomeworkResponseDto response = new HomeworkResponseDto(
                1L, testDate, "Do exercises", null, null
        );

        when(homeworkService.createHomework(any(HomeworkRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/homeworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descriptionHomeworks").value("Do exercises"));
    }

    @Test
    void getHomeworkByClassId_ShouldReturnList_WhenClassExists() throws Exception {
        List<HomeworkResponseDto> response = List.of(
                new HomeworkResponseDto(1L, testDate, "Math HW", null, null),
                new HomeworkResponseDto(2L, testDate, "Physics HW", null, null)
        );

        when(homeworkService.findHomeworkByClassId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/homeworks/class/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deleteHomework_ShouldReturnNoContent() throws Exception {
        Long homeworkId = 1L;
        doNothing().when(homeworkService).deleteHomework(homeworkId);

        mockMvc.perform(delete("/api/homeworks/{id}", homeworkId))
                .andExpect(status().isNoContent());

        verify(homeworkService).deleteHomework(homeworkId);
    }
}