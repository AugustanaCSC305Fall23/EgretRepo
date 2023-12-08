package edu.augustana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

/**
 * Course store multiple lesson plan
 */
public class Course implements Cloneable{

    private ArrayList<LessonPlan> lessonPlans;
    private int currentEditingIndex;

    public Course(){
        this.lessonPlans = new ArrayList<>();
        this.lessonPlans.add(new LessonPlan("Untitled"));
        this.currentEditingIndex = 0;
    }
    /**
     * Adds lesson plan in the current course
     */
    public void addLessonPlan(LessonPlan currentLessonPlan){
        lessonPlans.add(currentLessonPlan);
        currentEditingIndex = this.currentEditingIndex+1;
    }

    /**
     * Removes all the lesson plan in the course and creates a new lesson plan
     */
    public void clearAllLessonPlans() {
        lessonPlans.clear();
        lessonPlans.add(new LessonPlan("Untitled"));
        currentEditingIndex =0;
    }
    /**
     * Removes selected lesson plan in the course
     */
    public void removeLessonPlan(LessonPlan lessonPlan){
        lessonPlans.remove(lessonPlan);
        currentEditingIndex = currentEditingIndex-1;
    }

    /**
     * Load from the file to the app
     */

    public static Course loadFromFile(File courseFile)throws IOException {
        FileReader reader = new FileReader(courseFile);
        Gson gson = new Gson();
        return gson.fromJson(reader,Course.class);
    }

    /**
     * Saves the lesson plan in the file
     */
    public void saveToFile(File courseFile) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedLessonPlanText = gson.toJson(this);
        PrintWriter writer = new PrintWriter(new FileWriter(courseFile));
        writer.println(serializedLessonPlanText);
        writer.close();
    }

    public ArrayList<LessonPlan> getLessonPlans() {
        return lessonPlans;
    }

    public LessonPlan getCurrentLessonPlan(){
        return lessonPlans.get(currentEditingIndex);
    }

    @Override
    public Course clone() {
        try {
            Course clone = (Course) super.clone();
            clone.lessonPlans = new ArrayList<LessonPlan>();
            clone.lessonPlans.addAll(lessonPlans);
            clone.currentEditingIndex = currentEditingIndex;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
