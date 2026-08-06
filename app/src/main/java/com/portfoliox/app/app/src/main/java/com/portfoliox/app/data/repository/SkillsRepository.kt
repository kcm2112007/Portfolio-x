package com.portfoliox.app.data.repository

import com.portfoliox.app.data.model.Skill
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillsRepository @Inject constructor(
    private val postgrest: Postgrest
) {
    suspend fun getSkills(): Result<List<Skill>> = runCatching {
        postgrest.from("skills")
            .select()
            .decodeList<Skill>()
    }
}
