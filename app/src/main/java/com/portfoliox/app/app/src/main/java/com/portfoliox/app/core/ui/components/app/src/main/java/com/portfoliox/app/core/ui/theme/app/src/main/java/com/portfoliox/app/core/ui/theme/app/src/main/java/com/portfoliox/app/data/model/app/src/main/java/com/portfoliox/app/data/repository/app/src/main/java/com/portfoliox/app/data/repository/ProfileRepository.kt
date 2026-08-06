package com.portfoliox.app.data.repository

import com.portfoliox.app.data.model.UserProfile
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val postgrest: Postgrest
) {
    /** There's a single portfolio owner, so we fetch the one row from `profiles`. */
    suspend fun getProfile(): Result<UserProfile?> = runCatching {
        postgrest.from("profiles")
            .select()
            .decodeList<UserProfile>()
            .firstOrNull()
    }

    suspend fun updateProfile(profile: UserProfile): Result<Unit> = runCatching {
        postgrest.from("profiles").update(profile) {
            filter { eq("id", profile.id) }
        }
    }
}
