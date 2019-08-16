package uk.co.jflm;

import org.sonar.api.internal.google.common.base.Charsets;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class CliInputFile implements ClientInputFile {

    private final File file;

    public CliInputFile(File file) {
        this.file = file;
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public boolean isTest() {
        return false;
    }

    @Override
    public Charset getCharset() {
        return Charsets.UTF_8;
    }

    @Override
    public <G> G getClientObject() {
        return null;
    }

    @Override
    public InputStream inputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String contents() throws IOException {
        return String.join("", Files.readAllLines(file.toPath()));
    }

    @Override
    public String relativePath() {
        return file.toPath().toString();
    }

    @Override
    public URI uri() {
        return file.toURI();
    }
}
