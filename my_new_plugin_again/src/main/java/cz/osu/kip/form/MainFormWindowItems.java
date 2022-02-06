package cz.osu.kip.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;

public class MainFormWindowItems {

    private JRadioButton defaultUMLTargetDestination = new JRadioButton("Default target destination for uml file");
    private JRadioButton ownUMLTargetDestination = new JRadioButton("Own target destination for uml file");
    private ButtonGroup buttonGroupUMLTargetDestination = setButtonGroup(defaultUMLTargetDestination, ownUMLTargetDestination);
    private JLabel defaultUMLTargetDestinationDesc = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());

    private JRadioButton defaultConfigTargetDestination = new JRadioButton("Default target destination for config file");
    private JRadioButton ownConfigTargetDestination = new JRadioButton("Own target destination for config file");
    private ButtonGroup buttonGroupConfigTargetDestination = setButtonGroup(defaultConfigTargetDestination, ownConfigTargetDestination);
    private JLabel defaultConfigTargetDestinationDesc = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());

    private JRadioButton allPackages = new JRadioButton("All packages");
    private JRadioButton ownPackages = new JRadioButton("Choose packages");
    private ButtonGroup buttonGroupPackages = setButtonGroup(allPackages, ownPackages);

    private JFileChooser defaultUMLTargetFile = new JFileChooser();
    private JFileChooser defaultConfigTargetFile = new JFileChooser();
    private JCheckBox classesCheckBox = new JCheckBox("Classes");
    private JCheckBox interfacesCheckBox = new JCheckBox("Interfaces");
    private JCheckBox publicForClassCheckBox = new JCheckBox("Public");
    private JCheckBox defaultForClassCheckBox = new JCheckBox("Default");
    private JCheckBox publicForInterfaceCheckBox = new JCheckBox("Public");
    private JCheckBox defaultForInterfaceCheckBox = new JCheckBox("Default");
    private JCheckBox checkBoxForAttributes = new JCheckBox("Atributes");
    private JCheckBox checkBoxForMethods = new JCheckBox("Methods");
    private JCheckBox checkBoxForInnerClasses = new JCheckBox("Inner Classes");
    private JButton okButton = new JButton("ok");
    private JButton cancelButton = new JButton("cancel");

    private ActionListener defaultUMLTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
//            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            defaultUMLTargetDestinationDesc.setVisible(true);
            defaultUMLTargetFile.setVisible(false);
        }
    };
    private ActionListener ownUMLTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            defaultUMLTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
            defaultUMLTargetDestinationDesc.setVisible(false);
            defaultUMLTargetFile.setVisible(true);
        }
    };

    private ActionListener defaultConfigTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            defaultConfigTargetDestinationDesc.setVisible(true);
            defaultConfigTargetFile.setVisible(false);
        }
    };
    private ActionListener ownConfigTargetDestinationListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            defaultConfigTargetFile.setCurrentDirectory(new File(FormWindow.getFilePath().toPath().toFile().toString()));
            defaultConfigTargetDestinationDesc.setVisible(false);
            defaultConfigTargetFile.setVisible(true);
        }
    };

    private ActionListener ownPackagesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            //TODO: Find all subpackages in the chosen package and show them as checkboxes.

            TreeViewWindow treeViewWindow = new TreeViewWindow(FormWindow.getFilePath());
        }
    };

    private ActionListener classesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!classesCheckBox.isSelected())
            {
                defaultForClassCheckBox.setVisible(false);
                publicForClassCheckBox.setVisible(false);
            } else{
                defaultForClassCheckBox.setVisible(true);
                publicForClassCheckBox.setVisible(true);
            }
        }
    };


    private ActionListener interfacesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!interfacesCheckBox.isSelected())
            {
                defaultForInterfaceCheckBox.setVisible(false);
                publicForInterfaceCheckBox.setVisible(false);
            }else{
                defaultForInterfaceCheckBox.setVisible(true);
                publicForInterfaceCheckBox.setVisible(true);
            }
        }
    };

    public MainFormWindowItems() {
        defaultUMLTargetDestination.setSelected(true);
        defaultConfigTargetDestination.setSelected(true);
        defaultUMLTargetFile.setVisible(false);
        defaultConfigTargetFile.setVisible(false);
        defaultUMLTargetDestination.addActionListener(defaultUMLTargetDestinationListener);
        ownUMLTargetDestination.addActionListener(ownUMLTargetDestinationListener);
        defaultConfigTargetDestination.addActionListener(defaultConfigTargetDestinationListener);
        ownConfigTargetDestination.addActionListener(ownConfigTargetDestinationListener);
        allPackages.setSelected(true);
        allPackages.addActionListener(ownPackagesListener);
        ownPackages.addActionListener(ownPackagesListener);
        classesCheckBox.addActionListener(classesListener);
        interfacesCheckBox.addActionListener(interfacesListener);
        defaultForClassCheckBox.setVisible(false);
        defaultForInterfaceCheckBox.setVisible(false);
        publicForClassCheckBox.setVisible(false);
        publicForInterfaceCheckBox.setVisible(false);
    }

    private ButtonGroup setButtonGroup(JRadioButton... buttons) {
        ButtonGroup btnGroup = new ButtonGroup();
        for (JRadioButton button : buttons) {
            btnGroup.add(button);
        }
        return btnGroup;
    }

    public JRadioButton getAllPackages() {
        return allPackages;
    }

    public JRadioButton getOwnPackages() {
        return ownPackages;
    }

    public JCheckBox getClassesCheckBox() {
        return classesCheckBox;
    }

    public JCheckBox getInterfacesCheckBox() {
        return interfacesCheckBox;
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

    public JCheckBox getPublicForClassCheckBox() {
        return publicForClassCheckBox;
    }

    public JCheckBox getDefaultForClassCheckBox() {
        return defaultForClassCheckBox;
    }

    public JCheckBox getPublicForInterfaceCheckBox() {
        return publicForInterfaceCheckBox;
    }

    public JCheckBox getDefaultForInterfaceCheckBox() {
        return defaultForInterfaceCheckBox;
    }

    public JCheckBox getCheckBoxForAttributes() {
        return checkBoxForAttributes;
    }

    public JCheckBox getCheckBoxForMethods() {
        return checkBoxForMethods;
    }

    public JCheckBox getCheckBoxForInnerClasses() {
        return checkBoxForInnerClasses;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
