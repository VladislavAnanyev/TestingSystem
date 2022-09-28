package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.service.FileSystemStorageService;
import com.example.mywebquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileSystemStorageService fileStorageService;

    @PostMapping(path = "/upload")
    public String handleFileUpload(Model model, @RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal User authUser) {
        userService.uploadPhoto(file, authUser.getUserId());
        User userLogin = userService.loadUserByUserIdProxy(authUser.getUserId());
        model.addAttribute("user", userLogin);
        return "profile";
    }

    @GetMapping("/videos/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileName") String fileName) {
        System.out.println(httpRangeList);
        System.out.println("Привет");
        return Mono.just(getContent("/videos", fileName, httpRangeList, "video"));
    }

    private ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        try {
            fileSize = Optional.ofNullable(fileName)
                    .map(file -> Paths.get(getFilePath(location), file))
                    .map(this::sizeFromFile)
                    .orElse(0L);
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", contentTypePrefix+"/" + fileType)
                        .header("Content-Length", String.valueOf(fileSize))
                        .body(readByteRange(location, fileName, rangeStart, fileSize - 1));
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }
            data = readByteRange(location, fileName, rangeStart, rangeEnd);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", contentTypePrefix +
                        "/" + fileType)
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", contentLength)
                .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
    }
    public byte[] readByteRange(String location, String filename, long start, long end) throws IOException {
        Path path = Paths.get(getFilePath(location), filename);
        try (InputStream inputStream = (Files.newInputStream(path));
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[128];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }
    private String getFilePath(String location) {
        /*URL url = this.getClass().getResource(location);
        return new File(url.getFile()).getAbsolutePath();*/
        return "C:\\Users\\avlad\\IdeaProjects\\WebQuizSystem\\src\\main\\resources\\static\\videos";
    }
    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileStorageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
