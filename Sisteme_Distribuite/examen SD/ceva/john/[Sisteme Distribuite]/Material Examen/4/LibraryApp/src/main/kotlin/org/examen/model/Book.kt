package org.examen.model

class Book(private var data : Content) {
    var name: String?
        get() {
            return data.name
        }
        set(value) {
            data.name = value
        }
    var author: String?
        get() {
            return data.author
        }
        set(value) {
            data.author = value
        }
    var publisher: String?
        get() {
            return data.publisher
        }
        set(value) {
            data.publisher = value
        }
    var content: String?
        get() {
            return data.text
        }
        set(value) {
            data.text = value
        }
    fun hasAuthor(author: String): Boolean {
        return data.author.equals(author)
    }
    fun hasTitle(title: String): Boolean {
        return data.name.equals(title)
    }
    fun publishedBy(publisher: String): Boolean {
        return data.publisher.equals(publisher)
    }

    override fun toString(): String {
        return "Cartea ${this.name}, scrisa de ${this.author} si publicata de ${this.publisher}\n\t-${this.content}\n\n"
    }
}