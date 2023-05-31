package ibf2022.batch2.csf.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.ArchivesModel;
import ibf2022.batch2.csf.backend.models.DisplayModel;
import ibf2022.batch2.csf.backend.services.UploadService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping
@CrossOrigin(origins = "*")
public class UploadController {

	@Autowired
	private UploadService uploadSvc;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFile(@RequestPart String name, @RequestPart String title,
			@RequestPart(required = false) String comments, @RequestPart MultipartFile file) {

		String uploaderName = name;
		String uploaderTitle = title;
		String uploaderComments = comments;

		String filename = file.getOriginalFilename();
		String contentType = file.getContentType();
		// System.out.println("content-type >>> " + contentType);
		// String extension = filename.substring(filename.lastIndexOf("."));
		// String finalExtension = extension.replace(".", "");
		// System.out.println("extension >>> " + finalExtension);
		// System.out.println("final >>> " + "image/%s".formatted(finalExtension));

		// UPLOAD TO S3 BUCKET
		List<URL> urls = null;
		try {
			urls = uploadSvc.upload(file);
			urls.stream().forEach(url -> System.out.println(url.toString()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Json.createObjectBuilder()
							.add("error", e.getMessage()).build().toString());
		}

		// INSERT RECORD TO MONGO
		String bundleId = UUID.randomUUID().toString().substring(0, 8);
		List<String> urlStrings = urls.stream().map(url -> url.toString()).toList();
		Long date = new Date().toInstant().toEpochMilli();
		ArchivesModel archive = new ArchivesModel(bundleId, uploaderTitle, uploaderName, uploaderComments, date,
				urlStrings);
		uploadSvc.recordBundle(archive);

		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Json.createObjectBuilder()
						.add("bundleId", bundleId).build().toString());
	}

	// TODO: Task 5
	@GetMapping(path = "/bundle/{bundleId}")
	public ResponseEntity<String> getBundle(@PathVariable String bundleId) {
		ArchivesModel archive = uploadSvc.getBundleByBundleId(bundleId);

		if (archive == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Json.createObjectBuilder()
							.add("error", "bundleId not found").build().toString());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(archive.toJsonObject().toString());
	}

	// TODO: Task 6
	@GetMapping(path = "/bundles")
	public ResponseEntity<String> getAllBundles() {
		List<DisplayModel> archives = uploadSvc.getAllBundles();

		if (archives == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Json.createObjectBuilder()
							.add("error", "bundles is empty").build().toString());
		}

		JsonArrayBuilder jsArr = Json.createArrayBuilder();
		for (DisplayModel archive : archives) {
			jsArr.add(archive.toJsonObject());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(jsArr.build().toString());
	}

}
