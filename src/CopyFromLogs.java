import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.awt.Toolkit.getDefaultToolkit;

public class CopyFromLogs extends AnAction {

    public static final String PATH_TO_LOGS = "Logs";

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        FileUtils.getCurrentProjectDirectory(actionEvent)
                .map(getLogsPath())
                .flatMap(this::maybeContent)
                .ifPresent(updateClipboard());
    }

    @NotNull
    private Function<String, Path> getLogsPath() {
        return projectPath -> Paths.get(projectPath, PATH_TO_LOGS);
    }

    @NotNull
    private Consumer<String> updateClipboard() {
        return content -> getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
    }

    @NotNull
    private Optional<String> maybeContent(Path path) {
        return FileUtils.getContentOfDirectory(path)
                .peek(System.out::println)
                .reduce(toLastElement())
                .map(BuildInfoParser::forPath)
                .map(BuildInfoParser::toGoogleSheetFormat);
    }

    @NotNull
    private BinaryOperator<Path> toLastElement() {
        return (a, b) -> b;
    }
}
