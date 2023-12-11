package edu.augustana;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;
    void createCourse(){
        this.course = new Course();
    }

    @Test
    void addNewLessonPlanTest() {
        createCourse();
        course.addLessonPlan(new LessonPlan("test1"));
        assertTrue(course.getCurrentLessonPlan().getTitle().equals("test1"));
    }

    @Test
    void removeLessonPlanTest(){
        createCourse();
        LessonPlan lessonPlan = new LessonPlan("test2");
        course.addLessonPlan(lessonPlan);
        course.removeLessonPlan(lessonPlan);
        assertFalse(course.getLessonPlans().contains(lessonPlan));
    }
}