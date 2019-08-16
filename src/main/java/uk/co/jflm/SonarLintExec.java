package uk.co.jflm;

import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.LogOutput;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.IssueListener;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;

import java.nio.file.Paths;
import java.util.List;

public class SonarLintExec {

    // TODO - DI
    private static final FileLoader fileLoader = new FileLoader();
    private static final PluginLoader pluginLoader = new PluginLoader();

    public static void main(String[] args) throws Exception {

        LogOutput logOutput = (formattedMessage, level) -> System.out.println(level + ": " + formattedMessage);

        StandaloneGlobalConfiguration globalConfiguration = StandaloneGlobalConfiguration.builder()
                .setLogOutput(logOutput)
                .addPlugins(pluginLoader.loadPlugins())
                .build();

        StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(globalConfiguration);

        List<ClientInputFile> inputFiles = fileLoader.loadFiles();

        StandaloneAnalysisConfiguration configuration = StandaloneAnalysisConfiguration.builder()
                .addInputFiles(inputFiles)
                .setBaseDir(Paths.get("/tmp/sonar-cli"))
                .build();

        engine.getLoadedAnalyzers();

        IssueListener issueListener = issue -> System.out.println(issue.getSeverity() + ": " + issue.getRuleName());
        engine.analyze(configuration, issueListener, logOutput, null);
    }

}
