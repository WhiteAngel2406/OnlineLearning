package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.model.ApiResponse;
import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.model.CourseCategory;
import com.swp391.OnlineLearning.model.Enrollment;
import com.swp391.OnlineLearning.model.dto.CourseDTO;
import com.swp391.OnlineLearning.model.dto.CourseFeedbackStats;
import com.swp391.OnlineLearning.model.dto.FeedbackDTO;
import com.swp391.OnlineLearning.model.dto.UpdateCourseDTO;
import com.swp391.OnlineLearning.model.dto.*;
import com.swp391.OnlineLearning.service.*;
import com.swp391.OnlineLearning.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public class CourseController {
}
