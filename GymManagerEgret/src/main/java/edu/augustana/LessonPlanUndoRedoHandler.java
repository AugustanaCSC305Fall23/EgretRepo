package edu.augustana;

import java.util.Stack;

public class LessonPlanUndoRedoHandler {

    private Stack<PlanMakerController.State> undoStack, redoStack;
    // invariant: The top state of the undoStack always is a copy of the
    // current state of the canvas.
    private PlanMakerController planMaker;


    public LessonPlanUndoRedoHandler(PlanMakerController planMaker) {
        undoStack = new Stack<PlanMakerController.State>();
        redoStack = new Stack<PlanMakerController.State>();
        this.planMaker = planMaker;
        // store the initial state of the canvas on the undo stack
        undoStack.push(planMaker.createMemento());
    }

    /**
     * saves the current state of the drawing canvas for later restoration
     */
    public void saveState() {
        PlanMakerController.State planMakerState = planMaker.createMemento();
        undoStack.push(planMakerState);
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

        PlanMakerController.State planMakerState = undoStack.pop();
        redoStack.push(planMakerState);
        planMaker.restoreState(undoStack.peek());
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

        PlanMakerController.State planMakerState = redoStack.pop();
        undoStack.push(planMakerState);
        planMaker.restoreState(planMakerState);
    }

}
