package uk.co.jflm;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

class FileLoader {

    List<ClientInputFile> loadFiles() {
        File file = new File("/tmp/ExampleClass.java");
        return Arrays.asList(new CliInputFile(file));
    }

}
