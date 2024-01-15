package fr.serialcoders.qawaleproject.logic.objects.commands;

public interface ICommand {
    public void execute();
    public void undo();
}
