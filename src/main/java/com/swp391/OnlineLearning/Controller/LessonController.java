package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.service.*;
import com.swp391.OnlineLearning.model.*;
import com.swp391.OnlineLearning.model.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LessonController {

    private final ChapterService chapterService;
    private final UploadService uploadService;
    private final LessonService lessonService;
    private final QuestionService questionService;
    private final AnswerOptionService answerOptionService;
    private final ShortAnswerOptionService shortAnswerOptionService;

    public LessonController(ChapterService chapterService, UploadService uploadService, LessonService lessonService, QuestionService questionService, AnswerOptionService answerOptionService, ShortAnswerOptionService shortAnswerOptionService) {
        this.chapterService = chapterService;
        this.uploadService = uploadService;
        this.lessonService = lessonService;
        this.questionService = questionService;
        this.answerOptionService = answerOptionService;
        this.shortAnswerOptionService = shortAnswerOptionService;
    }
    //=========================================== Xá»­ lÃ­ Lesson ===================================================
    //--- GET LESSONS ---
    @GetMapping("/api/chapters/{chapterId}/lessons")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getLessons(@PathVariable("chapterId") Long chapterId) {
        Chapter chapter = this.chapterService.findById(chapterId).orElseThrow(() -> new IllegalArgumentException("Chapter not found with id: " + chapterId));
        List<Lesson> lessons = chapter.getLessons();

        List<LessonResponse> lessonResponses = lessons.stream().map(LessonResponse::new).toList();
        ApiResponse<List<LessonResponse>> response = new ApiResponse<>(HttpStatus.OK,
                "List of lessons",
                lessonResponses,
                null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/lessons/{lessonId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<LessonResponse>> getLesson(@PathVariable("lessonId") Long lessonId) {
        Lesson lesson = this.lessonService.findById(lessonId);
        LessonResponse lessonResponse = new LessonResponse(lesson);

        ApiResponse<LessonResponse> response = new ApiResponse<>(
                HttpStatus.OK,
                "Details of lesson",
                lessonResponse,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //--- CREATE LESSON ---
    @PostMapping("/api/chapters/{chapterId}/lectures")
    @ResponseBody
    public ResponseEntity<ApiResponse<LessonResponse>> createLecture(@PathVariable("chapterId") Long chapterId,
                                                                     @Valid @ModelAttribute CreateLectureRequest request) { // <-- DÃ¹ng LectureRequest
        Lesson newLesson = lessonService.createLecture(chapterId, request); // Gá»i service tÆ°Æ¡ng á»©ng
        LessonResponse lessonResponse = new LessonResponse(newLesson);
        ApiResponse<LessonResponse> response = new ApiResponse<>(HttpStatus.CREATED, "Táº¡o bÃ i giáº£ng thÃ nh cÃ´ng", lessonResponse, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/api/chapters/{chapterId}/quizzes")
    @ResponseBody
    public ResponseEntity<ApiResponse<LessonResponse>> createQuiz(@PathVariable("chapterId") Long chapterId,
                                                                  @Valid @ModelAttribute CreateQuizRequest request) { // <-- DÃ¹ng LectureRequest
        Lesson newLesson = lessonService.createQuiz(chapterId, request);
        LessonResponse lessonResponse = new LessonResponse(newLesson);
        ApiResponse<LessonResponse> response = new ApiResponse<>(HttpStatus.CREATED, "Táº¡o bÃ i test thÃ nh cÃ´ng", lessonResponse, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //--- UPDATE LESSON ---
    @PutMapping("/api/lectures/{lectureId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<LessonResponse>> updateLecture(@PathVariable("lectureId") Long lectureId,
                                                                     @Valid @ModelAttribute CreateLectureRequest request){
        Lesson lectureToUpdate = this.lessonService.updateLectureById(lectureId, request);

        LessonResponse lessonResponse = new LessonResponse(lectureToUpdate);
        ApiResponse<LessonResponse> response = new ApiResponse<>(HttpStatus.OK,
                "Lecture updated successfully!",
                lessonResponse,
                null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/quizzes/{quizId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<LessonResponse>> updateLecture(@PathVariable("quizId") Long quizId,
                                                                     @Valid @ModelAttribute CreateQuizRequest request){
        Lesson lectureToUpdate = this.lessonService.updateQuizById(quizId, request);

        LessonResponse lessonResponse = new LessonResponse(lectureToUpdate);
        ApiResponse<LessonResponse> response = new ApiResponse<>(HttpStatus.OK,
                "Quiz updated successfully!",
                lessonResponse,
                null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--- DELETE LESSON ---
    @DeleteMapping("/api/chapters/{chapterId}/lessons/{lessonId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteLecture(@PathVariable("chapterId") Long chapterId,
                                                                     @PathVariable("lessonId") Long lessonId){
        try{
            this.lessonService.deleteAndReorder(chapterId, lessonId);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK,
                    "Lesson deleted successfully!", null, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST,
                    "KhÃ´ng thá»ƒ xÃ³a bÃ i há»c!", null, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    //============================== Xá»¬ LÃ QUIZ ===================================
    @GetMapping("/courses/{courseId}/quizzes/{quizId}/questions")
    public String getQuizQuestionBank(@PathVariable("quizId") Long id,
                                      @PathVariable("courseId") Long courseId,
                                      @RequestParam(value = "type", required = false) String type,
                                      Model model){
        try{
            Lesson quiz = lessonService.findQuizAndQuestions(id);
            List<Question> questions = this.questionService.findByQuizIdWithSpecs(id, type);

            model.addAttribute("questions", questions);
            model.addAttribute("type", type);
            model.addAttribute("quiz", quiz);
            model.addAttribute("courseId", courseId);
            return "course/quizQuestions";
        }catch (Exception e){
            return "redirect:/courses/" + courseId;
        }
    }

    @GetMapping("/courses/{courseId}/quizzes/{quizId}/questions/multiple-choice/create")
    public String getMultipleChoiceQuestionPage(@PathVariable("courseId") Long courseId,
                                                @PathVariable("quizId") Long quizId,
                                                Model model){
        Lesson quiz = lessonService.findById(quizId);
        MultipleChoiceQuestionFormDTO multipleChoiceQuestionFormDTO = new MultipleChoiceQuestionFormDTO();

        multipleChoiceQuestionFormDTO.getAnswerOptions().add(new AnswerOption());
        multipleChoiceQuestionFormDTO.getAnswerOptions().add(new AnswerOption());

        model.addAttribute("isUpdate", false);
        model.addAttribute("mediaTypes", Question.MediaType.values());
        model.addAttribute("quiz", quiz);
        model.addAttribute("courseId", courseId);
        model.addAttribute("multipleChoiceQuestionFormDTO", multipleChoiceQuestionFormDTO);

        return "course/createMultipleChoiceQuestion";
    }

    @PostMapping("/courses/{courseId}/quizzes/{quizId}/questions/multiple-choice/create")
    public String createQuizQuestion(@PathVariable("courseId") Long courseId,
                                     @PathVariable("quizId") Long quizId,
                                     @Valid @ModelAttribute("multipleChoiceQuestionFormDTO") MultipleChoiceQuestionFormDTO multipleChoiceQuestionFormDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Model model){
        if (bindingResult.hasErrors()) {
            Lesson quiz = lessonService.findById(quizId);
            if (quiz == null) {
                throw new IllegalArgumentException("KhÃ´ng tÃ¬m tháº¥y Quiz vá»›i ID: " + quizId);
            }
            model.addAttribute("isUpdate", false);
            model.addAttribute("courseId", courseId);
            model.addAttribute("quiz", quiz); // 2. Add quiz back to the model
            model.addAttribute("mediaTypes", Question.MediaType.values());// 3. Add mediaTypes back too

            return "course/createMultipleChoiceQuestion";
        }
        try{
            //save new question
            Question newQuestion = new Question();
            newQuestion.setQuestionType(Question.QuestionType.MULTIPLE_CHOICE);
            newQuestion.setLesson(lessonService.findById(quizId));
            newQuestion.setMediaType(multipleChoiceQuestionFormDTO.getMediaType());

            newQuestion.setContent(multipleChoiceQuestionFormDTO.getContent());
            if (multipleChoiceQuestionFormDTO.getMedia() != null && !multipleChoiceQuestionFormDTO.getMedia().isEmpty()){
                String fileName = this.uploadService.uploadFile(multipleChoiceQuestionFormDTO.getMedia(), "quizzes/media", null);
                newQuestion.setMediaUrl(fileName);
            }else{
                newQuestion.setMediaType(Question.MediaType.NONE);
            }
            this.questionService.save(newQuestion);

            //save AnswerOption
            List<AnswerOption> answerOptions = multipleChoiceQuestionFormDTO.getAnswerOptions();
            answerOptions.forEach(answerOption -> {
               answerOption.setQuestion(newQuestion);
               this.answerOptionService.save(answerOption);
            });

            redirectAttributes.addFlashAttribute("message", "Question created successfully");
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }catch (Exception e){
            bindingResult.reject("global.error", e.getMessage());
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }
    }

    @GetMapping("/courses/{courseId}/quizzes/{quizId}/questions/short-answer/create")
    public String getShortAnswerQuestionPage(@PathVariable("courseId") Long courseId,
                                             @PathVariable("quizId") Long quizId,
                                             Model model){
        Lesson quiz = lessonService.findById(quizId);
        ShortAnswerQuestionFormDTO newQuestion = new ShortAnswerQuestionFormDTO();

        model.addAttribute("isUpdate", false);
        model.addAttribute("courseId", courseId);
        model.addAttribute("mediaTypes", Question.MediaType.values());
        model.addAttribute("quiz", quiz);
        model.addAttribute("shortAnswerQuestionFormDTO", newQuestion);

        return "course/createShortAnswerQuestion";
    }

    @PostMapping("/courses/{courseId}/quizzes/{quizId}/questions/short-answer/create")
    public String createQuizQuestion(@PathVariable("courseId") Long courseId,
                                     @PathVariable("quizId") Long quizId,
                                     @Valid @ModelAttribute("shortAnswerQuestionFormDTO") ShortAnswerQuestionFormDTO shortAnswerQuestionFormDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Model model){
        if (bindingResult.hasErrors()) {
            Lesson quiz = lessonService.findById(quizId);
            if (quiz == null) {
                throw new IllegalArgumentException("KhÃ´ng tÃ¬m tháº¥y Quiz vá»›i ID: " + quizId);
            }
            model.addAttribute("isUpdate", false);
            model.addAttribute("courseId", courseId);
            model.addAttribute("quiz", quiz); // 2. Add quiz back to the model
            model.addAttribute("mediaTypes", Question.MediaType.values());// 3. Add mediaTypes back too

            return "course/createShortAnswerQuestion";
        }
        try{
            //save new question
            Question newQuestion = new Question();
            newQuestion.setQuestionType(Question.QuestionType.SHORT_ANSWER);
            newQuestion.setLesson(lessonService.findById(quizId));
            newQuestion.setMediaType(shortAnswerQuestionFormDTO.getMediaType());
            newQuestion.setContent(shortAnswerQuestionFormDTO.getContent());

            if (shortAnswerQuestionFormDTO.getMedia() != null && !shortAnswerQuestionFormDTO.getMedia().isEmpty()){
                String fileName = this.uploadService.uploadFile(shortAnswerQuestionFormDTO.getMedia(), "quizzes/media", null);
                newQuestion.setMediaUrl(fileName);
            }else{
                newQuestion.setMediaType(Question.MediaType.NONE);
            }
            this.questionService.save(newQuestion);

            //save short answer option
            ShortAnswerOption answerOption = new ShortAnswerOption();
            answerOption.setQuestion(newQuestion);
            answerOption.setSolutionText(shortAnswerQuestionFormDTO.getSolutionText());
            this.shortAnswerOptionService.save(answerOption);

            redirectAttributes.addFlashAttribute("message", "Question created successfully");
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }catch (Exception e){
            bindingResult.reject("global.error", e.getMessage());
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }
    }

    //===== Handle delete question =====
    @PostMapping("/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/delete")
    public String deleteMultipleChoiceQuestion(@PathVariable("questionId") Long questionId,
                                               @PathVariable("courseId") Long courseId,
                                               @PathVariable("quizId") Long quizId,
                                               RedirectAttributes redirectAttributes,
                                               Model model){
        try{
            Question question = questionService.findById(questionId);
            questionService.delete(question);
            redirectAttributes.addFlashAttribute("message", "Question deleted successfully");
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
    }


    //==================== UPDATE MULTIPLE CHOICE QUESTION ====================

    @GetMapping("/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/multiple-choice/update")
    public String getUpdateMultipleChoiceQuestionPage(@PathVariable("questionId") Long questionId,
                                                      @PathVariable("courseId") Long courseId,
                                                      @PathVariable("quizId") Long quizId,
                                                      Model model) {
        try {
            Question question = questionService.findByIdWithAnswerOptions(questionId);
            Lesson quiz = lessonService.findById(quizId);

            // Map tá»« Entity sang DTO Ä‘á»ƒ Ä‘á»• dá»¯ liá»‡u vÃ o form
            MultipleChoiceQuestionFormDTO dto = new MultipleChoiceQuestionFormDTO();
            dto.setContent(question.getContent());
            dto.setMediaType(question.getMediaType());
            // Láº¥y danh sÃ¡ch cÃ¢u tráº£ lá»i Ä‘Ã£ cÃ³
            List<AnswerOption> existingOptions = question.getAnswerOptions();
            dto.setAnswerOptions(existingOptions);

            model.addAttribute("isUpdate", true); // ÄÃ¡nh dáº¥u Ä‘Ã¢y lÃ  form update
            model.addAttribute("pageTitle", "Cáº­p nháº­t cÃ¢u há»i tráº¯c nghiá»‡m");
            model.addAttribute("quiz", quiz);
            model.addAttribute("courseId", courseId);
            model.addAttribute("questionId", questionId); // Truyá»n questionId Ä‘á»ƒ form action biáº¿t
            model.addAttribute("multipleChoiceQuestionFormDTO", dto);
            model.addAttribute("mediaTypes", Question.MediaType.values());

            return "course/createMultipleChoiceQuestion"; // TÃ¡i sá»­ dá»¥ng view create
        } catch (Exception e) {
            // Handle error, maybe redirect with an error message
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }
    }

    @PostMapping("/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/multiple-choice/update")
    public String updateMultipleChoiceQuestion(@PathVariable("questionId") Long questionId,
                                               @PathVariable("courseId") Long courseId,
                                               @PathVariable("quizId") Long quizId,
                                               @Valid @ModelAttribute("multipleChoiceQuestionFormDTO") MultipleChoiceQuestionFormDTO dto,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes,
                                               Model model) {
        if (bindingResult.hasErrors()) {
            Lesson quiz = lessonService.findById(quizId);
            model.addAttribute("isUpdate", true);
            model.addAttribute("pageTitle", "Cáº­p nháº­t cÃ¢u há»i tráº¯c nghiá»‡m");
            model.addAttribute("quiz", quiz);
            model.addAttribute("courseId", courseId);
            model.addAttribute("questionId", questionId);
            model.addAttribute("mediaTypes", Question.MediaType.values());
            return "course/createMultipleChoiceQuestion";
        }

        try {
            Question questionToUpdate = questionService.findById(questionId);
            questionToUpdate.setContent(dto.getContent());
            questionToUpdate.setMediaType(dto.getMediaType());

            // Xá»­ lÃ½ upload file má»›i (náº¿u cÃ³)
            if (dto.getMedia() != null && !dto.getMedia().isEmpty()) {
                String fileName = this.uploadService.uploadFile(dto.getMedia(), "quizzes/media", null);
                questionToUpdate.setMediaUrl(fileName);
            } else if (dto.getMediaType() == Question.MediaType.NONE) {
                questionToUpdate.setMediaUrl(null); // XÃ³a media náº¿u ngÆ°á»i dÃ¹ng chá»n NONE
            }

            questionService.save(questionToUpdate);

            //xÃ³a vÃ  thÃªm má»›i cÃ¡c lá»±a chá»n
            //khi xÃ³a á»Ÿ question, cÃ¡c answerOption Ä‘Ã³ sáº½ bá»‹ má»“ cÃ´i vÃ  cÅ©ng bá»‹ xÃ³a (do orphanRemoval = true)
            questionToUpdate.getAnswerOptions().clear();

            dto.getAnswerOptions().forEach(option -> {
                option.setQuestion(questionToUpdate);
                answerOptionService.save(option);
            });

            redirectAttributes.addFlashAttribute("message", "Cáº­p nháº­t cÃ¢u há»i thÃ nh cÃ´ng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lá»—i khi cáº­p nháº­t cÃ¢u há»i: " + e.getMessage());
        }

        return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
    }

    //==================== UPDATE SHORT ANSWER QUESTION ====================

    @GetMapping("/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/short-answer/update")
    public String getUpdateShortAnswerQuestionPage(@PathVariable("questionId") Long questionId,
                                                   @PathVariable("courseId") Long courseId,
                                                   @PathVariable("quizId") Long quizId,
                                                   Model model) {
        try {
            Question question = questionService.findById(questionId);
            Lesson quiz = lessonService.findById(quizId);
            ShortAnswerOption solution = question.getShortAnswerOption();

            ShortAnswerQuestionFormDTO dto = new ShortAnswerQuestionFormDTO();
            dto.setContent(question.getContent());
            dto.setMediaType(question.getMediaType());
            dto.setSolutionText(solution.getSolutionText());

            model.addAttribute("isUpdate", true);
            model.addAttribute("pageTitle", "Cáº­p nháº­t cÃ¢u há»i tráº£ lá»i ngáº¯n");
            model.addAttribute("quiz", quiz);
            model.addAttribute("courseId", courseId);
            model.addAttribute("questionId", questionId);
            model.addAttribute("shortAnswerQuestionFormDTO", dto);
            model.addAttribute("mediaTypes", Question.MediaType.values());

            return "course/createShortAnswerQuestion"; // TÃ¡i sá»­ dá»¥ng view create
        } catch (Exception e) {
            return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
        }
    }

    @PostMapping("/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/short-answer/update")
    public String updateShortAnswerQuestion(@PathVariable("questionId") Long questionId,
                                            @PathVariable("courseId") Long courseId,
                                            @PathVariable("quizId") Long quizId,
                                            @Valid @ModelAttribute("shortAnswerQuestionFormDTO") ShortAnswerQuestionFormDTO dto,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {
        if (bindingResult.hasErrors()) {
            Lesson quiz = lessonService.findById(quizId);
            model.addAttribute("isUpdate", true);
            model.addAttribute("pageTitle", "Cáº­p nháº­t cÃ¢u há»i tráº£ lá»i ngáº¯n");
            model.addAttribute("quiz", quiz);
            model.addAttribute("courseId", courseId);
            model.addAttribute("questionId", questionId);
            model.addAttribute("mediaTypes", Question.MediaType.values());
            return "course/createShortAnswerQuestion";
        }

        try {
            Question questionToUpdate = questionService.findById(questionId);
            questionToUpdate.setContent(dto.getContent());
            questionToUpdate.setMediaType(dto.getMediaType());

            if (dto.getMedia() != null && !dto.getMedia().isEmpty()) {
                String fileName = this.uploadService.uploadFile(dto.getMedia(), "quizzes/media", null);
                questionToUpdate.setMediaUrl(fileName);
            } else if (dto.getMediaType() == Question.MediaType.NONE) {
                questionToUpdate.setMediaUrl(null);
            }

            questionService.save(questionToUpdate);

            ShortAnswerOption solution = questionToUpdate.getShortAnswerOption();
            solution.setSolutionText(dto.getSolutionText());
            shortAnswerOptionService.save(solution);

            redirectAttributes.addFlashAttribute("message", "Cáº­p nháº­t cÃ¢u há»i thÃ nh cÃ´ng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lá»—i khi cáº­p nháº­t cÃ¢u há»i: " + e.getMessage());
        }

        return "redirect:/courses/" + courseId + "/quizzes/" + quizId + "/questions";
    }
}
