package br.com.bidei.security.domain

data class User(
        private var uid: String,
        val name: String?,
        val email: String,
        val isEmailVerified: Boolean = false,
        val issuer: String,
        val picture: String?
)