package checker;

import java.nio.file.Path;
import java.util.HashSet;

public class ExecuteScriptFileNames {
    private HashSet<Path> executeScriptFileNames = new HashSet<>();

    /**
     *
     * @param pathP путь который надо проверить
     * @return использовался ли путь
     */
    public boolean checkPath(Path pathP) {
        if (executeScriptFileNames.contains(pathP)) return true;

        executeScriptFileNames.add(pathP);
        return false;
    }

    /**
     * Очищает список выполненных скриптов
     */
    public void clearList() {
        executeScriptFileNames.clear();
    }

}
