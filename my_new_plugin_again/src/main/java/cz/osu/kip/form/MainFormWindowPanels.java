package cz.osu.kip.form;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class MainFormWindowPanels {

    private Project currentProject;
    private File filePath;
    private MainFormWindowItems mainFormWindowItems;
    private JPanel scrollablePanel;
    private JPanel contentPanel;
    private JPanel targetUMLDestinationPanel = new JPanel();
    private JPanel targetConfigDestinationPanel = new JPanel();
    private JPanel packagesPanel = new JPanel();
    private JPanel classesInterfacesPlusModifiersPanel = new JPanel();
    private JPanel attributesMethodsInnerClassesPanel = new JPanel();

    public MainFormWindowPanels(Project currentProject, File filePath, MainFormWindowItems mainFormWindowItems) {
        this.currentProject = currentProject;
        this.filePath = filePath;
        this.mainFormWindowItems = mainFormWindowItems;
        setUMLTargetDestinationPanel();
        setConfigTargetDestinationPanel();
        setPackagesPanel();
        setClassesInterfacesPlusModifiersPanel();
        //setAttributesMethodsInnerClassesPanel();
        scrollablePanel = makeScrollablePanel();
    }

    public JPanel getScrollablePanel() {
        return scrollablePanel;
    }

    @NotNull
    private JPanel makeScrollablePanel() {
        contentPanel = makeContentPanel();
        JBScrollPane scrollPane = new JBScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 10, 800, 790);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800, 800));
        contentPane.add(scrollPane);
        return contentPane;
    }

    @NotNull
    private JPanel makeContentPanel() {
        JPanel myPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(myPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        myPanel.setLayout(groupLayout);

//        JCheckBoxMenuItem publicCheckBoxMenuItem = new JCheckBoxMenuItem("ahoj");
//        JColorChooser colorChooser = new JColorChooser();
//        List<String> text = new ArrayList<>(Arrays.asList("private", "public", "protected"));
//        JComboBox<String> comboBox = new ComboBox(text.toArray());

//        JPanel targetUMLDestinationPanel = getUMLTargetDestinationPanel();
//        JPanel targetConfigDestinationPanel = getConfigTargetDestinationPanel();
        setLayout(groupLayout);
        return myPanel;
    }

    private void setLayout(GroupLayout groupLayout) {
        setVerticalGroupLayout(groupLayout);
        setHorizontalGroupLayout(groupLayout);
    }

    private void setHorizontalGroupLayout(GroupLayout groupLayout) {
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(LEADING)
                                .addComponent(targetUMLDestinationPanel)
                                .addComponent(targetConfigDestinationPanel)
                                .addComponent(packagesPanel)
                                .addComponent(classesInterfacesPlusModifiersPanel)
                                .addComponent(attributesMethodsInnerClassesPanel))
        );
    }

    private void setVerticalGroupLayout(GroupLayout groupLayout) {
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(targetUMLDestinationPanel))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(targetConfigDestinationPanel))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(packagesPanel))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(classesInterfacesPlusModifiersPanel))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(attributesMethodsInnerClassesPanel))
        );
    }

    public void setUMLTargetDestinationPanel() {
        GroupLayout groupLayoutFirstPanel = new GroupLayout(targetUMLDestinationPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        targetUMLDestinationPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup()
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultUMLTargetDestination())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getOwnUMLTargetDestination()))
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetDestinationDesc())
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetFile()))
        );
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetDestination())
                                .addComponent(mainFormWindowItems.getOwnUMLTargetDestination()))
                        .addComponent(mainFormWindowItems.getDefaultUMLTargetDestinationDesc())
                        .addComponent(mainFormWindowItems.getDefaultUMLTargetFile()));
    }

    public void setConfigTargetDestinationPanel() {
        GroupLayout groupLayoutFirstPanel = new GroupLayout(targetConfigDestinationPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        targetConfigDestinationPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup()
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultConfigTargetDestination())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getOwnConfigTargetDestination()))
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetDestinationDesc())
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetFile()))
        );
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetDestination())
                                .addComponent(mainFormWindowItems.getOwnConfigTargetDestination()))
                        .addComponent(mainFormWindowItems.getDefaultConfigTargetDestinationDesc())
                        .addComponent(mainFormWindowItems.getDefaultConfigTargetFile()));
    }

    public void setPackagesPanel() {
        GroupLayout groupLayoutFirstPanel = new GroupLayout(packagesPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        packagesPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addComponent(mainFormWindowItems.getAllPackages())
                        .addGap(80)
                        .addComponent(mainFormWindowItems.getOwnPackages())
        );
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getAllPackages())
                                .addComponent(mainFormWindowItems.getOwnPackages())));
    }

    public void setClassesInterfacesPlusModifiersPanel() {
        GroupLayout groupLayoutFirstPanel = new GroupLayout(classesInterfacesPlusModifiersPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        classesInterfacesPlusModifiersPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup()
                                .addComponent(mainFormWindowItems.getClassesCheckBox())
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getDefaultForClassCheckBox()))
                                .addComponent(mainFormWindowItems.getCheckBoxForClassAttributes())
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForPublicClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForInternalClassAttributes()))
                                .addComponent(mainFormWindowItems.getCheckBoxForClassMethods())
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForPublicClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForInternalClassMethods()))
                                .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses())
                                .addGap(80)
                                .addComponent(mainFormWindowItems.getInterfacesCheckBox())
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox()))
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForInterfaceAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForInterfaceMethods()))
                        ));
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addComponent(mainFormWindowItems.getClassesCheckBox())
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultForClassCheckBox()))
                        .addComponent(mainFormWindowItems.getCheckBoxForClassAttributes())
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForPublicClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForInternalClassAttributes()))
                        .addComponent(mainFormWindowItems.getCheckBoxForClassMethods())
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForPublicClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForInternalClassMethods()))
                        .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses())
                        .addGap(80)
                        .addComponent(mainFormWindowItems.getInterfacesCheckBox())
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox()))
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaceAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaceMethods()))
                        );}

    public void setAttributesMethodsInnerClassesPanel() {
        GroupLayout groupLayoutFirstPanel = new GroupLayout(attributesMethodsInnerClassesPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        attributesMethodsInnerClassesPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup()
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getClassesCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getInterfacesCheckBox()))
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox()))
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultForClassCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox())))
        );
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getClassesCheckBox())
                                .addComponent(mainFormWindowItems.getInterfacesCheckBox()))
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox()))
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultForClassCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox())));
    }
}
