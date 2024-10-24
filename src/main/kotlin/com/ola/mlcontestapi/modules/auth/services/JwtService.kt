package com.ola.mlcontestapi.modules.auth.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
class JwtService {
    @Value("\${token.secret}")
    private val secret: String? = null

    @Value("\${token.lifetime}")
    private val lifetime: Int? = null

    fun extractUsername(token: String?): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    fun <T> extractClaim(token: String?, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun generateToken(username: String): com.ola.mlcontestapi.modules.auth.entities.JwtTokenDetails {
        val claims = mapOf<String, Any>()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any>, username: String): com.ola.mlcontestapi.modules.auth.entities.JwtTokenDetails {
        val expirationDate = Date(System.currentTimeMillis() + lifetime!!)
        val tokenString = Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expirationDate)
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()

        return com.ola.mlcontestapi.modules.auth.entities.JwtTokenDetails(tokenString, expirationDate)
    }

    private fun getSignKey(): Key {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}