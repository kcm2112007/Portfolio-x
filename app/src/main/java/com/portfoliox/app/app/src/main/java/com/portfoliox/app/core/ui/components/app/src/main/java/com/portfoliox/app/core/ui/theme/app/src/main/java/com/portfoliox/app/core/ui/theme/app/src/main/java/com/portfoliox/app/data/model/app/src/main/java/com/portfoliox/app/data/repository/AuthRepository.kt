package com.portfoliox.app.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.SessionStatus
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: Auth
) {
    /** Emits true once Supabase has restored a valid session from local storage. */
    val isLoggedIn: Flow<Boolean> = auth.sessionStatus.map { it is SessionStatus.Authenticated }

    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signOut(): Result<Unit> = runCatching { auth.signOut() }

    fun currentUserId(): String? = auth.currentUserOrNull()?.id
}
