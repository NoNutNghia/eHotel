package com.example.ehotel.connectDB;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


public class ConnectDB {
    private static final String connectMongo = "mongodb://localhost:27017";
    private static final String database = "ehotel";
    private static MongoClient mongoClient;

    public static MongoCollection<Document> connectDatabase(String collection) {
        mongoClient = MongoClients.create(connectMongo);

        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase databaseName = mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);

        return databaseName.getCollection(collection);
    }

    public static void closeConnect() {
        mongoClient.close();
    }
}
