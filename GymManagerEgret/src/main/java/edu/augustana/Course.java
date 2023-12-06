package edu.augustana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class Course implements Cloneable{

    private ArrayList<LessonPlan> lessonPlans;
    private int currentEditingIndex;

    public Course(){
        this.lessonPlans = new ArrayList<>();
        this.lessonPlans.add(new LessonPlan("Untitled"));
        this.currentEditingIndex = 0;
    }

    public void addLessonPlan(LessonPlan currentLessonPlan){
        lessonPlans.add(currentLessonPlan);
        currentEditingIndex = this.currentEditingIndex+1;
    }


    public void clearAllLessonPlans() {
        lessonPlans.clear();
        lessonPlans.add(new LessonPlan("Untitled"));
        currentEditingIndex =0;
    }

    public LessonPlan getCurrentLessonPlan(){
        return lessonPlans.get(currentEditingIndex);
    }

    public void removeLessonPlan(LessonPlan lessonPlan){
        lessonPlans.remove(lessonPlan);
        currentEditingIndex = currentEditingIndex-1;
    }

    public ArrayList<LessonPlan> getLessonPlans() {
        return lessonPlans;
    }

    public static Course loadFromFile(File courseFile)throws IOException {
        FileReader reader = new FileReader(courseFile);
        Gson gson = new Gson();
        return gson.fromJson(reader,Course.class);
    }

    public void saveToFile(File courseFile) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedLessonPlanText = gson.toJson(this);
        PrintWriter writer = new PrintWriter(new FileWriter(courseFile));
        writer.println(serializedLessonPlanText);
        writer.close();
    }

    @Override
    public Course clone() {
        try {
            Course clone = (Course) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
