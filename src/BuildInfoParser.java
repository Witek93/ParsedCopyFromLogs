import java.nio.file.Path;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class BuildInfoParser {
    private Path path;

    public BuildInfoParser(Path path) {
        this.path = path;
    }

    public static BuildInfoParser forPath(Path path) {
        return new BuildInfoParser(path);
    }

    public String getMachine() {
        return "NOT IMPLEMENTED YET";
    }

    public String getLogsTime() {
        return ofNullable(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .orElse("");
    }

    public String getBuild() {
        return "NOT IMPLEMENTED YET";
    }

    public String toGoogleSheetFormat() {
        return Stream.of(getMachine(), getLogsTime(), getBuild())
                .collect(joining("\t"));

    }


}
