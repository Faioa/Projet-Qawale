package fr.serialcoders.qawaleproject.logic.objects.qawale;

import fr.serialcoders.qawaleproject.logic.objects.ITool;
import javafx.scene.input.MouseEvent;

public class QawaleTool implements ITool {

    private int x;
    private int y;

    @Override
    public boolean victory() {
        return false;
    }

    @Override
    public void press(MouseEvent e) {

    }

    @Override
    public void drag(MouseEvent e) {

    }

    @Override
    public void release(MouseEvent e) {

    }
}
