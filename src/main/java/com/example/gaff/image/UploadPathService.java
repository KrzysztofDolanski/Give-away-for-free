package com.example.gaff.image;

import java.io.File;

public interface UploadPathService {
    File getFilePath(String modifiedFileName, String path);
}
