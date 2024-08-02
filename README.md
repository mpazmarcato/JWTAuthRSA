# Geração de Chaves e Criação de Tokens
No código cada usuário na rede recebe um par de chaves RSA exclusivo e um token JWT assinado com a chave privada correspondente.

## Características
- Armazenamento das Chaves Públicas.
- Geração de Chaves RSA e Criação de Tokens.
- Verificação de Tokens JWT.

## Sobre os tokens
A assinatura é criada usando o cabeçalho e o corpo codificados, e a chave privada RSA. Ela garante que o token não foi alterado e que foi realmente assinado pela entidade que possui a chave privada correspondente à chave pública usada para verificar o token. Para criar a assinatura:

- Codifique o cabeçalho e o corpo em Base64Url.
- Combine essas duas partes com um ponto (.) entre elas.
- Assine a combinação usando a chave privada e o algoritmo especificado (neste caso, RS256).
Quando o token é verificado, a assinatura é comparada com a assinatura gerada usando a chave pública correspondente. Se as assinaturas coincidirem, o token é considerado válido.
