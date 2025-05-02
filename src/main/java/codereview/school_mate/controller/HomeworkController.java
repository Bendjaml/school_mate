package codereview.school_mate.controller;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.service.HomeworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/api/homeworks")
@RequiredArgsConstructor
@Tag(name = "Домашняя работа", description = "Управление домашними работами")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @PostMapping
    @Operation(summary = "Создать новой домашней работы", description = "Создает запись о новой домашней работе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Домашняя работа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<HomeworkResponseDto> createHomework(@RequestBody HomeworkRequestDto dto) {
        HomeworkResponseDto response = homeworkService.createHomework(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Получить все домашнии работы", description = "Возвращает список всех домашних работ")
    public ResponseEntity<List<HomeworkResponseDto>> getAllHomeworks() {
        List<HomeworkResponseDto> response = homeworkService.findAllHomework();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить домашнюю работу по ID", description = "Возвращает данные о домашней работе по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Домашняя работа успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Домашняя работа не найдена по индификатору")
    })
    public ResponseEntity<HomeworkResponseDto> getHomeworkById(@PathVariable Long id) {
        HomeworkResponseDto response = homeworkService.findHomeworkById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Получить домашнюю работу по ID класса", description = "Возвращает данные о домашней работе по идентификатору класса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Домашняя работа успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Домашняя работа не найдена по индификатору класса")
    })
    public ResponseEntity<List<HomeworkResponseDto>> getHomeworksByClassId(@PathVariable Long classId) {
        List<HomeworkResponseDto> response = homeworkService.findHomeworkByClassId(classId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные домашней работы", description = "Обновляет информацию в домашней работе по ее ID")
    public ResponseEntity<HomeworkResponseDto> updateHomework(
            @PathVariable Long id,
            @Valid @RequestBody HomeworkRequestDto dto) {
        HomeworkResponseDto response = homeworkService.updateHomework(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete homework")
    public ResponseEntity<Void> deleteHomework(@PathVariable Long id) {
        homeworkService.deleteHomework(id);
        return ResponseEntity.noContent().build();
    }
}