package server.repository;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AudioRepository {
    private LinkedList<FileSystemResource> fsr_list = new LinkedList<>();



    public void add_test_file() {
        FileSystemResource fsr = new FileSystemResource(
                new File(System.getProperty("user.dir") + "\\"+ "resources\\B64DJ1.wav"));
        fsr_list.add(fsr);
    }

    public LinkedList<FileSystemResource> getFsr_list() {
        return fsr_list;
    }

    //(new File
            //(System.getProperty("user.dir") + "\\"+ "B64DJ1.wav"));
}
