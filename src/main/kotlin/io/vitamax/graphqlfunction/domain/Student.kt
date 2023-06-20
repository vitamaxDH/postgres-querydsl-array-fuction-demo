package io.vitamax.graphqlfunction.domain

import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.util.*

@Entity
@Table(name = "student")
class Student (

    @Id
    val id: UUID = UUID.randomUUID(),

    @Type(ListArrayType::class)
    @Column(columnDefinition = "text[]")
    val subjectNames: List<String> = emptyList(),

)
