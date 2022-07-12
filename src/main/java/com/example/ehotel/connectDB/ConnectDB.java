package com.example.ehotel.connectDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class ConnectDB {
    private static final String connectMongo = "mongodb://localhost:27017";
    private static final String database = "ehotel";
    private static MongoClient mongoClient;

    public static MongoCollection<Document> connectDatabase(String collection) {
        mongoClient = MongoClients.create(connectMongo);

        MongoDatabase databaseName = mongoClient.getDatabase(database);

        return databaseName.getCollection(collection);
    }

    public static void closeConnect() {
        mongoClient.close();
    }
}
