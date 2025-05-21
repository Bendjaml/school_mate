package codereview.school_mate.service;

import codereview.school_mate.dto.request.registration.ParentRegistrationRequestDto;
import codereview.school_mate.dto.request.ParentRequestDto;
import codereview.school_mate.dto.responce.ParentResponseDto;
import codereview.school_mate.model.User;

import java.util.List;

public interface ParentService {
        ParentResponseDto createParent(ParentRegistrationRequestDto dto, User user);
        ParentResponseDto findByIdParent(Long id);
        List<ParentResponseDto> findAllParent();
        ParentResponseDto updateParent(Long id, ParentRequestDto dto);
        void deleteParent(Long id);
    }
