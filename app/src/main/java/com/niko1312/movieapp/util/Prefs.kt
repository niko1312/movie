package com.niko1312.movieapp.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.niko1312.movieapp.MovieApp

class Prefs private constructor() {

    private val prefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance())

    private fun getString(key: String, default: String? = null): String =
        prefs.getString(key, default) ?: KEY_ALARM_WAIT_TO_GO_HOME

    private fun clear() = prefs.edit().clear().apply()

    companion object {

        private const val KEY_ALARM_WAIT_TO_GO_HOME = "b66ffea8276ce576d60df52600822c88"
        const val KEY_MOVIE_KEY = "KEY_MOVIE_KEY"

        private val instance = Prefs()

        fun getKey(): String = instance.getString(KEY_MOVIE_KEY, KEY_ALARM_WAIT_TO_GO_HOME)

        fun clear() {
            instance.clear()
        }
    }
}