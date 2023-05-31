package ibf2022.batch2.csf.backend.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.UploadModel;

import java.util.ArrayList;

@Service
public class UnzipService {

    public List<UploadModel> unzip(MultipartFile file) throws IOException {

        List<UploadModel> files = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry entry = zis.getNextEntry();

        while (entry != null) {

            if (!entry.isDirectory()) {
                String filename = entry.getName();
                String contentType = getContentType(filename);
                long contentSize = entry.getSize();
                byte[] extractedFile = extractFile(zis);
                UploadModel uploadModel = new UploadModel(filename, contentType, contentSize, extractedFile);
                files.add(uploadModel);
            }

            zis.closeEntry();
            entry = zis.getNextEntry();
        }

        zis.close();
        return files;
    }

    public byte[] extractFile(ZipInputStream zis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = zis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();

        return bos.toByteArray();
    }

    public String getContentType(String filename) {

        String extension = filename.substring(filename.lastIndexOf("."));
        String finalExtension = extension.replace(".", "");
        return "image/%s".formatted(finalExtension);

    }

}
