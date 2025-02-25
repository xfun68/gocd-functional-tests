package com.thoughtworks.cruise.util;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;
import static java.lang.System.getProperty;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;


public class FileUtil {
    private static final String CRUISE_TMP_FOLDER = "cruise" + "-" + UUID.randomUUID().toString();

    private static final boolean ON_NETWARE = Os.isFamily("netware");
    private static final boolean ON_DOS = Os.isFamily("dos");

    public static final FileFilter NONHIDDEN_FILE_FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            return !isHidden(pathname);
        }
    };

    public static boolean isFolderEmpty(File folder) {
        if (folder == null) {
            return true;
        }
        File[] files = folder.listFiles();
        return files != null && files.length > 0;
    }

    public static String makepath(String... paths) {
        StringBuilder fullPath = new StringBuilder();
        for (String path : paths) {
            fullPath.append(path);
            fullPath.append("/");
        }
        return fullPath.toString().substring(0, fullPath.length() - 1);
    }

    public static File writeStringToTempFile(String fileName, String extension, String contents) throws Exception {
        File tempFile = TestFileUtil.createTempFile(fileName + "." + extension);
        tempFile.deleteOnExit();
        FileUtils.writeStringToFile(tempFile, contents);
        return tempFile;
    }

    public static String readToEnd(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return readToEnd(input);
    }

    public static String readToEnd(InputStream input) throws IOException {
        @SuppressWarnings("unchecked") List<String> list = IOUtils.readLines(input);
        StringBuilder builder = new StringBuilder();
        for (String line : list) {
            builder.append(line);
            builder.append(lineSeparator());
        }
        return builder.toString().trim();
    }

    public static boolean isHidden(File file) {
        return file.isHidden() || file.getName().startsWith(".");
    }

    public static void writeContentToFile(String content, File file) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            IOUtils.write(content, outputStream);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void writeContentToFile(byte[] content, File file) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            IOUtils.write(content, outputStream);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static String readContentFromFile(File file) throws IOException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            return IOUtils.toString(input);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }


    public static boolean deleteFolder(File testFolder) {
        return FileUtils.deleteQuietly(testFolder);
    }


    public static String normalizePath(File fileToNormalize) {
        return normalizePath(fileToNormalize.getPath());
    }

    public static String normalizePath(String filePath) {
        return StringUtils.replace(filePath, "\\", "/");
    }

    public static String fileNameFromPath(String src) {
        String[] urlparts = normalizePath(src).split("/");
        return urlparts[urlparts.length - 1];
    }

    public static String applyBaseDirIfRelativeAndNormalize(File baseDir, File actualFileToUse) {
        return normalizePath(applyBaseDirIfRelative(baseDir, actualFileToUse));
    }

    public static File applyBaseDirIfRelative(File baseDir, File actualFileToUse) {
        if (actualFileToUse == null) {
            return baseDir;
        }
        if (actualFileToUse.isAbsolute()) {
            return actualFileToUse;
        }
        return new File(baseDir, actualFileToUse.getPath());

    }

    public static void createParentFolderIfNotExist(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static String lineSeparator() {
        return getProperty("line.separator");
    }

    public static String fileseparator() {
        return File.separator;
    }

     public static String toFileURI(File file) {
        URI uri = file.toURI();
        String uriString = uri.toASCIIString();
        return uriString.replaceAll("^file:/", "file:///");
    }

    public static Boolean isStructureSame(File folder1, File folder2) {
        List<String> structure1 = flatten(folder1);
        List<String> structure2 = flatten(folder2);
        Collections.sort(structure1);
        Collections.sort(structure2);
        return structure1.equals(structure2);
    }

    private static List<String> flatten(File folder1) {
        ArrayList<String> list = new ArrayList<String>();
        flatten(list, folder1.getAbsolutePath(), folder1);
        return list;
    }

    private static void flatten(List<String> list, String absPath, File file1) {
        if (file1.isFile()) {
            list.add(file1.getAbsolutePath().replace(absPath, ""));
        } else if (file1.isDirectory()) {
            for (File file : file1.listFiles()) {
                flatten(list, absPath, file);
            }
        } else {
            return;
        }
    }

    public static boolean isSubdirectoryOf(File parent, File subdirectory) throws IOException {
        File parentFile = parent.getCanonicalFile();
        File current = subdirectory.getCanonicalFile();
        while (current != null) {
            if (current.equals(parentFile)) {
                return true;
            }
            current = current.getParentFile();
        }
        return false;
    }

    //CopiedFromAnt

    public static boolean isAbsolutePath(String filename) {
        int len = filename.length();
        if (len == 0) {
            return false;
        }
        char sep = File.separatorChar;
        filename = filename.replace('/', sep).replace('\\', sep);
        char c = filename.charAt(0);
        if (!(ON_DOS || ON_NETWARE)) {
            return (c == sep);
        }
        if (c == sep) {
            // CheckStyle:MagicNumber OFF
            if (!(ON_DOS && len > 4 && filename.charAt(1) == sep)) {
                return false;
            }
            // CheckStyle:MagicNumber ON
            int nextsep = filename.indexOf(sep, 2);
            return nextsep > 2 && nextsep + 1 < len;
        }
        int colon = filename.indexOf(':');
        return (Character.isLetter(c) && colon == 1
                && filename.length() > 2 && filename.charAt(2) == sep)
                || (ON_NETWARE && colon > 0);
    }

    public static String[] dissect(String path) {
        char sep = File.separatorChar;
        path = path.replace('/', sep).replace('\\', sep);

        // make sure we are dealing with an absolute path
        if (!isAbsolutePath(path)) {
            throw new RuntimeException(path + " is not an absolute path");
        }
        String root = null;
        int colon = path.indexOf(':');
        if (colon > 0 && (ON_DOS || ON_NETWARE)) {

            int next = colon + 1;
            root = path.substring(0, next);
            char[] ca = path.toCharArray();
            root += sep;
            //remove the initial separator; the root has it.
            next = (ca[next] == sep) ? next + 1 : next;

            StringBuffer sbPath = new StringBuffer();
            // Eliminate consecutive slashes after the drive spec:
            for (int i = next; i < ca.length; i++) {
                if (ca[i] != sep || ca[i - 1] != sep) {
                    sbPath.append(ca[i]);
                }
            }
            path = sbPath.toString();
        } else if (path.length() > 1 && path.charAt(1) == sep) {
            // UNC drive
            int nextsep = path.indexOf(sep, 2);
            nextsep = path.indexOf(sep, nextsep + 1);
            root = (nextsep > 2) ? path.substring(0, nextsep + 1) : path;
            path = path.substring(root.length());
        } else {
            root = File.separator;
            path = path.substring(1);
        }
        return new String[]{root, path};
    }

    public static File normalize(final String path) {
        Stack s = new Stack();
        String[] dissect = dissect(path);
        s.push(dissect[0]);

        StringTokenizer tok = new StringTokenizer(dissect[1], File.separator);
        while (tok.hasMoreTokens()) {
            String thisToken = tok.nextToken();
            if (".".equals(thisToken)) {
                continue;
            }
            if ("..".equals(thisToken)) {
                if (s.size() < 2) {
                    // Cannot resolve it, so skip it.
                    return new File(path);
                }
                s.pop();
            } else { // plain component
                s.push(thisToken);
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.size(); i++) {
            if (i > 1) {
                // not before the filesystem root and not after it, since root
                // already contains one
                sb.append(File.separatorChar);
            }
            sb.append(s.elementAt(i));
        }
        return new File(sb.toString());
    }

    public static String removeLeadingPath(File leading, File path) {
        String l = normalize(leading.getAbsolutePath()).getAbsolutePath();
        String p = normalize(path.getAbsolutePath()).getAbsolutePath();
        if (l.equals(p)) {
            return "";
        }
        // ensure that l ends with a /
        // so we never think /foo was a parent directory of /foobar
        if (!l.endsWith(File.separator)) {
            l += File.separator;
        }
        return (p.startsWith(l)) ? p.substring(l.length()) : p;
    }

    public static boolean isSymbolicLink(File parent, String name)
            throws IOException {
        if (parent == null) {
            File f = new File(name);
            parent = f.getParentFile();
            name = f.getName();
        }
        File toTest = new File(parent.getCanonicalPath(), name);
        return !toTest.getAbsolutePath().equals(toTest.getCanonicalPath());
    }

    public static void createFilesByPath(File baseDir, String... files) throws IOException {
        for (String file : files) {
            if (file.endsWith("/")) {
                File file1 = new File(baseDir, file);
                file1.mkdirs();
            } else {
                File file1 = new File(baseDir, file);
                file1.getParentFile().mkdirs();
                file1.createNewFile();
            }
        }
    }

    public static String subtractPath(File rootPath, File file) {
        String fullPath = normalizePath(file.getParentFile());
        String basePath = normalizePath(rootPath);
        String trimedPath = StringUtils.removeStart(StringUtils.removeStart(fullPath, basePath), "/");
        return trimedPath;
    }

    private static final long ONE_KB = 1024;

    private static final long ONE_MB = ONE_KB * ONE_KB;

    private static final long ONE_GB = ONE_KB * ONE_MB;

    private static final long ONE_TB = ONE_KB * ONE_GB;

    private static final long ONE_PB = ONE_KB * ONE_TB;


    public static String byteCountToDisplaySize(long size) {
        if (size >= ONE_PB) {
            return displaySizeFor(size, ONE_PB, " PB");
        } else if (size >= ONE_TB) {
            return displaySizeFor(size, ONE_TB, " TB");
        } else if (size >= ONE_GB) {
            return displaySizeFor(size, ONE_GB, " GB");
        } else if (size >= ONE_MB) {
            return displaySizeFor(size, ONE_MB, " MB");
        } else if (size >= ONE_KB) {
            return displaySizeFor(size, ONE_KB, " KB");
        } else {
            return String.valueOf(size) + " bytes";
        }
    }

    private static String displaySizeFor(long size, double unit, String unitInString) {
        BigDecimal bigDecimal = new BigDecimal((double) size / unit);
        return bigDecimal.setScale(1, RoundingMode.HALF_UP) + unitInString;
    }

    public static List<String> readLines(InputStream resource) throws IOException {
        String output = readToEnd(resource);
        List<String> stringList = Arrays.asList(output.split("[\r\n]+"));
        return stringList;
    }

    public static File createTempFolder() {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), CRUISE_TMP_FOLDER);
        File dir = new File(tempDir, UUID.randomUUID().toString());
        dir.mkdirs();
        return dir;
    }

    public static boolean isFolderInsideSandbox(String path) {
        File fileAtPath = new File(path);
        if (fileAtPath.isAbsolute()) {
            return false;
        }
        try {
            if (!FileUtil.isSubdirectoryOf(new File("."), fileAtPath)) {
                return false;
            }
        } catch (IOException e) {
            bomb("Dest folder specification is not valid. " + e.getMessage());
        }
        return true;
    }
}


