package by.ilyinyauhen.workexchangeapp.validator;

import java.io.File;

public class FileValidator {

    private FileValidator() {
    }

    public static boolean validateTxtFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0L;
    }

}
