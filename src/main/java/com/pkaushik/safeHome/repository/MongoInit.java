package com.pkaushik.safeHome.repository;

//TODO: make class singleton
public class MongoInit {

    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:12121")); 

    
}