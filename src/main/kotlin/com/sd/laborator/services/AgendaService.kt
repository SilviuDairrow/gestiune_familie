package com.sd.laborator.services;

import com.sd.laborator.interfaces.IAgendaService
import com.sd.laborator.pojo.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class AgendaService : IAgendaService {
    companion object {
        val initialAgenda = arrayOf(
            Person(1, "rex", "3227fe6bde46249b0aae4b69ef6efd806422a46788e281d050d32d0d9fbde723", "none1", "none2", 23.23, 23.23, 23.23),//pass = rex
            Person(2, "max", "9baf3a40312f39849f46dad1040f2f039f1cffa1238c41e9db675315cfad39b6", "none3", "none4"), //max
            Person(3, "bibu", "276cc54074fa00e2dbb2f41961db0a91de053f61575e203856019bb4b81d639b", "none5", "none6")
        )
    }
    @Autowired
    private lateinit var _encryptionService: EncryptionService;

    private val agenda = ConcurrentHashMap<Int, Person>(
        initialAgenda.associateBy { person: Person -> person.id }
    )

    override fun getPerson(id: Int): Person? {
        return agenda[id]
    }

    override fun createPerson(person: Person) {
        val maxId = agenda.keys.maxOrNull() ?: 1
        val newId = maxId + 1

        val passHash = _encryptionService.hashPass(person.password)

        val personWithEncryptedPassword = person.copy(id = newId, password = passHash)
        agenda[newId] = personWithEncryptedPassword
    }

    override fun deletePerson(id: Int) {
        agenda.remove(id)
    }

    override fun updatePerson(id: Int, person: Person) {
        deletePerson(id)
        createPerson(person)
    }

    override fun searchAgenda(lastNameFilter: String, firstNameFilter: String, loginIDFilter: String): List<Person> {
        return agenda.filter {
            it.value.lastName.lowercase(Locale.getDefault()).contains(lastNameFilter, ignoreCase = true) &&
                    it.value.firstName.lowercase(Locale.getDefault()).contains(firstNameFilter, ignoreCase = true) &&
                    it.value.loginID.contains(loginIDFilter)
        }.map {
            it.value
        }.toList()
    }
    override fun getAllPersons(): List<Person> {
        return agenda.values.toList()
    }
}
