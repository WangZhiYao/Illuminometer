package com.paperloong.illuminometer.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/26
 */
val Context.illuminometerDataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore_pref_illuminometer")