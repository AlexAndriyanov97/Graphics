package main.java.ru.fit.andriyanov.graphics.View;

import main.java.ru.fit.andriyanov.graphics.Controller.SettingsController;
import main.java.ru.fit.andriyanov.graphics.Model.GameEvent;
import main.java.ru.fit.andriyanov.graphics.Model.Model;

import javax.swing.*;

public class SettingsView extends JFrame {
    private static final String FRAME_TITLE = "Settings Dialog";
    private JButton cancelButton;
    private JButton okButton;
    private JPanel mainPanel;
    private JSlider widthSlider;
    private JSpinner widthSpinner;
    private JSlider heightSlider;
    private JSpinner heightSpinner;
    private JSpinner birthBeginSpinner;
    private JSpinner birthEndSpinner;
    private JSpinner lifeBeginSpinner;
    private JSpinner lifeEndSpinner;
    private JSpinner firstImpactSpinner;
    private JSpinner secondImpactSpinner;
    private JSpinner radiusSpinner;
    private JSpinner thicknessSpinner;

    private SettingsController settingsController;
    private Model gameModel;

    public SettingsView(Model gameModel) {
        this.gameModel = gameModel;
        settingsController = new SettingsController(gameModel, this);

        setTitle(FRAME_TITLE);
        setContentPane(mainPanel);
        widthSlider.setValue(gameModel.getFieldWidth());
        heightSlider.setValue(gameModel.getFieldHeight());
        widthSpinner.setValue(gameModel.getFieldWidth());
        heightSpinner.setValue(gameModel.getFieldHeight());

        widthSlider.addChangeListener(settingsController::onWidthComponentChanged);
        heightSlider.addChangeListener(settingsController::onHeightComponentChanged);

        widthSpinner.addChangeListener(settingsController::onWidthComponentChanged);
        heightSpinner.addChangeListener(settingsController::onHeightComponentChanged);

        gameModel.registerObserver(this::onFieldWidthChanged, GameEvent.FIELD_WIDTH_CHANGED);
        gameModel.registerObserver(this::onFieldHeightChanged, GameEvent.FIELD_HEIGHT_CHANGED);

        okButton.addActionListener(settingsController::onOkButtonPressed);
        cancelButton.addActionListener(settingsController::onCancelButtonPressed);

        lifeBeginSpinner.addChangeListener(settingsController::onLifeBeginChanged);
        lifeEndSpinner.addChangeListener(settingsController::onLifeEndChanged);
        birthBeginSpinner.addChangeListener(settingsController::onBirthBeginChanged);
        birthEndSpinner.addChangeListener(settingsController::onBirthEndChanged);
        firstImpactSpinner.addChangeListener(settingsController::onFirstImpactChanged);
        secondImpactSpinner.addChangeListener(settingsController::onSecondImpactChanged);
        radiusSpinner.addChangeListener(settingsController::onRadiusChanged);
        thicknessSpinner.addChangeListener(settingsController::onThicknessChanged);
    }

    private void onFieldWidthChanged(Object newWidth) {
        widthSlider.setValue((int)newWidth);
        widthSpinner.setValue(newWidth);
    }

    public void onFieldHeightChanged(Object newHeight) {
        heightSlider.setValue((int)newHeight);
        heightSpinner.setValue(newHeight);
    }

    private void createUIComponents() {
        SpinnerNumberModel lifeBeginModel = new SpinnerNumberModel(gameModel.getLifeBegin(), 0, 10, 0.1);
        lifeBeginSpinner = new JSpinner(lifeBeginModel);

        SpinnerNumberModel lifeEndModel = new SpinnerNumberModel(gameModel.getLifeEnd(), 0, 10, 0.1);
        lifeEndSpinner = new JSpinner(lifeEndModel);

        SpinnerNumberModel birthBeginModel = new SpinnerNumberModel(gameModel.getBirthBegin(), 0, 10, 0.1);
        birthBeginSpinner = new JSpinner(birthBeginModel);

        SpinnerNumberModel birthEndModel = new SpinnerNumberModel(gameModel.getBirthEnd(), 0, 10, 0.1);
        birthEndSpinner = new JSpinner(birthEndModel);

        SpinnerNumberModel firstImpactModel = new SpinnerNumberModel(gameModel.getFirstImpact(), 0, 10, 0.1);
        firstImpactSpinner = new JSpinner(firstImpactModel);

        SpinnerNumberModel secondImpactModel = new SpinnerNumberModel(gameModel.getSecondImpact(), 0, 10, 0.1);
        secondImpactSpinner = new JSpinner(secondImpactModel);

        SpinnerNumberModel radiusModel = new SpinnerNumberModel(gameModel.getRadius(), 1, 50, 1);
        radiusSpinner = new JSpinner(radiusModel);

        SpinnerNumberModel thicknessModel = new SpinnerNumberModel(gameModel.getThickness(), 1, 30, 1);
        thicknessSpinner = new JSpinner(thicknessModel);
    }
}
