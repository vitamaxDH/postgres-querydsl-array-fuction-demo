package io.vitamax.graphqlfunction.domain

import com.querydsl.core.types.dsl.Expressions
import io.vitamax.graphqlfunction.domain.QStudent.student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*


interface StudentRepository:
    JpaRepository<Student, UUID>,
    StudentCustomRepository

interface StudentCustomRepository {
    fun findSubjectsIntersect(subjectNames: Array<String>): List<Student>
    fun findBySubjectNamesContaining(subjectName: String): List<Student>
}

class StudentRepositoryImpl:
    QuerydslRepositorySupport(Student::class.java),
    StudentCustomRepository {

    override fun findSubjectsIntersect(subjectNames: Array<String>): List<Student> =
        from(student)
            .where(
                Expressions.booleanTemplate(
                    "ARRAYOVERLAP({0}, {1}) = true",
                    student.subjectNames,
                    subjectNames,
                )
            )
            .fetch()

    override fun findBySubjectNamesContaining(subjectName: String): List<Student> =
        from(student)
            .where(
                Expressions.booleanTemplate(
                    "ARRAY_POSITION({0}, {1}) IS NOT NULL",
                    student.subjectNames,
                    subjectName,
                )
            )
            .fetch()
}
