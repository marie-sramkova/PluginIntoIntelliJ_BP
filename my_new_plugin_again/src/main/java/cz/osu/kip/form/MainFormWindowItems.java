package cz.osu.kip.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainFormWindowItems {
    private JRadioButton defaultTargetDestination = new JRadioButton(new AbstractAction("Default target destination") {
        @Override
        public void actionPerformed(ActionEvent e) {
            defaultTargetDestinationDesc.setVisible(true);
        }
    });
    private JRadioButton ownTargetDestination = new JRadioButton(new AbstractAction("Own target destination") {
        @Override
        public void actionPerformed(ActionEvent e) {
            defaultTargetDestinationDesc.setVisible(false);
        }
    });
    private ButtonGroup buttonGroup = setButtonGroup();

    private JLabel defaultTargetDestinationDesc = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());


    private JLabel sourceLabel = new JLabel("Source location: ");
    private JLabel targetLabel = new JLabel("Target location: ");
    private JTextField source = new JTextField(20);
    private JTextField target = new JTextField(20);
    private JCheckBox privateCheckBox = new JCheckBox("Private");
    private JCheckBox publicCheckBox = new JCheckBox("Public");
    private JCheckBox protectedCheckBox = new JCheckBox("Protected");
    private JCheckBox defaultCheckBox = new JCheckBox("Default");
    private JCheckBox checkBoxForAllInDir = new JCheckBox("Generate for all directories");
    private JCheckBox checkBoxForChooseDirs = new JCheckBox("Choose directories");
    private JCheckBox checkBoxForClasses = new JCheckBox("Classes");
    private JCheckBox checkBoxForInterfaces = new JCheckBox("Interfaces");
    private JCheckBox checkBoxForAttributes = new JCheckBox("Atributes");
    private JCheckBox checkBoxForMethods = new JCheckBox("Methods");
    private JCheckBox checkBoxForInnerClasses = new JCheckBox("Inner Classes");
    private JCheckBox defaultTargetLocation = new JCheckBox("Default target location");
    private JButton okButton = new JButton("ok");
    private JButton cancelButton = new JButton("cancel");
    private JButton chooseDifferentTargetLocation = new JButton(new AbstractAction("Choose target location") {
        @Override
        public void actionPerformed(ActionEvent e) {
            makeFormForNewTargetLocationChoice();
        }
    });

    public MainFormWindowItems() {
        defaultTargetDestinationDesc.setVisible(true);
        defaultTargetDestination.setSelected(true);
    }

    private ButtonGroup setButtonGroup() {
        buttonGroup = new ButtonGroup();
        buttonGroup.add(defaultTargetDestination);
        buttonGroup.add(ownTargetDestination);
        return buttonGroup;
    }

    public JRadioButton getDefaultTargetDestination() {
        return defaultTargetDestination;
    }

    public JRadioButton getOwnTargetDestination() {
        return ownTargetDestination;
    }

    public JLabel getDefaultTargetDestinationDesc() {
        return defaultTargetDestinationDesc;
    }

    private void makeFormForNewTargetLocationChoice() {
        JFrame locationChoiceFrame = new JFrame();
        JPanel contentForLocationChoice = new JPanel();
        GroupLayout groupLayout = new GroupLayout(contentForLocationChoice);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        contentForLocationChoice.setLayout(groupLayout);
        contentForLocationChoice.setPreferredSize(new Dimension(500, 500));
        JButton okButton = new JButton("ok");
        JButton cancelButton = new JButton("cancel");
        JFileChooser defaultTargetFile = new JFileChooser();
        contentForLocationChoice.add(defaultTargetFile);
        locationChoiceFrame.setContentPane(contentForLocationChoice);
        locationChoiceFrame.pack();
        locationChoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        locationChoiceFrame.setVisible(true);

        int result = defaultTargetFile.showSaveDialog(locationChoiceFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            System.out.println(defaultTargetFile.getSelectedFile().toString());
            locationChoiceFrame.dispatchEvent(new WindowEvent(locationChoiceFrame, WindowEvent.WINDOW_CLOSING));
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Cancel was selected");
            locationChoiceFrame.dispatchEvent(new WindowEvent(locationChoiceFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    public JLabel getSourceLabel() {
        return sourceLabel;
    }

    public void setSourceLabel(JLabel sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    public JLabel getTargetLabel() {
        return targetLabel;
    }

    public void setTargetLabel(JLabel targetLabel) {
        this.targetLabel = targetLabel;
    }

    public JTextField getSource() {
        return source;
    }

    public void setSource(JTextField source) {
        this.source = source;
    }

    public JTextField getTarget() {
        return target;
    }

    public void setTarget(JTextField target) {
        this.target = target;
    }

    public JCheckBox getPrivateCheckBox() {
        return privateCheckBox;
    }

    public void setPrivateCheckBox(JCheckBox privateCheckBox) {
        this.privateCheckBox = privateCheckBox;
    }

    public JCheckBox getPublicCheckBox() {
        return publicCheckBox;
    }

    public void setPublicCheckBox(JCheckBox publicCheckBox) {
        this.publicCheckBox = publicCheckBox;
    }

    public JCheckBox getProtectedCheckBox() {
        return protectedCheckBox;
    }

    public void setProtectedCheckBox(JCheckBox protectedCheckBox) {
        this.protectedCheckBox = protectedCheckBox;
    }

    public JCheckBox getDefaultCheckBox() {
        return defaultCheckBox;
    }

    public void setDefaultCheckBox(JCheckBox defaultCheckBox) {
        this.defaultCheckBox = defaultCheckBox;
    }

    public JCheckBox getCheckBoxForAllInDir() {
        return checkBoxForAllInDir;
    }

    public void setCheckBoxForAllInDir(JCheckBox checkBoxForAllInDir) {
        this.checkBoxForAllInDir = checkBoxForAllInDir;
    }

    public JCheckBox getCheckBoxForChooseDirs() {
        return checkBoxForChooseDirs;
    }

    public void setCheckBoxForChooseDirs(JCheckBox checkBoxForChooseDirs) {
        this.checkBoxForChooseDirs = checkBoxForChooseDirs;
    }

    public JCheckBox getCheckBoxForClasses() {
        return checkBoxForClasses;
    }

    public void setCheckBoxForClasses(JCheckBox checkBoxForClasses) {
        this.checkBoxForClasses = checkBoxForClasses;
    }

    public JCheckBox getCheckBoxForInterfaces() {
        return checkBoxForInterfaces;
    }

    public void setCheckBoxForInterfaces(JCheckBox checkBoxForInterfaces) {
        this.checkBoxForInterfaces = checkBoxForInterfaces;
    }

    public JCheckBox getCheckBoxForAttributes() {
        return checkBoxForAttributes;
    }

    public void setCheckBoxForAttributes(JCheckBox checkBoxForAttributes) {
        this.checkBoxForAttributes = checkBoxForAttributes;
    }

    public JCheckBox getCheckBoxForMethods() {
        return checkBoxForMethods;
    }

    public void setCheckBoxForMethods(JCheckBox checkBoxForMethods) {
        this.checkBoxForMethods = checkBoxForMethods;
    }

    public JCheckBox getCheckBoxForInnerClasses() {
        return checkBoxForInnerClasses;
    }

    public void setCheckBoxForInnerClasses(JCheckBox checkBoxForInnerClasses) {
        this.checkBoxForInnerClasses = checkBoxForInnerClasses;
    }

    public JCheckBox getDefaultTargetLocation() {
        return defaultTargetLocation;
    }

    public void setDefaultTargetLocation(JCheckBox defaultTargetLocation) {
        this.defaultTargetLocation = defaultTargetLocation;
    }

    public JButton getChooseDifferentTargetLocation() {
        return chooseDifferentTargetLocation;
    }

    public void setChooseDifferentTargetLocation(JButton chooseDifferentTargetLocation) {
        this.chooseDifferentTargetLocation = chooseDifferentTargetLocation;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
