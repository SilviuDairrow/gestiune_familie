package com.sd.laborator.services;

import com.sd.laborator.interfaces.IEncryptionService
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

@Service
class EncryptionService : IEncryptionService
{
    companion object {
        private val secretKey: ByteArray = generateAESKey()

        private const val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

        private fun generateAESKey(): ByteArray
        {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(128, SecureRandom())
            return keyGenerator.generateKey().encoded
        }
    }

    override fun encrypt(passNoCrypted: String): String
    {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(secretKey, "AES"), IvParameterSpec(Base64.getDecoder().decode(iv)))
        val encrypted = cipher.doFinal(passNoCrypted.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    override fun decrypt(passCrypted: String): String
    {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(secretKey, "AES"), IvParameterSpec(Base64.getDecoder().decode(iv)))
        val decodedValue: ByteArray = Base64.getDecoder().decode(passCrypted)
        val decryptedValue: ByteArray = cipher.doFinal(decodedValue)
        return String(decryptedValue)
    }

    override fun hashPass(passEncrypted: String): String
    {
        return privateHashPass(passEncrypted);
    }

    private fun hashWithAlgorithm(input: String, algorithm: String): String
    {
        val digest = MessageDigest.getInstance(algorithm);
        val hashBytes = digest.digest(input.toByteArray());
        return hashBytes.joinToString("") { "%02x".format(it) };
    }

    private fun privateHashPass(passEncrypted: String): String
    {
        return hashWithAlgorithm(passEncrypted, "SHA-256");
    }
}