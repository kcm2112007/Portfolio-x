package com.portfoliox.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * These models map 1:1 to Supabase Postgres tables (snake_case columns,
 * see @SerialName). Create matching tables + RLS policies in the Supabase
 * dashboard using the SQL in README.md.
 */

@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val bio: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @SerialName("resume_url") val resumeUrl: String? = null,
    val location: String? = null,
    @SerialName("career_objective") val careerObjective: String? = null,
    @SerialName("social_links") val socialLinks: Map<String, String> = emptyMap()
)

enum class SkillCategory {
    PROGRAMMING, ANDROID_DEVELOPMENT, WEB_DEVELOPMENT, UI_UX, TOOLS, SOFT_SKILLS
}

@Serializable
data class Skill(
    val id: String,
    val name: String,
    val icon: String? = null,
    val level: Int, // 0-100
    val category: String, // maps to SkillCategory.name
    @SerialName("years_experience") val yearsExperience: Double = 0.0
)

enum class ProjectStatus { IN_PROGRESS, COMPLETED, ARCHIVED }

@Serializable
data class Project(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String? = null,
    val images: List<String> = emptyList(),
    val technologies: List<String> = emptyList(),
    @SerialName("github_url") val githubUrl: String? = null,
    @SerialName("demo_url") val demoUrl: String? = null,
    val status: String = ProjectStatus.COMPLETED.name,
    val date: String? = null,
    val category: String? = null
)

@Serializable
data class Certificate(
    val id: String,
    val title: String,
    val issuer: String,
    val image: String? = null,
    @SerialName("pdf_url") val pdfUrl: String? = null,
    @SerialName("verification_url") val verificationUrl: String? = null,
    @SerialName("credential_id") val credentialId: String? = null,
    val date: String? = null
)

enum class GalleryType { PHOTO, VIDEO }

@Serializable
data class GalleryItem(
    val id: String,
    val type: String, // GalleryType.name
    val title: String? = null,
    @SerialName("media_url") val mediaUrl: String,
    val album: String? = null,
    val description: String? = null,
    val date: String? = null
)

@Serializable
data class Achievement(
    val id: String,
    val title: String,
    val description: String? = null,
    val icon: String? = null,
    val date: String? = null
)

@Serializable
data class EducationEntry(
    val id: String,
    val institution: String,
    val degree: String,
    val score: String? = null, // marks/CGPA
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null
)

@Serializable
data class BlogPost(
    val id: String,
    val title: String,
    val content: String, // rich text / markdown
    @SerialName("cover_image") val coverImage: String? = null,
    val category: String? = null,
    val date: String? = null,
    val published: Boolean = true
)
