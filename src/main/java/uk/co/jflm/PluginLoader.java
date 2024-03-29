package uk.co.jflm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginLoader {


    URL[] loadPlugins() throws IOException, URISyntaxException {
        URL pluginsDir = this.getClass().getClassLoader().getResource("plugins");
        if (pluginsDir == null) {
            throw new IllegalStateException("Couldn't find plugins");
        }

        if ("file".equalsIgnoreCase(pluginsDir.toURI().getScheme())) {
            return getPluginsUrls(pluginsDir);
        } else {
            return getPluginsUrlsWithFs(pluginsDir);
        }
    }

    private URL[] getPluginsUrlsWithFs(URL pluginsDir) throws IOException, URISyntaxException {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try (FileSystem fs = FileSystems.newFileSystem(pluginsDir.toURI(), env)) {
            return getPluginsUrls(pluginsDir);
        }
    }

    private URL[] getPluginsUrls(URL pluginsDir) throws IOException, URISyntaxException {
        List<URL> pluginsUrls = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(pluginsDir.toURI()), "*.jar")) {
            for (Path path : directoryStream) {
//                globalLogOutput.log("Found plugin: " + path.getFileName().toString(), LogOutput.Level.DEBUG);

                URL newUrl;
                if ("file".equalsIgnoreCase(pluginsDir.toURI().getScheme())) {
                    newUrl = path.toUri().toURL();
                } else {
                    // any attempt to convert path directly to URL or URI will result in having spaces double escaped
                    newUrl = new URL(pluginsDir, path.toString());
                }
                pluginsUrls.add(newUrl);
            }
        }
        return pluginsUrls.toArray(new URL[0]);
    }


}
