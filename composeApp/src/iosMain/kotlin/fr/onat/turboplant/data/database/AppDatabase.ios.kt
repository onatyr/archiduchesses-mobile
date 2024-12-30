package fr.onat.turboplant.data.database

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver

actual fun getSQLiteDriver(): SQLiteDriver = NativeSQLiteDriver()