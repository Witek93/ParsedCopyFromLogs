import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;

public class FileUtils {
    static Optional<String> getCurrentProjectDirectory(AnActionEvent actionEvent) {
        return ofNullable(actionEvent)
                .map(event -> event.getData(PROJECT))
                .map(Project::getBasePath);
    }

    static Stream<Path> getContentOfDirectory(Path path) {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            return stream(directoryStream.spliterator(), false);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

}
