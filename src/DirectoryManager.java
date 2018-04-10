import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class DirectoryManager extends SimpleFileVisitor<Path> {
    private static List<Path> pathsList = new ArrayList<>();
    private File result = new File("C:\\Users\\Denis\\Desktop\\result.txt");

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        DirectoryManager manager = new DirectoryManager();
        String directoryPath = null;

        System.out.println("Введите путь папки для сканирования текстовых файлов: ");
        directoryPath = reader.readLine();
        Path defaultPath = Paths.get(directoryPath);
        Files.walkFileTree(defaultPath, manager);
        manager.writeFiles(manager.sortByFileName(pathsList));
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".txt")) {
            pathsList.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    public List<Path> sortByFileName(List<Path> pathsList) {
        return pathsList.stream()
                .sorted(Comparator.comparing(o -> o.getFileName().toString()))
                .collect(Collectors.toList());
    }

    public void writeFiles(List<Path> pathsList) {
        try (PrintWriter writer = new PrintWriter(result)) {
            for (Path p : pathsList) {
                List<String> lines = Files.readAllLines(p);
                lines.forEach(writer::write);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
