package ibf2022.batch2.csf.backend.repositories;

import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.ArchivesModel;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mongo;

	// TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	// db.archives.insertOne(
	// {
	// bundleId: '12345678',
	// date: new Date(Date.now()),
	// title: 'MyArchive',
	// name: 'David',
	// comments: 'This is so cool !',
	// urls:
	// ['https://csfbatch2assesment.sgp1.digitaloceanspaces.com/b6a6Boucicaut-Meister.jpg',
	// 'https://csfbatch2assesment.sgp1.digitaloceanspaces.com/958da273002h.jpg'
	// ]
	// }
	// )
	//
	public Document recordBundle(ArchivesModel archive) {
		Document d = Document.parse(archive.toJsonObject().toString());
		return mongo.insert(d, "archives");

	}

	// TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	// db.archives.findOne({bundleId : '12345678'})
	//
	public Document getBundleByBundleId(String id) {
		Query query = Query.query(Criteria.where("bundleId").is(id));
		return mongo.findOne(query, Document.class, "archives");
	}

	// TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	// db.archives.aggregate([
	// {$project: {_id:0, title:1, date:1}},
	// {$sort: {date:-1, title:1}}
	// ])
	//
	public List<Document> getBundles() {
		ProjectionOperation pop = Aggregation.project("title", "date").andExclude("_id");
		SortOperation sop = Aggregation.sort(Sort.by(Direction.DESC, "date")).and(Sort.by(Direction.ASC, "title"));

		Aggregation pipeline = Aggregation.newAggregation(pop, sop);

		AggregationResults<Document> results = mongo.aggregate(pipeline, "archives", Document.class);

		List<Document> rs = results.getMappedResults();

		return rs;
	}

}
