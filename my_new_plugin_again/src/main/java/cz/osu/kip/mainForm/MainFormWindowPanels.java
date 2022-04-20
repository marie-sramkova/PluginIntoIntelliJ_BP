package cz.osu.kip.mainForm;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
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
        scrollablePanel = makeScrollablePanel();
    }

    public JPanel getScrollablePanel() {
        return scrollablePanel;
    }

    @NotNull
    private JPanel makeScrollablePanel() {
        contentPanel = makeContentPanel();

        JBScrollPane scrollPane = new JBScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setBounds(0, 10, 800, 790);
        JPanel contentPane = new JPanel();
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
        setMainContextPanelLayout(groupLayout);
        return myPanel;
    }

    private void setMainContextPanelLayout(GroupLayout groupLayout) {
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(targetUMLDestinationPanel))
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(targetConfigDestinationPanel))
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(packagesPanel))
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(classesInterfacesPlusModifiersPanel))
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(attributesMethodsInnerClassesPanel))
        );

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

    public void setUMLTargetDestinationPanel() {
        GroupLayout groupLayout = setSubpanelsLayout(targetUMLDestinationPanel);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultUMLTargetDestination())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getOwnUMLTargetDestination()))
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetDestinationDesc())
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetFile()))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultUMLTargetDestination())
                                .addComponent(mainFormWindowItems.getOwnUMLTargetDestination()))
                        .addComponent(mainFormWindowItems.getDefaultUMLTargetDestinationDesc())
                        .addComponent(mainFormWindowItems.getDefaultUMLTargetFile()));
    }

    public void setConfigTargetDestinationPanel() {
        GroupLayout groupLayout = setSubpanelsLayout(targetConfigDestinationPanel);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultConfigTargetDestination())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getOwnConfigTargetDestination()))
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetDestinationDesc())
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetFile()))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultConfigTargetDestination())
                                .addComponent(mainFormWindowItems.getOwnConfigTargetDestination()))
                        .addComponent(mainFormWindowItems.getDefaultConfigTargetDestinationDesc())
                        .addComponent(mainFormWindowItems.getDefaultConfigTargetFile()));
    }

    public void setPackagesPanel() {
        GroupLayout groupLayout = setSubpanelsLayout(packagesPanel);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(mainFormWindowItems.getAllPackages())
                        .addGap(80)
                        .addComponent(mainFormWindowItems.getOwnPackages())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getAllPackages())
                                .addComponent(mainFormWindowItems.getOwnPackages())));
    }

    public void setClassesInterfacesPlusModifiersPanel() {
        GroupLayout groupLayout = setSubpanelsLayout(classesInterfacesPlusModifiersPanel);
        Component c = new Component() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(0,0, 770, 0);
            }
        };

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(mainFormWindowItems.getClassesCheckBox())
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getDefaultForClassCheckBox()))
                                .addComponent(mainFormWindowItems.getCheckBoxForClassAttributes())
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForPublicClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassAttributes())
                                        .addComponent(mainFormWindowItems.getCheckBoxForInternalClassAttributes()))
                                .addComponent(mainFormWindowItems.getCheckBoxForClassMethods())
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForPublicClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassMethods())
                                        .addComponent(mainFormWindowItems.getCheckBoxForInternalClassMethods()))
                                .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses())
                                //.addComponent(c)
                                .addComponent(mainFormWindowItems.getInterfacesCheckBox())
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox()))
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForInterfaceAttributes())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getCheckBoxForInterfaceMethods()))
                        ));
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(mainFormWindowItems.getClassesCheckBox())
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPublicForClassCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultForClassCheckBox()))
                        .addComponent(mainFormWindowItems.getCheckBoxForClassAttributes())
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForPublicClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForInternalClassAttributes()))
                        .addComponent(mainFormWindowItems.getCheckBoxForClassMethods())
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForPrivateClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForPublicClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForProtectedClassMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForInternalClassMethods()))
                        .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses())
                        //.addComponent(c)
                        .addComponent(mainFormWindowItems.getInterfacesCheckBox())
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPublicForInterfaceCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultForInterfaceCheckBox()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaceAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaceMethods()))
                        );}

    private GroupLayout setSubpanelsLayout(JPanel jPanel){
        Border blackline = BorderFactory.createLineBorder(Color.black);
        jPanel.setBorder(blackline);
        jPanel.setMinimumSize(new Dimension(780, 0));
        GroupLayout groupLayout = new GroupLayout(jPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        jPanel.setLayout(groupLayout);
        return groupLayout;
    }
}
