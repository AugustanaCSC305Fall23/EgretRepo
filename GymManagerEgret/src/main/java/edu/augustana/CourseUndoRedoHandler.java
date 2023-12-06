package edu.augustana;

import java.util.Stack;

public class CourseUndoRedoHandler {

    private Stack<CourseViewController.State> undoStack, redoStack;
    // invariant: The top state of the undoStack always is a copy of the
    // current state of the canvas.
    private CourseViewController courseView;


    public CourseUndoRedoHandler(CourseViewController courseView) {
        undoStack = new Stack<CourseViewController.State>();
        redoStack = new Stack<CourseViewController.State>();
        this.courseView = courseView;
        // store the initial state of the canvas on the undo stack
        undoStack.push(courseView.createMemento());
    }

    /**
     * saves the current state of the drawing canvas for later restoration
     */
    public void saveState() {
        CourseViewController.State courseViewState = courseView.createMemento();
        undoStack.push(courseViewState);
        System.out.println(undoStack);
        redoStack.clear();
    }

    /**
     * restores the state of the drawing canvas to what it was before the last
     * change. Nothing happens if there is no previous state (for example, when the
     * application first starts up or when you've already undone all actions since
     * the startup state).
     */
    public void undo() {
        if (undoStack.size() == 1) // only the current state is on the stack
            return;

        CourseViewController.State courseViewState = undoStack.pop();
        redoStack.push(courseViewState);
        courseView.restoreState(undoStack.peek());
        System.out.println(undoStack.peek().toString());
    }

    /**
     * restores the state of the drawing canvas to what it was before the last undo
     * action was performed. If some change was made to the state of the canvas
     * since the last undo, then this method does nothing.
     */
    public void redo() {
        if (redoStack.isEmpty())
            return;

        CourseViewController.State courseViewState = redoStack.pop();
        undoStack.push(courseViewState);
        courseView.restoreState(courseViewState);
    }

}
