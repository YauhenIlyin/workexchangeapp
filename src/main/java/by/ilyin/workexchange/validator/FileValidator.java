package by.ilyin.workexchange.validator;

import java.io.File;

public class FileValidator {

    public FileValidator() {
    }

    public boolean validateTxtFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0L;
    }

}
