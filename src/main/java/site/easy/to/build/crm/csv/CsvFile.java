package site.easy.to.build.crm.csv;

import java.util.HashMap;

public class CsvFile {
    String name;
    String path;
    HashMap<String,Class<?>> types = new HashMap<>();

    public CsvFile() {
    }

    public CsvFile(String name, String path, HashMap<String, Class<?>> types) {
        this.name = name;
        this.path = path;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String, Class<?>> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, Class<?>> types) {
        this.types = types;
    }


}
