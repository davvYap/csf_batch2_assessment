package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ibf2022.batch2.csf.backend.models.UploadModel;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public List<URL> upload(List<UploadModel> files) {

		List<URL> url = new ArrayList<>();

		for (UploadModel file : files) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getContentSize());

			String uuid = UUID.randomUUID().toString().substring(0, 4);
			String key = uuid + file.getFilename();
			InputStream inputStream = new ByteArrayInputStream(file.getContent());

			PutObjectRequest putReq = new PutObjectRequest("csfbatch2assesment", key, inputStream, metadata);

			putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putReq);
			url.add(s3.getUrl("csfbatch2assesment", key));
		}

		return url;
	}

}
