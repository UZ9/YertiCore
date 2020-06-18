package com.yerti.core.database

import org.bukkit.plugin.Plugin
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class SQLiteManager(plugin: Plugin, database: String, user: String?, pass: String?) {
    private var connection: Connection? = null
    var file: File? = null
    fun execute(param: String?) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file!!.absolutePath)
            val statement = connection!!.createStatement()
            statement.execute(param)
            statement.close()
            connection!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun executeUpdate(param: String?) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file!!.absolutePath)
            val statement = connection!!.createStatement()
            statement.executeUpdate(param)
            statement.close()
            connection!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun query(param: String?): ResultSet? {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file!!.absolutePath)
            val statement = connection!!.createStatement()
            val set = statement.executeQuery(param)
            statement.close()
            connection!!.close()
            return set
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            file = File(plugin.dataFolder, "$database.sqlite")
            if (!file!!.parentFile.exists()) {
                file!!.parentFile.mkdir()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}