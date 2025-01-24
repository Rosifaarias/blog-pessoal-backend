package com.generation.blogpessoal.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// A ANOTAÇÃO @COMPONENT INDICA QUE ESTA CLASSE É UM BEAN GERIDO PELO SPRING, OU SEJA, SERÁ GERENCIADA COMO UM OBJETO ÚNICO NA APLICAÇÃO.
@Component
public class JwtService {

    // A SENHA USADA PARA ASSINAR O TOKEN (DEVE SER MANTIDA SECRETA E SEGURA).
    public static final String SECRET = "H5SNSzDFk2Q76DcjQxLWbbnHIkWyonbsDVhbdD4w2bl8e43ThhXIiJIBHBcRqSU/\r\n";

    // MÉTODO QUE RETORNA A CHAVE PARA ASSINATURA DO TOKEN A PARTIR DA STRING SECRET.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); // DECODE A CHAVE SECRET DE BASE64.
        return Keys.hmacShaKeyFor(keyBytes); // RETORNA A CHAVE EM UM FORMATO UTILIZÁVEL PELO JWT.
    }

    // MÉTODO PRIVADO QUE EXTRAI TODAS AS "CLAIMS" (REIVINDICAÇÕES) DO TOKEN.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // MÉTODO GENÉRICO QUE EXTRAI UMA CLAIM ESPECÍFICA DO TOKEN, BASEADO NA FUNÇÃO PASSADA COMO PARÂMETRO.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // EXTRAI TODAS AS CLAIMS DO TOKEN.
        return claimsResolver.apply(claims); // APLICA A FUNÇÃO PARA EXTRAIR A CLAIM DESEJADA.
    }

    // MÉTODO QUE EXTRAI O NOME DE USUÁRIO (SUBJECT) DO TOKEN.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // EXTRAI O NOME DE USUÁRIO DO TOKEN.
    }

    // MÉTODO QUE EXTRAI A DATA DE EXPIRAÇÃO DO TOKEN.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // EXTRAI A DATA DE EXPIRAÇÃO DO TOKEN.
    }

    // MÉTODO PRIVADO QUE VERIFICA SE O TOKEN ESTÁ EXPIRADO.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // VERIFICA SE A DATA DE EXPIRAÇÃO É ANTERIOR A DATA ATUAL.
    }

    // MÉTODO QUE VALIDA SE O TOKEN É VÁLIDO COMPARANDO O NOME DE USUÁRIO E A EXPIRAÇÃO.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // EXTRAI O NOME DE USUÁRIO DO TOKEN.
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // COMPARA O NOME DE USUÁRIO E CHECA SE O TOKEN NÃO EXPIROU.
    }

    // MÉTODO PRIVADO QUE CRIA O TOKEN JWT COM BASE NAS CLAIMS E NOME DE USUÁRIO.
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims) // DEFINE AS CLAIMS NO TOKEN.
                .setSubject(userName) // DEFINE O NOME DE USUÁRIO (SUBJECT) NO TOKEN.
                .setIssuedAt(new Date(System.currentTimeMillis())) // DEFINE A DATA DE EMISSÃO DO TOKEN.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // DEFINE A DATA DE EXPIRAÇÃO DO TOKEN (1 HORA).
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // ASSINA O TOKEN COM A CHAVE E ALGORITMO HS256.
                .compact(); // RETORNA O TOKEN COMPACTADO.
    }

    // MÉTODO QUE GERA UM NOVO TOKEN PARA UM USUÁRIO.
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>(); // CRIA UM MAPA PARA AS CLAIMS (AQUI, VAZIO).
        return createToken(claims, userName); // GERA O TOKEN COM O NOME DE USUÁRIO.
    }
}