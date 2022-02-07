package cz.osu.kip.form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private JCheckBox checkBoxForClassAttributes = new JCheckBox("Atributes");
    private JCheckBox checkBoxForClassMethods = new JCheckBox("Methods");
    private JCheckBox checkBoxForInnerClasses = new JCheckBox("Inner Classes");
    private JCheckBox checkBoxForPrivateClassAttributes = new JCheckBox("Private");
    private JCheckBox checkBoxForPublicClassAttributes = new JCheckBox("Public");
    private JCheckBox checkBoxForProtectedClassAttributes = new JCheckBox("Protected");
    private JCheckBox checkBoxForInternalClassAttributes = new JCheckBox("Internal");
    private JCheckBox checkBoxForPrivateClassMethods = new JCheckBox("Private");
    private JCheckBox checkBoxForPublicClassMethods = new JCheckBox("Public");
    private JCheckBox checkBoxForProtectedClassMethods = new JCheckBox("Protected");
    private JCheckBox checkBoxForInternalClassMethods = new JCheckBox("Internal");
    private JCheckBox checkBoxForInterfaceAttributes = new JCheckBox("Atributes");
    private JCheckBox checkBoxForInterfaceMethods = new JCheckBox("Methods");
    private JButton okButton = new JButton("ok");
    private JButton cancelButton = new JButton("cancel");
    private TreeViewWindow treeViewWindow;

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
            if (ownPackages.isSelected()) {
                if (treeViewWindow == null) {
                    treeViewWindow = new TreeViewWindow();
                    treeViewWindow.show(FormWindow.getFilePath());
                } else {
                    treeViewWindow.show(FormWindow.getFilePath());
                }
            }
        }
    };

    private ActionListener classesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!classesCheckBox.isSelected()) {
                defaultForClassCheckBox.setVisible(false);
                publicForClassCheckBox.setVisible(false);
                checkBoxForClassAttributes.setVisible(false);
                checkBoxForClassMethods.setVisible(false);
                checkBoxForInnerClasses.setVisible(false);
            } else {
                defaultForClassCheckBox.setVisible(true);
                publicForClassCheckBox.setVisible(true);
                checkBoxForClassAttributes.setVisible(true);
                checkBoxForClassMethods.setVisible(true);
                checkBoxForInnerClasses.setVisible(true);
            }
        }
    };

    private ActionListener classesAttributesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkBoxForClassAttributes.isSelected()) {
                checkBoxForPrivateClassAttributes.setVisible(false);
                checkBoxForPublicClassAttributes.setVisible(false);
                checkBoxForProtectedClassAttributes.setVisible(false);
                checkBoxForInternalClassAttributes.setVisible(false);
            } else {
                checkBoxForPrivateClassAttributes.setVisible(true);
                checkBoxForPublicClassAttributes.setVisible(true);
                checkBoxForProtectedClassAttributes.setVisible(true);
                checkBoxForInternalClassAttributes.setVisible(true);
            }
        }
    };

    private ActionListener classesMethodesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkBoxForClassAttributes.isSelected()) {
                checkBoxForPrivateClassMethods.setVisible(false);
                checkBoxForPublicClassMethods.setVisible(false);
                checkBoxForProtectedClassMethods.setVisible(false);
                checkBoxForInternalClassMethods.setVisible(false);
            } else {
                checkBoxForPrivateClassMethods.setVisible(true);
                checkBoxForPublicClassMethods.setVisible(true);
                checkBoxForProtectedClassMethods.setVisible(true);
                checkBoxForInternalClassMethods.setVisible(true);
            }
        }
    };

    private ActionListener interfacesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!interfacesCheckBox.isSelected()) {
                defaultForInterfaceCheckBox.setVisible(false);
                publicForInterfaceCheckBox.setVisible(false);
                checkBoxForInterfaceAttributes.setVisible(false);
                checkBoxForInterfaceMethods.setVisible(false);
            } else {
                defaultForInterfaceCheckBox.setVisible(true);
                publicForInterfaceCheckBox.setVisible(true);
                checkBoxForInterfaceAttributes.setVisible(true);
                checkBoxForInterfaceMethods.setVisible(true);
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
        checkBoxForClassAttributes.setVisible(false);
        checkBoxForClassMethods.setVisible(false);
        checkBoxForInnerClasses.setVisible(false);
        checkBoxForClassAttributes.addActionListener(classesAttributesListener);
        checkBoxForClassMethods.addActionListener(classesMethodesListener);
        checkBoxForPrivateClassAttributes.setVisible(false);
        checkBoxForPublicClassAttributes.setVisible(false);
        checkBoxForProtectedClassAttributes.setVisible(false);
        checkBoxForInternalClassAttributes.setVisible(false);
        checkBoxForPrivateClassMethods.setVisible(false);
        checkBoxForPublicClassMethods.setVisible(false);
        checkBoxForProtectedClassMethods.setVisible(false);
        checkBoxForInternalClassMethods.setVisible(false);
        checkBoxForInterfaceAttributes.setVisible(false);
        checkBoxForInterfaceMethods.setVisible(false);
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

    public JFileChooser getDefaultUMLTargetFile() {
        return defaultUMLTargetFile;
    }

    public JFileChooser getDefaultConfigTargetFile() {
        return defaultConfigTargetFile;
    }

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

    public TreeViewWindow getTreeViewWindow() {
        return treeViewWindow;
    }

    public JCheckBox getCheckBoxForClassAttributes() {
        return checkBoxForClassAttributes;
    }

    public JCheckBox getCheckBoxForClassMethods() {
        return checkBoxForClassMethods;
    }

    public JCheckBox getCheckBoxForInnerClasses() {
        return checkBoxForInnerClasses;
    }

    public JCheckBox getCheckBoxForPrivateClassAttributes() {
        return checkBoxForPrivateClassAttributes;
    }

    public JCheckBox getCheckBoxForPublicClassAttributes() {
        return checkBoxForPublicClassAttributes;
    }

    public JCheckBox getCheckBoxForProtectedClassAttributes() {
        return checkBoxForProtectedClassAttributes;
    }

    public JCheckBox getCheckBoxForInternalClassAttributes() {
        return checkBoxForInternalClassAttributes;
    }

    public JCheckBox getCheckBoxForPrivateClassMethods() {
        return checkBoxForPrivateClassMethods;
    }

    public JCheckBox getCheckBoxForPublicClassMethods() {
        return checkBoxForPublicClassMethods;
    }

    public JCheckBox getCheckBoxForProtectedClassMethods() {
        return checkBoxForProtectedClassMethods;
    }

    public JCheckBox getCheckBoxForInternalClassMethods() {
        return checkBoxForInternalClassMethods;
    }

    public JCheckBox getCheckBoxForInterfaceAttributes() {
        return checkBoxForInterfaceAttributes;
    }

    public JCheckBox getCheckBoxForInterfaceMethods() {
        return checkBoxForInterfaceMethods;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
