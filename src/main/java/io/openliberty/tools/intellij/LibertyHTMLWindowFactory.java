package io.openliberty.tools.intellij;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;


public class LibertyHTMLWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        LibertyHTMLViewerWindow libertyHTMLViewerWindow = new LibertyHTMLViewerWindow(project);
        Content content = ContentFactory.getInstance().createContent(libertyHTMLViewerWindow.getContentPanel(),
                "Test Html Viewer",false);
        toolWindow.getContentManager().addContent(content);
    }
}

