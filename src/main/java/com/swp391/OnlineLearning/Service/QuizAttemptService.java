package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Lesson;
import com.swp391.OnlineLearning.Model.Question;
import com.swp391.OnlineLearning.Model.QuizAttempt;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.AnsweredOption;

import java.util.List;
import java.util.Map;

public interface QuizAttemptService {
//    QuizAttempt findQuizAttemptByQuizIdAndUserId(Lesson lesson, User user);

//    List<QuizAttempt> findQuizAttemptByUserId(Long userId);
    List<QuizAttempt> findAllQuizAttempt();
    QuizAttempt startQuizAttempt(User user, Lesson lesson);
    QuizAttempt findQuizAttemptById(Long id);
    boolean existsById(Long id);
    void save(QuizAttempt quizAttempt);
    void delete(Long id);
    public QuizAttempt finishQuizAttempt(QuizAttempt quizAttempt, Map<Long, AnsweredOption> answersMap);
//    boolean isQuizAttemptExist(Lesson lesson, User user);
    List<Question> getQuestionsList(Long id);
}
