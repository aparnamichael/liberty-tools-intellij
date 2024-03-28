/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 *  SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package io.openliberty.tools.intellij.runConfiguration;

import com.intellij.application.options.ModuleDescriptionsComboBox;
import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.execution.ui.DefaultJreSelector;
import com.intellij.execution.ui.JrePathEditor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.JdkComboBox;
import com.intellij.openapi.roots.ui.configuration.ProjectJdkConfigurableUi;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.StateRestoringCheckBox;
import io.openliberty.tools.intellij.LibertyModules;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Editor associated with Liberty run & debug configurations. Defines when configuration changes are updated, default values, etc.
 */
public class LibertyRunSettingsEditor extends SettingsEditor<LibertyRunConfiguration> {

    private JPanel root;
    private LabeledComponent<EditorTextField> editableParams;
    private LabeledComponent<ComboBox> libertyModule;

    private StateRestoringCheckBox runInContainerCheckBox;
    private JrePathEditor myJrePathEditor;
    private final ConfigurationModuleSelector myModuleSelector;
    private LabeledComponent<ModuleDescriptionsComboBox> myModule;

    public LibertyRunSettingsEditor(Project project) {
        myModuleSelector = new ConfigurationModuleSelector(project, getModulesComponent());
        myJrePathEditor.setDefaultJreSelector(DefaultJreSelector.fromModuleDependencies(getModulesComponent(), false));
        libertyModule.getComponent().setModel(new DefaultComboBoxModel(LibertyModules.getInstance().getLibertyBuildFilesAsString(project).toArray()));
    }

    @Override
    protected void resetEditorFrom(@NotNull LibertyRunConfiguration configuration) {

        String buildFile = configuration.getBuildFile();
        for (int i = 0; i < libertyModule.getComponent().getItemCount(); i++) {
            String libertyModuleBuildFile = libertyModule.getComponent().getItemAt(i).toString();
            if (buildFile.equals(libertyModuleBuildFile)) {
                // need to use setSelectedIndex instead of setSelectedItem, setSelectedItem(buildFile) results in no behaviour change
                libertyModule.getComponent().setSelectedIndex(i);
                break;
            }
        }
        runInContainerCheckBox.setSelected(configuration.runInContainer());
        editableParams.getComponent().setText(configuration.getParams());
    }

    @Override
    protected void applyEditorTo(@NotNull LibertyRunConfiguration configuration) throws ConfigurationException {
        configuration.setParams(editableParams.getComponent().getText());
        configuration.setBuildFile(String.valueOf(libertyModule.getComponent().getSelectedItem()));
        configuration.setRunInContainer(runInContainerCheckBox.isSelected());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return root;
    }

    public void createUIComponents() {
        ComboBox comboBox = new ComboBox();
        libertyModule = new LabeledComponent<>();
        libertyModule.setComponent(comboBox);
        editableParams = new LabeledComponent<>();
        editableParams.setComponent(new EditorTextField());
        runInContainerCheckBox = new StateRestoringCheckBox();

//        final var ui = new ProjectJdkConfigurableUi();
//        libertyJdkComponent = new LabeledComponent<>();
//        libertyJdkComponent.setComponent(ui.getJdkComboBox());



    }

    public ModuleDescriptionsComboBox getModulesComponent() {
        return myModule.getComponent();
    }
}