package com.sd.laborator.interfaces

interface IEncryptionService {
    public fun encrypt(passNoCrypted : String): String;
    public fun decrypt(passCrypted : String): String;
    public fun hashPass(passEncrypted: String): String;
}