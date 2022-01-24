package cz.osu.kip.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainFormWindowItems {

    private ActionListener defaultUMLTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            System.out.println("Selected: " + aButton.getText());
            defaultUMLTargetDestinationDesc.setVisible(true);
            defaultUMLTargetFile.setVisible(false);
        }
    };
    private ActionListener ownUMLTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            System.out.println("Selected: " + aButton.getText());
            defaultUMLTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
            defaultUMLTargetDestinationDesc.setVisible(false);
            defaultUMLTargetFile.setVisible(true);
        }
    };

    private JRadioButton defaultUMLTargetDestination = new JRadioButton("Default target destination for uml file");
    private JRadioButton ownUMLTargetDestination = new JRadioButton("Own target destination for uml file");
    private ButtonGroup buttonGroupUMLTargetDestination = setButtonGroup(defaultUMLTargetDestination, ownUMLTargetDestination);
    private JLabel defaultUMLTargetDestinationDesc = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());

    private JRadioButton defaultConfigTargetDestination = new JRadioButton(new AbstractAction("Default target destination for config file") {
        @Override
        public void actionPerformed(ActionEvent e) {
            defaultConfigTargetDestinationDesc.setVisible(true);
            defaultConfigTargetFile.setVisible(false);
        }
    });
    private JRadioButton ownConfigTargetDestination = new JRadioButton(new AbstractAction("Own target destination for config file") {
        @Override
        public void actionPerformed(ActionEvent e) {
            defaultConfigTargetDestinationDesc.setVisible(false);
            defaultConfigTargetFile.setVisible(true);
            defaultConfigTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
        }
    });
    private ButtonGroup buttonGroupConfigTargetDestination = setButtonGroup(defaultConfigTargetDestination, ownConfigTargetDestination);
    private JLabel defaultConfigTargetDestinationDesc = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());


    private JFileChooser defaultUMLTargetFile = new JFileChooser();
    private JFileChooser defaultConfigTargetFile = new JFileChooser();
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
        defaultUMLTargetDestination.setSelected(true);
        defaultConfigTargetDestination.setSelected(true);
        defaultUMLTargetFile.setVisible(false);
//        defaultUMLTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
        defaultConfigTargetFile.setVisible(false);
//        defaultConfigTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
        defaultUMLTargetDestination.addActionListener(defaultUMLTargetDestinationListener);
        ownUMLTargetDestination.addActionListener(ownUMLTargetDestinationListener);
    }

    private ButtonGroup setButtonGroup(JRadioButton... buttons) {
        ButtonGroup btnGroup = new ButtonGroup();
        for (JRadioButton button : buttons) {
            btnGroup.add(button);
        }
        return btnGroup;
    }

    public JFileChooser getDefaultUMLTargetFile() { return defaultUMLTargetFile; }

    public JFileChooser getDefaultConfigTargetFile() { return defaultConfigTargetFile; }

    public JRadioButton getDefaultUMLTargetDestination() {
        return defaultUMLTargetDestination;
    }

    public JRadioButton getOwnUMLTargetDestination() {
        return ownUMLTargetDestination;
    }

    public JLabel getDefaultUMLTargetDestinationDesc() {
        return defaultUMLTargetDestinationDesc;
    }

    public JRadioButton getDefaultConfigTargetDestination() {
        return defaultConfigTargetDestination;
    }

    public JRadioButton getOwnConfigTargetDestination() {
        return ownConfigTargetDestination;
    }

    public JLabel getDefaultConfigTargetDestinationDesc() {
        return defaultConfigTargetDestinationDesc;
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

    public ButtonGroup getButtonGroupUMLTargetDestination() {
        return buttonGroupUMLTargetDestination;
    }

    public void setButtonGroupUMLTargetDestination(ButtonGroup buttonGroupUMLTargetDestination) {
        this.buttonGroupUMLTargetDestination = buttonGroupUMLTargetDestination;
    }

    public ButtonGroup getButtonGroupConfigTargetDestination() {
        return buttonGroupConfigTargetDestination;
    }

    public void setButtonGroupConfigTargetDestination(ButtonGroup buttonGroupConfigTargetDestination) {
        this.buttonGroupConfigTargetDestination = buttonGroupConfigTargetDestination;
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
