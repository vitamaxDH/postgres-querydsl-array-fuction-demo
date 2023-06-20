package io.vitamax.graphqlfunction.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class StudentRepositoryTest {

    @Autowired
    private lateinit var repo: StudentRepository

    @Test
    @Transactional
    fun `should fetch when any subject name overlaps`() {
        val science = "SCIENCE"
        val math = "MATH"
        val pe = "PE"

        val testSet = listOf(
            listOf(science, math, pe),
            listOf(math, pe),
            listOf(science, pe),
        )
        testSet.forEach { subjectNames ->
            repo.save(Student(
                subjectNames = subjectNames,
            ))
        }
        val studentsTakingPEClass = repo.findSubjectsIntersect(arrayOf(pe))
        studentsTakingPEClass.size shouldBe testSet.size
    }

    @Test
    @Transactional
    fun `should fetch when subjectName contains`() {
        val science = "SCIENCE"
        val math = "MATH"
        val pe = "PE"

        val mathGroupSize = 3
        (1..mathGroupSize).forEach { _ ->
            repo.save(Student(
                subjectNames = listOf(math, science, pe),
            ))
        }

        // another group without math
        repeat(3) {
            repo.save(Student(
                subjectNames = listOf(science, pe),
            ))
        }
        val studentsTakingMathClass = repo.findBySubjectNamesContaining(math)
        studentsTakingMathClass.size shouldBe mathGroupSize
    }
}
