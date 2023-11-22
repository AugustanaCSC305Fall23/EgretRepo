package edu.augustana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class Course {

    private ArrayList<LessonPlan> lessonPlans;
    private int currentEditingIndex;

    public Course(){
        this.lessonPlans = new ArrayList<>();
        this.lessonPlans.add(new LessonPlan("Untitled 1"));
        //testing
        this.lessonPlans.add(new LessonPlan("Untitled 2"));
        this.lessonPlans.add(new LessonPlan("Untitled 3"));

        this.currentEditingIndex = 0;
    }

    public void addLessonPlan(LessonPlan lessonPlan){
        lessonPlans.add(lessonPlan);
    }

    public int getCurrentEditingIndex(){
        return currentEditingIndex;
    }

    public LessonPlan getCurrentLessonPlan(){
        return lessonPlans.get(currentEditingIndex);
    }

    public void removeLessonPlan(LessonPlan lessonPlan){
        lessonPlans.remove(lessonPlan);
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


}
