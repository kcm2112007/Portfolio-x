package com.portfoliox.app.data.repository

import com.portfoliox.app.data.model.EducationEntry
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EducationRepository @Inject constructor(
    private val postgrest: Postgrest
) {
    suspend fun getEducation(): Result<List<EducationEntry>> = runCatching {
        postgrest.from("education_entries")
            .select()
            .decodeList<EducationEntry>()
    }
}
