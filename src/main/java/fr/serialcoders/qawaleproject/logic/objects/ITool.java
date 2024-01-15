package fr.serialcoders.qawaleproject.logic.objects;

import javafx.scene.input.MouseEvent;

public interface ITool {
    public boolean victory();
    public void press(MouseEvent e);
    public void drag(MouseEvent e);
    public void release(MouseEvent e);
}
