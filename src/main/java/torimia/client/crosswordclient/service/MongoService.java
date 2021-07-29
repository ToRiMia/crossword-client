package torimia.client.crosswordclient.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class MongoService {

    public static final String MONGO_URI = "mongodb://root:example@localhost:27017/crossword_mongo_db?authSource=admin";
    public static final String DB_NAME = "crossword_mongo_db";
    public static final String COLLECTION_USER = "user";
    public static final String COLLECTION_GAME = "game";

    public void deleteUser(String login) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(COLLECTION_USER);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("login", login);

            log.info(String.valueOf(collection.deleteOne(whereQuery)));
        }
    }

    public void deleteGame(String gameId) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(COLLECTION_GAME);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("gameId", gameId);

            log.info(String.valueOf(collection.deleteOne(whereQuery)));
        }
    }

    public Document findById(String gameId) {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            var database = mongoClient.getDatabase(DB_NAME);
            var collection = database.getCollection(COLLECTION_GAME);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("gameId", gameId);

            return collection.find(whereQuery).first();
        }
    }
}

