package com.swp391.OnlineLearning.Model.dto;

import com.swp391.OnlineLearning.Model.Question;
import com.swp391.OnlineLearning.Util.ValidMediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@ValidMediaType
public class ShortAnswerQuestionFormDTO {
    @NotBlank(message = "Vui lòng nhập nội dung câu hỏi")
    private String content;

    @NotNull(message = "Vui lòng chọn loại media kèm theo")
    private Question.MediaType mediaType;

    private MultipartFile media;

    @NotBlank(message = "Vui lòng nhập đáp án cho câu hỏi")
    private String solutionText;

    public ShortAnswerQuestionFormDTO() {
        super();
    }

    public ShortAnswerQuestionFormDTO(String content, Question.MediaType mediaType, MultipartFile media, String solutionText) {
        this.content = content;
        this.mediaType = mediaType;
        this.media = media;
        this.solutionText = solutionText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question.MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(Question.MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MultipartFile getMedia() {
        return media;
    }

    public void setMedia(MultipartFile media) {
        this.media = media;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }
}
