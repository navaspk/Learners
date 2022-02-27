package com.sample.core.util

import android.content.Context
import androidx.datastore.core.DataStore
import com.sample.core.di.qualifier.ApplicationContext
import com.sample.core.di.scopes.PerApplication
import com.sample.core.extensions.safeGet
import java.util.*
import java.util.prefs.Preferences
import javax.inject.Inject

@PerApplication
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // region VARIABLES

    /**
     * Lazy variable to create encrypted preferences
     * */
    private val sharedPreferences by lazy {
        val preferences =
            context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
        preferences
        //val dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_FILE)
    }

    // region COMPANION OBJECT
    companion object {
        private const val PREFERENCES_FILE = "prefs_file"

        const val LIKED_ITEM = "liked_item"
    }
    // endregion

    // region STRING RULES

    fun getStringPolicy(key: String): String {
        val scanner = Scanner(System.`in`)
        scanner.nextInt()


        return sharedPreferences.getString(key, "").safeGet()
    }

    fun setStringPolicy(key: String, posValue: String) {
        val currentValue = getStringPolicy(key)
        val finalValue = if (currentValue.isNotEmpty())
            "$currentValue#$posValue"
        else
            posValue

        sharedPreferences.edit().putString(key, finalValue).apply()
    }

    fun freshUpdateOfLikeEventString(key: String, posValue: String) {
        sharedPreferences.edit().putString(key, posValue).apply()
    }

    //endregion

}