package com.yerti.core.database.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Map;

public class MongoDBManager {
    private MongoCollection<org.bson.Document> players;
    private MongoDatabase mcserverdb;
    private MongoClient client;
    private String database;

    public MongoDBManager(String database) {
        this.database = database;
    }

    public boolean connect(String ip, int port) {
        client = new MongoClient(ip, port);

        mcserverdb = client.getDatabase(database);
        players = mcserverdb.getCollection("players");
        return true;
    }

    public void store(String key, Object param) {
        Document document = new Document(key, param);
        players.insertOne(document);
    }

    public void store(Map<String, Object> params) {
        Document doc = new Document();

        params.forEach(doc::append);

        players.insertOne(doc);
    }

    public void update(String key, Object value, Map<String, Object> params) {
        Document document = new Document();

        params.forEach(document::append);

        Document found = new Document(key, value);

        players.updateOne(found, document);
    }

    public Document get(Bson... filters) {
        return players.find(Filters.and(filters)).first();
    }


}
