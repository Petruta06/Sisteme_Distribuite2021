package org.examen.interfaces

import org.examen.pojo.Person

interface AgendaService{
    fun getPerson(id : Int) : Person?
    fun createPerson(person : Person)
    fun deletePerson(id : Int)
    fun updatePerson(id : Int, person : Person)
    fun searchAgenda(lastNameFilter : String, firstNameFilter : String, telephoneNumberFilter : String) : List<Person>
}