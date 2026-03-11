package com.praisetechzw.phoneguard.core.di

import android.content.Context
import androidx.room.Room
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.praisetechzw.phoneguard.core.database.PhoneGuardDatabase
import com.praisetechzw.phoneguard.core.network.PhoneGuardApi
import com.praisetechzw.phoneguard.core.data.repository.DeviceRepository
import com.praisetechzw.phoneguard.core.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PhoneGuardDatabase {
        return Room.databaseBuilder(
            context,
            PhoneGuardDatabase::class.java,
            "phone_guard.db"
        ).build()
    }

    @Provides
    fun providePhoneReportDao(db: PhoneGuardDatabase) = db.phoneReportDao()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }

    @Provides
    @Singleton
    fun provideApi(): PhoneGuardApi {
        return Retrofit.Builder()
            .baseUrl("https://api.praisetechzw.com/phoneguard/") // Placeholder base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhoneGuardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>) = UserRepository(dataStore)

    @Provides
    @Singleton
    fun provideDeviceRepository(api: PhoneGuardApi, dao: com.praisetechzw.phoneguard.core.database.PhoneReportDao) = DeviceRepository(api, dao)
}
