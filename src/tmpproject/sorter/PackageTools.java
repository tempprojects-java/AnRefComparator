package tmpproject.sorter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageTools {

    public static List<Class> getClasses(String packageName) throws ClassNotFoundException {
        URLConnection connection = getUrlConnection(packageName);

        if (isItJarFile(connection)) {
            return getClasses(packageName, (JarURLConnection) connection);
        }

        return getClasses(packageName, connection);
    }

    private static boolean isItJarFile(URLConnection connection) {
        return (connection instanceof JarURLConnection);
    }

    private static URLConnection getUrlConnection(String packageName) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');

        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            URL resource = cld.getResource(path);
            return resource.openConnection();

        } catch (NullPointerException e) {
            throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Null pointer exception)");
        } catch (final IOException e) {
            throw new ClassNotFoundException(
                    "IOException was thrown when trying to get all resources for "
                            + packageName, e);
        }
    }

    private static List<Class> getClasses(String packageName, JarURLConnection connection) throws ClassNotFoundException {
        List<Class> result = new ArrayList<>();

        try {
            JarFile jarFile = connection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            String name;

            for (JarEntry jarEntry = null; entries.hasMoreElements()
                    && ((jarEntry = entries.nextElement()) != null); ) {

                name = jarEntry.getName();

                if (name.contains(".class")) {
                    name = name.substring(0, name.length() - 6).replace('/', '.');

                    if (name.contains(packageName)) {
                        result.add(Class.forName(name));
                    }
                }
            }

            return result;
        } catch (IOException e) {
            throw new ClassNotFoundException(
                    "IOException was thrown when trying to get all resources for "
                            + packageName, e);
        }
    }

    private static List<Class> getClasses(String packageName, URLConnection connection) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');

        try {
            File basePath = new File(URLDecoder.decode(connection.getURL().getPath(), "UTF-8"));
            File[] files = basePath.listFiles();
            List<Class> result = new ArrayList<>();

            for (File file : files) {
                if ((file.getName().endsWith(".class")) && (!file.getName().contains("$"))) {
                    int index = basePath.getPath().indexOf(path);
                    String packagePrefix = basePath.getPath().substring(index).replace('/', '.');

                    try {
                        String className = packagePrefix + '.' + file.getName().substring(0, file.getName().length() - 6);
                        result.add(Class.forName(className));
                    } catch (NoClassDefFoundError e) {

                    }
                }
            }
            return result;

        } catch (final UnsupportedEncodingException e) {
            throw new ClassNotFoundException(
                    packageName
                            + " does not appear to be a valid package (Unsupported encoding)",
                    e);
        }
    }
}
