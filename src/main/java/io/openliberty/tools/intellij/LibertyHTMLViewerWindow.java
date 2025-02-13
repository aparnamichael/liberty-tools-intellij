package io.openliberty.tools.intellij;

import com.intellij.openapi.project.Project;
import com.intellij.ui.jcef.JBCefBrowser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LibertyHTMLViewerWindow {
    private final JPanel contentPanel = new JPanel();

    public LibertyHTMLViewerWindow(@NotNull Project project) {
        JBCefBrowser browser = new JBCefBrowser();
//        browser.loadURL("https://www.google.com/");
        browser.loadHTML(getHTMLContent());
        contentPanel.add(browser.getComponent());
    }

    private String getHTMLContent() {
        return "<html>" +
                "<head>" +
                "    <title>Hello World with TypeScript</title>" +
                "    <script type=\"module\">" +
                "        document.addEventListener(\"DOMContentLoaded\", () => {" +
                "            const button = document.getElementById(\"okButton\");" +
                "            if (button) {" +
                "                button.addEventListener(\"click\", () => {" +
                "                    alert(\"OK button clicked!\");" +
                "                });" +
                "            }" +
                "        });" +
                "    </script>" +
                "</head>" +
                "<body>" +
                "    <h1>Hello World</h1>" +
                "    <button id=\"okButton\">OK</button>" +
                "</body>" +
                "</html>";
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
