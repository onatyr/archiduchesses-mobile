package fr.onat.turboplant.data.database

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver

actual fun getSQLiteDriver(): SQLiteDriver = AndroidSQLiteDriver()