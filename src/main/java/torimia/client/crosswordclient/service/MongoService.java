package torimia.client.crosswordclient.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
@RequiredArgsConstructor
public class MongoService {

    public static final String MONGO_URI = "mongodb://root:example@localhost:27017/crossword_mongo_db?authSource=admin";
    public static final String DB_NAME = "crossword_mongo_db";
    public static final String COLLECTION_USER = "user";
    private final String collectionGameName;

    public void deleteUser(String login) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(COLLECTION_USER);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("login", login);

            log.info(String.valueOf(collection.deleteOne(whereQuery)));
        }
    }

    public long deleteGame(String gameId) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(collectionGameName);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("gameId", gameId);

            DeleteResult deleteResult = collection.deleteOne(whereQuery);
            return deleteResult.getDeletedCount();
        }
    }

    public Document findById(String gameId) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(collectionGameName);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("gameId", gameId);

            return collection.find(whereQuery).first();
        }
    }
}

