package cz.osu.kip.mainForm;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFormWindowItems {

    private FormWindow formWindow;
    private JRadioButton defaultUMLTargetDestination = new JRadioButton("Default target destination for uml file");
    private JRadioButton ownUMLTargetDestination = new JRadioButton("Own target destination for uml file");
    private ButtonGroup buttonGroupUMLTargetDestination = setButtonGroup(defaultUMLTargetDestination, ownUMLTargetDestination);
    private JLabel defaultUMLTargetDestinationDesc;// = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());

    private JRadioButton defaultConfigTargetDestination = new JRadioButton("Default target destination for config file");
    private JRadioButton ownConfigTargetDestination = new JRadioButton("Own target destination for config file");
    private ButtonGroup buttonGroupConfigTargetDestination = setButtonGroup(defaultConfigTargetDestination, ownConfigTargetDestination);
    private JLabel defaultConfigTargetDestinationDesc;// = new JLabel(new File(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()).toString());

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
    private TreeViewWindow treeViewWindow;
    private String initialUrl;

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
            formWindow.refresh();
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
            formWindow.refresh();
        }
    };

    private ActionListener ownPackagesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (ownPackages.isSelected()) {
                TreeViewWindow newTreeViewWindow;
                if (treeViewWindow == null) {
                    newTreeViewWindow = new TreeViewWindow(FormWindow.getFilePath());
                } else {
                    if (initialUrl != null) {
                        newTreeViewWindow = new TreeViewWindow(treeViewWindow, initialUrl);
                    }else{
                        newTreeViewWindow = new TreeViewWindow(treeViewWindow, FormWindow.getFilePath().toString());
                    }
                }
                newTreeViewWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        if (!newTreeViewWindow.isWasCanceled()){
                            treeViewWindow = newTreeViewWindow;
                        }
                    }
                });
            }else{
                treeViewWindow = null;
            }
        }
    };

    private ActionListener classesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!classesCheckBox.isSelected()) {
                if (defaultForClassCheckBox.isSelected())
                    defaultForClassCheckBox.doClick();
                if (defaultForClassCheckBox.isSelected())
                    defaultForClassCheckBox.doClick();
                if (publicForClassCheckBox.isSelected())
                    publicForClassCheckBox.doClick();
                if (checkBoxForClassAttributes.isSelected())
                    checkBoxForClassAttributes.doClick();
                if (checkBoxForClassMethods.isSelected())
                    checkBoxForClassMethods.doClick();
                if (checkBoxForInnerClasses.isSelected())
                    checkBoxForInnerClasses.doClick();
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
            formWindow.refresh();
        }
    };

    private ActionListener classesAttributesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkBoxForClassAttributes.isSelected()) {
                if (checkBoxForPrivateClassAttributes.isSelected())
                    checkBoxForPrivateClassAttributes.doClick();
                if (checkBoxForPublicClassAttributes.isSelected())
                    checkBoxForPublicClassAttributes.doClick();
                if (checkBoxForProtectedClassAttributes.isSelected())
                    checkBoxForProtectedClassAttributes.doClick();
                if (checkBoxForInternalClassAttributes.isSelected())
                    checkBoxForInternalClassAttributes.doClick();
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
            formWindow.refresh();
        }
    };

    private ActionListener classesMethodesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkBoxForClassMethods.isSelected()) {
                if (checkBoxForPrivateClassMethods.isSelected())
                    checkBoxForPrivateClassMethods.doClick();
                if (checkBoxForPublicClassMethods.isSelected())
                    checkBoxForPublicClassMethods.doClick();
                if (checkBoxForProtectedClassMethods.isSelected())
                    checkBoxForProtectedClassMethods.doClick();
                if (checkBoxForInternalClassMethods.isSelected())
                    checkBoxForInternalClassMethods.doClick();
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
            formWindow.refresh();
        }
    };

    private ActionListener interfacesListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!interfacesCheckBox.isSelected()) {
                if (defaultForInterfaceCheckBox.isSelected())
                    defaultForInterfaceCheckBox.doClick();
                if (publicForInterfaceCheckBox.isSelected())
                    publicForInterfaceCheckBox.doClick();
                if (checkBoxForInterfaceAttributes.isSelected())
                    checkBoxForInterfaceAttributes.doClick();
                if (checkBoxForInterfaceMethods.isSelected())
                    checkBoxForInterfaceMethods.doClick();
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
            formWindow.refresh();
        }
    };

    public MainFormWindowItems(File filePath, FormWindow formWindow) {
        this.formWindow = formWindow;
        defaultUMLTargetDestination.setSelected(true);
        defaultConfigTargetDestination.setSelected(true);
        defaultUMLTargetFile.setVisible(false);
        String defaultUmlTargetDestinationText = setDefaultTargetDestinationText(new File(filePath.toPath().resolve("PlantUmlFiles").toFile().toString()));
        defaultUMLTargetDestinationDesc = new JLabel(defaultUmlTargetDestinationText);
        defaultConfigTargetFile.setVisible(false);
        String defaultConfigTargetDestinationText = setDefaultTargetDestinationText(new File(filePath.toPath().resolve("PlantUmlFiles.myuml").toFile().toString()));
        defaultConfigTargetDestinationDesc = new JLabel(defaultConfigTargetDestinationText);
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

    @NotNull
    private String setDefaultTargetDestinationText(File filePath) {
        String defaultUmlTargetDestinationText = filePath.toString();
        int rowLength = 120;
        if (defaultUmlTargetDestinationText.length() > rowLength){
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            while(defaultUmlTargetDestinationText.length() > rowLength) {
                String row = defaultUmlTargetDestinationText.substring(0,rowLength);
                int lastIndexOfSlash = row.length()-1;
                if (row.contains("/")) {
                    lastIndexOfSlash = row.lastIndexOf("/");
                }
                else if (row.contains("\\")) {
                    lastIndexOfSlash = row.lastIndexOf("\\");
                }
                sb.append(row.substring(0, lastIndexOfSlash)).append("<br>");
                defaultUmlTargetDestinationText = defaultUmlTargetDestinationText.substring(lastIndexOfSlash);
            }
            sb.append(defaultUmlTargetDestinationText);
            sb.append("</html>");
            defaultUmlTargetDestinationText = sb.toString();
        }
        return defaultUmlTargetDestinationText;
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

    public void setDefaultUMLTargetDestination() {
        this.defaultUMLTargetDestination.setSelected(true);
        defaultUMLTargetDestinationDesc.setVisible(true);
    }

    public void setOwnUMLTargetDestination(String umlTargetDestination) {
        this.ownUMLTargetDestination.setSelected(true);
        defaultUMLTargetFile.setVisible(true);
        defaultUMLTargetFile.setSelectedFile(new File(umlTargetDestination));
    }

    public void setDefaultConfigTargetDestination() {
        this.defaultConfigTargetDestination.setSelected(true);
        this.defaultConfigTargetDestinationDesc.setVisible(true);
    }

    public void setOwnConfigTargetDestination(String configTargetDestination) {
        this.ownConfigTargetDestination.setSelected(true);
        defaultConfigTargetFile.setVisible(true);
        defaultConfigTargetFile.setSelectedFile(new File(configTargetDestination));
    }

    public void setOwnPackages(TreeViewWindow treeViewWindow, String initialUrl) {
        this.ownPackages.setSelected(true);
        this.treeViewWindow = treeViewWindow;
        this.initialUrl = initialUrl;
    }

    public void setClassesCheckBox(boolean selected) {
        this.classesCheckBox.setSelected(selected);
        if (selected == true){
            this.publicForClassCheckBox.setVisible(true);
            this.defaultForClassCheckBox.setVisible(true);
            this.checkBoxForClassAttributes.setVisible(true);
            this.checkBoxForClassMethods.setVisible(true);
            this.checkBoxForInnerClasses.setVisible(true);
        }
    }

    public void setInterfacesCheckBox(boolean selected) {
        this.interfacesCheckBox.setSelected(selected);
        if (selected == true){
            this.publicForInterfaceCheckBox.setVisible(true);
            this.defaultForInterfaceCheckBox.setVisible(true);
            this.checkBoxForInterfaceAttributes.setVisible(true);
            this.checkBoxForInterfaceMethods.setVisible(true);
        }
    }

    public void setPublicForClassCheckBox(boolean selected) {
        this.publicForClassCheckBox.setSelected(selected);
    }

    public void setDefaultForClassCheckBox(boolean selected) {
        this.defaultForClassCheckBox.setSelected(selected);
    }

    public void setPublicForInterfaceCheckBox(boolean selected) {
        this.publicForInterfaceCheckBox.setSelected(selected);
    }

    public void setDefaultForInterfaceCheckBox(boolean selected) {
        this.defaultForInterfaceCheckBox.setSelected(selected);
    }

    public void setCheckBoxForClassAttributes(boolean selected) {
        this.checkBoxForClassAttributes.setSelected(selected);
        if (selected == true){
            this.checkBoxForPrivateClassAttributes.setVisible(true);
            this.checkBoxForPublicClassAttributes.setVisible(true);
            this.checkBoxForProtectedClassAttributes.setVisible(true);
            this.checkBoxForInternalClassAttributes.setVisible(true);
        }
    }

    public void setCheckBoxForClassMethods(boolean selected) {
        this.checkBoxForClassMethods.setSelected(selected);
        if (selected == true){
            this.checkBoxForPrivateClassMethods.setVisible(true);
            this.checkBoxForPublicClassMethods.setVisible(true);
            this.checkBoxForProtectedClassMethods.setVisible(true);
            this.checkBoxForInternalClassMethods.setVisible(true);
        }
    }

    public void setCheckBoxForInnerClasses(boolean selected) {
        this.checkBoxForInnerClasses.setSelected(selected);
    }

    public void setCheckBoxForPrivateClassAttributes(boolean selected) {
        this.checkBoxForPrivateClassAttributes.setSelected(selected);
    }

    public void setCheckBoxForPublicClassAttributes(boolean selected) {
        this.checkBoxForPublicClassAttributes.setSelected(selected);
    }

    public void setCheckBoxForProtectedClassAttributes(boolean selected) {
        this.checkBoxForProtectedClassAttributes.setSelected(selected);
    }

    public void setCheckBoxForInternalClassAttributes(boolean selected) {
        this.checkBoxForInternalClassAttributes.setSelected(selected);
    }

    public void setCheckBoxForPrivateClassMethods(boolean selected) {
        this.checkBoxForPrivateClassMethods.setSelected(selected);
    }

    public void setCheckBoxForPublicClassMethods(boolean selected) {
        this.checkBoxForPublicClassMethods.setSelected(selected);
    }

    public void setCheckBoxForProtectedClassMethods(boolean selected) {
        this.checkBoxForProtectedClassMethods.setSelected(selected);
    }

    public void setCheckBoxForInternalClassMethods(boolean selected) {
        this.checkBoxForInternalClassMethods.setSelected(selected);
    }

    public void setCheckBoxForInterfaceAttributes(boolean selected) {
        this.checkBoxForInterfaceAttributes.setSelected(selected);
    }

    public void setCheckBoxForInterfaceMethods(boolean selected) {
        this.checkBoxForInterfaceMethods.setSelected(selected);
    }

    public void setDefaultUMLTargetDestinationDesc(JLabel defaultUMLTargetDestinationDesc) {
        this.defaultUMLTargetDestinationDesc = defaultUMLTargetDestinationDesc;
    }

    public void setDefaultConfigTargetDestinationDesc(JLabel defaultConfigTargetDestinationDesc) {
        this.defaultConfigTargetDestinationDesc = defaultConfigTargetDestinationDesc;
    }
}
