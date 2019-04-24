package main.java.ru.nsu.fit.andriyanov.graphics.view;

import javax.swing.*;
import java.awt.*;

public class ValueBar extends JPanel {
    private JLabel text;

    public ValueBar() {
        super();
        text = new JLabel();
        Dimension dimension = new Dimension(getWidth(),12);
        setPreferredSize(dimension);
        BoxLayout boxLayout = new BoxLayout(this,BoxLayout.X_AXIS);
        setLayout(boxLayout);

        text.setHorizontalAlignment(SwingConstants.LEFT);
        add(text);
    }

    public void SetText(String value){
        text.setText(value);
    }
}
