package fr.serialcoders.qawaleproject.logic.objects.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandStack {

    private final List<ICommand> undo;
    private final List<ICommand> redo;
    private List<ICommandStackListener> listeners;

    public CommandStack() {
        undo = new ArrayList<ICommand>();
        redo = new ArrayList<ICommand>();
        listeners = new ArrayList<ICommandStackListener>();
    }

    public void addCommand(ICommand command) {
        undo.add(command);
        redo.clear();
        notifyListeners();
    }

    public void undo() {
        if (!isUndoEmpty()) {
            ICommand tmp = undo.remove(undo.size() - 1);
            tmp.undo();
            redo.add(tmp);
            notifyListeners();
        }
    }

    public void redo() {
        if (!isRedoEmpty()) {
            ICommand tmp = redo.remove(redo.size() - 1);
            tmp.execute();
            undo.add(tmp);
            notifyListeners();
        }
    }

    public boolean isUndoEmpty() {
        return undo.isEmpty();
    }

    public boolean isRedoEmpty() {
        return redo.isEmpty();
    }

    private void notifyListeners() {
        for (ICommandStackListener listener : listeners)
            listener.commandStackChanged();
    }

    public void subscribe(ICommandStackListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void unsubscribe(ICommandStackListener listener) {
        listeners.remove(listener);
    }

}
