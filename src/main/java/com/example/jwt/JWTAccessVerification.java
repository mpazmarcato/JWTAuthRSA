package com.example.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe JWTAccessVerification demonstra a geração de pares de chaves RSA
 * para cada usuário, criação de tokens JWT assinados com chaves privadas
 * e verificação de tokens usando chaves públicas.
 */
public class JWTAccessVerification {

    // Armazenar chaves públicas para cada usuário (em um ambiente real, isso seria salvo em um banco de dados seguro)
    private static Map<String, PublicKey> userPublicKeys = new HashMap<>();

    /**
     * O método principal gera um par de chaves RSA para cada usuário,
     * cria um token JWT assinado com a chave privada do usuário,
     * e verifica o token usando a chave pública correspondente.
     *
     * @param args argumentos da linha de comando (não utilizado neste exemplo)
     */
    public static void main(String[] args) {
        try {
            String[] users = {"user1", "user2", "user3"};

            for (String user : users) {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                keyPairGen.initialize(2048);
                KeyPair pair = keyPairGen.generateKeyPair();

                PrivateKey privateKey = pair.getPrivate();
                PublicKey publicKey = pair.getPublic();

                userPublicKeys.put(user, publicKey);

                // Criação do token JWT assinado com a chave privada
                String jws = Jwts.builder()
                        .setSubject(user)
                        .claim("role", "admin")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validade
                        .signWith(privateKey, SignatureAlgorithm.RS256)
                        .compact();

                System.out.println("Token JWT para " + user + ": " + jws);

                // Verificação do token JWT com a chave pública
                try {
                    Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jws);
                    System.out.println("O token para " + user + " é válido.");
                } catch (Exception e) {
                    System.out.println("O token para " + user + " não é válido.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
