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
    private static MainFormWindowItems mainFormWindowItems = new MainFormWindowItems();
    private static JPanel targetDestinationPanel = new JPanel();
    private JPanel scrollablePanel;
    private JPanel contentPanel;

    public MainFormWindowPanels(Project currentProject, File filePath, MainFormWindowItems mainFormWindowItems){
        this.currentProject = currentProject;
        this.filePath = filePath;
        this.mainFormWindowItems = mainFormWindowItems;
        setTargetDestinationPanel();
        scrollablePanel = makeScrollablePanel();
    }

    public JPanel getScrollablePanel() {
        return scrollablePanel;
    }

    public void setTargetDestinationPanel(){
        GroupLayout groupLayoutFirstPanel = new GroupLayout(targetDestinationPanel);
        groupLayoutFirstPanel.setAutoCreateGaps(true);
        groupLayoutFirstPanel.setAutoCreateContainerGaps(true);
        targetDestinationPanel.setLayout(groupLayoutFirstPanel);

        groupLayoutFirstPanel.setHorizontalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup()
                                .addGroup(groupLayoutFirstPanel.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getDefaultTargetDestination())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getOwnTargetDestination()))
                                .addComponent(mainFormWindowItems.getDefaultTargetDestinationDesc()))
        );
        groupLayoutFirstPanel.setVerticalGroup(
                groupLayoutFirstPanel.createSequentialGroup()
                        .addGroup(groupLayoutFirstPanel.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultTargetDestination())
                                .addComponent(mainFormWindowItems.getOwnTargetDestination()))
                        .addComponent(mainFormWindowItems.getDefaultTargetDestinationDesc()));
    }

    private JPanel getTargetDestinationPanel() {
        return targetDestinationPanel;
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
//        JFileChooser defaultTargetFile = new JFileChooser();
//        defaultTargetFile.setCurrentDirectory(new File(filePath.toPath().toFile().toString()));

        JPanel targetDestinationPanel = getTargetDestinationPanel();

        setVerticalGroupFroLayout(groupLayout, targetDestinationPanel);
        setHorizontalGroupFroLayout(groupLayout, targetDestinationPanel);

//        if (checkBoxForChooseDirs.isSelected()){
//            showFormWindow(currentProject, filePath);
//        }

//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(9, 4));
//
////        String src = "D:\sramk\Documents\vysoka_skola\bakalarka\temp\backup";
////        String trg = "D:\sramk\Documents\vysoka_skola\2_rocnik\2_semestr\7OPR2\projects\cviceni12\slozka2";
//
//        System.out.println(filePath.toString());
//
//        JTextField source = new JTextField(20);
//        JTextField target = new JTextField(20);
//        JCheckBox privateCheckBox = new JCheckBox();
//        JCheckBoxMenuItem publicCheckBoxMenuItem = new JCheckBoxMenuItem("ahoj");
//        JColorChooser colorChooser = new JColorChooser();
//        List<String> text = new ArrayList<>(Arrays.asList("private", "public", "protected"));
//        JComboBox<String> comboBox = new ComboBox(text.toArray());
//        JFileChooser defaultTargetFile = new JFileChooser();
//        defaultTargetFile.setCurrentDirectory(new File(filePath.toPath().toFile().toString()));
//        //  Nefunkční - dopracovat
//        System.out.println(new File(filePath.toPath().resolve("PlantUmlFiles").toFile().toString()));
//
        return myPanel;
    }

    private void setHorizontalGroupFroLayout(GroupLayout groupLayout, JPanel targetDestinationPanel) {
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(LEADING)
                                .addComponent(targetDestinationPanel)
                                .addComponent(mainFormWindowItems.getCheckBoxForAllInDir())
                                .addComponent(mainFormWindowItems.getCheckBoxForClasses())
                                .addComponent(mainFormWindowItems.getCheckBoxForAttributes())
                                .addComponent(mainFormWindowItems.getDefaultTargetLocation())
                                .addComponent(mainFormWindowItems.getTargetLabel())
                                .addComponent(mainFormWindowItems.getPrivateCheckBox()))
                        .addGroup(groupLayout.createParallelGroup(LEADING)
                                .addComponent(mainFormWindowItems.getCheckBoxForChooseDirs())
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaces())
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(mainFormWindowItems.getCheckBoxForMethods())
                                        .addGap(80)
                                        .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses()))
                                .addComponent(mainFormWindowItems.getChooseDifferentTargetLocation())
                                .addComponent(mainFormWindowItems.getTarget())
                                .addGroup(groupLayout.createParallelGroup(LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(mainFormWindowItems.getPublicCheckBox())
                                                .addComponent(mainFormWindowItems.getProtectedCheckBox())
                                                .addComponent(mainFormWindowItems.getDefaultCheckBox()))))
        );
    }

    private void setVerticalGroupFroLayout(GroupLayout groupLayout, JPanel targetDestinationPanel) {
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(targetDestinationPanel))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForAllInDir())
                                .addComponent(mainFormWindowItems.getCheckBoxForChooseDirs()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForClasses())
                                .addComponent(mainFormWindowItems.getCheckBoxForInterfaces()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getCheckBoxForAttributes())
                                .addComponent(mainFormWindowItems.getCheckBoxForMethods())
                                .addComponent(mainFormWindowItems.getCheckBoxForInnerClasses()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getDefaultTargetLocation())
                                .addComponent(mainFormWindowItems.getChooseDifferentTargetLocation()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getTargetLabel())
                                .addComponent(mainFormWindowItems.getTarget()))
                        .addGroup(groupLayout.createParallelGroup(BASELINE)
                                .addComponent(mainFormWindowItems.getPrivateCheckBox())
                                .addComponent(mainFormWindowItems.getPublicCheckBox())
                                .addComponent(mainFormWindowItems.getProtectedCheckBox())
                                .addComponent(mainFormWindowItems.getDefaultCheckBox()))
        );
    }
}
