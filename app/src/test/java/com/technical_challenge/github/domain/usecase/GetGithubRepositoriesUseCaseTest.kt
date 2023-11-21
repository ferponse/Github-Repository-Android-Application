package com.technical_challenge.github.domain.usecase

import androidx.paging.PagingData
import com.technical_challenge.github.domain.model.RepositoryDomainModelMother
import com.technical_challenge.github.domain.repository.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GetGithubRepositoriesUseCaseTest {

    @Mock
    private lateinit var repository: GithubRepository

    @Test
    fun `invoke should return github repositories from repository`() = runTest {
        // Given
        val mockGithubRepositories = PagingData.from(listOf(RepositoryDomainModelMother.mock()))
        whenever(repository.getGithubRepositories()).thenReturn(flowOf(mockGithubRepositories))
        val useCase = GetGithubRepositoriesUseCase(repository)

        // When
        val resultFlow = useCase()
        val resultPagingData = resultFlow.toList().first()

        // Then
        assertEquals(mockGithubRepositories, resultPagingData)
    }
}