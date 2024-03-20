package com.sd.laborator.services;

import com.sd.laborator.interfaces.IAuthService
import com.sd.laborator.pojo.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest

//encapsulare + abstractizare
@Service
class AuthService() : IAuthService
{
    /*    private fun privateHashID(idEncrypted: String): String
        {
            return hashWithAlgorithm(idEncrypted, "SHA-256");
        }
        */
    @Autowired
    private lateinit var agendaService: AgendaService

    private fun privateHashPass(passEncrypted: String): String
    {
        return hashWithAlgorithm(passEncrypted, "SHA-256");
    }

    private fun hashWithAlgorithm(input: String, algorithm: String): String
    {
        val digest = MessageDigest.getInstance(algorithm);
        val hashBytes = digest.digest(input.toByteArray());
        return hashBytes.joinToString("") { "%02x".format(it) };
    }

    override fun login(loginID: String, password: String): Boolean
    {
        val utilizatoriTotal: List<Person> = agendaService.getAllPersons()
        for (utilizator in utilizatoriTotal)
        {
            if (utilizator.loginID == loginID && utilizator.password == hashPass(password)) {
                return true 
            }
        }

        return false;
    }

    /*
        override fun hashID(idEncrypted: String): String
        {
            return privateHashID(idEncrypted);
        }
    */

    override fun hashPass(passEncrypted: String): String
    {
        return privateHashPass(passEncrypted);
    }

}