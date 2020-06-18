package com.yerti.core.database.mongodb

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.conversions.Bson

class MongoDBManager(private val database: String) {
    private var players: MongoCollection<Document>? = null
    private var mcserverdb: MongoDatabase? = null
    private var client: MongoClient? = null
    fun connect(ip: String?, port: Int): Boolean {
        client = MongoClient(ip, port)
        mcserverdb = client!!.getDatabase(database)
        players = mcserverdb!!.getCollection("players")
        return true
    }

    fun store(key: String?, param: Any?) {
        val document = Document(key, param)
        players!!.insertOne(document)
    }

    fun store(params: Map<String?, Any?>) {
        val doc = Document()
        params.forEach { (key: String?, value: Any?) -> doc.append(key, value) }
        players!!.insertOne(doc)
    }

    fun update(key: String?, value: Any?, params: Map<String?, Any?>) {
        val document = Document()
        params.forEach { (key: String?, value: Any?) -> document.append(key, value) }
        val found = Document(key, value)
        players!!.updateOne(found, document)
    }

    operator fun get(vararg filters: Bson?): Document {
        return players!!.find(Filters.and(*filters)).first()
    }

}