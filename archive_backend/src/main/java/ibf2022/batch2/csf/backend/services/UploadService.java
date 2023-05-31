package ibf2022.batch2.csf.backend.services;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.ArchivesModel;
import ibf2022.batch2.csf.backend.models.DisplayModel;
import ibf2022.batch2.csf.backend.models.UploadModel;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Service
public class UploadService {

    @Autowired
    private UnzipService unzipSvc;

    @Autowired
    private ImageRepository imgRepo;

    @Autowired
    private ArchiveRepository archiveRepo;

    public List<URL> upload(MultipartFile file) throws IOException {
        List<UploadModel> uploadedFiles = unzipSvc.unzip(file);

        return imgRepo.upload(uploadedFiles);
    }

    public Document recordBundle(ArchivesModel archive) {
        return archiveRepo.recordBundle(archive);
    }

    public ArchivesModel getBundleByBundleId(String id) {
        Document doc = archiveRepo.getBundleByBundleId(id);
        ArchivesModel model = null;
        if (doc != null) {
            model = ArchivesModel.convertFromDocument(doc);
        }
        return model;
    }

    public List<DisplayModel> getAllBundles() {
        List<Document> docs = archiveRepo.getBundles();
        if (docs == null) {
            return null;
        }

        return docs.stream().map(d -> DisplayModel.convertFromDocument(d)).toList();
    }

}