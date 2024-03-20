package com.sd.laborator.interfaces

interface IAuthService {
    //public fun hashID(idEncrypted: String): String
    public fun hashPass(passEncrypted: String): String
    public fun login(loginID: String, password: String): Boolean
    //public fun hashWithAlgorithm(input: String, algorithm: String): String //STIU CA TREBUIE STERS DAR O LAS AICI

}