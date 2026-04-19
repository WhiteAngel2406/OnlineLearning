package com.swp391.OnlineLearning.Util;

import com.swp391.OnlineLearning.model.Question;
import com.swp391.OnlineLearning.model.dto.CourseDTO;
import com.swp391.OnlineLearning.model.dto.MultipleChoiceQuestionFormDTO;
import com.swp391.OnlineLearning.model.dto.ShortAnswerQuestionFormDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

// Logic cho annotation @ValidMediaType, Ã¡p dá»¥ng cho class DTO
public class ValidMediaTypeValidator implements ConstraintValidator<ValidMediaType, Object> { // DÃ¹ng Object Ä‘á»ƒ cÃ³ thá»ƒ tÃ¡i sá»­ dá»¥ng cho cÃ¡c DTO khÃ¡c

    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp");
    private static final Set<String> ALLOWED_VIDEO_EXTENSIONS = Set.of("mp4", "avi", "mkv", "mov", "webm");
    private static final Set<String> ALLOWED_AUDIO_EXTENSIONS = Set.of("mp3", "aac", "wav", "ogg");

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        // Láº¥y cÃ¡c giÃ¡ trá»‹ thuá»™c tÃ­nh tá»« DTO báº±ng reflection hoáº·c Ã©p kiá»ƒu
        Question.MediaType mediaType = null;
        MultipartFile mediaFile = null;

        // Giáº£ sá»­ DTO lÃ  MultipleChoiceQuestionFormDTO
        if (dto instanceof MultipleChoiceQuestionFormDTO) {
            MultipleChoiceQuestionFormDTO questionDTO = (MultipleChoiceQuestionFormDTO) dto;
            mediaType = questionDTO.getMediaType();
            mediaFile = questionDTO.getMedia();
        } else if (dto instanceof ShortAnswerQuestionFormDTO){
            ShortAnswerQuestionFormDTO questionDTO = (ShortAnswerQuestionFormDTO) dto;
            mediaType = questionDTO.getMediaType();
            mediaFile = questionDTO.getMedia();
        }else if (dto instanceof CourseDTO){
            CourseDTO courseDTO = (CourseDTO) dto;
            mediaType = Question.MediaType.IMAGE;
            mediaFile = courseDTO.getThumbnailFile();
        }

        // 1. Náº¿u ngÆ°á»i dÃ¹ng chá»n NONE, hoáº·c khÃ´ng upload file, thÃ¬ luÃ´n há»£p lá»‡
        if (mediaType == Question.MediaType.NONE) {
            return true;
        }else if (mediaFile == null || mediaFile.isEmpty()) {
            return false;
        }

        // 2. Láº¥y pháº§n má»Ÿ rá»™ng cá»§a file (extension)
        String originalFilename = mediaFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            return false; // KhÃ´ng cÃ³ tÃªn file hoáº·c khÃ´ng cÃ³ extension -> khÃ´ng há»£p lá»‡
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // 3. So sÃ¡nh extension vá»›i MediaType Ä‘Ã£ chá»n
        switch (mediaType) {
            case IMAGE:
                return ALLOWED_IMAGE_EXTENSIONS.contains(extension);
            case VIDEO:
                return ALLOWED_VIDEO_EXTENSIONS.contains(extension);
            case AUDIO:
                return ALLOWED_AUDIO_EXTENSIONS.contains(extension);
            default:
                return false; // TrÆ°á»ng há»£p khÃ´ng xÃ¡c Ä‘á»‹nh
        }
    }
}