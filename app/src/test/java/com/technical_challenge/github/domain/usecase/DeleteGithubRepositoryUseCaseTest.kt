package com.technical_challenge.github.domain.usecase

import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.repository.GithubRepository
import com.technical_challenge.github.kotlin_utils.Either
import com.technical_challenge.github.kotlin_utils.buildFailure
import com.technical_challenge.github.kotlin_utils.buildSuccess
import com.technical_challenge.github.kotlin_utils.isSuccess
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DeleteGithubRepositoryUseCaseTest {

    @Mock
    private lateinit var repository: GithubRepository

    private lateinit var useCase: DeleteGithubRepositoryUseCase

    @BeforeEach
    fun setUp() {
        useCase = DeleteGithubRepositoryUseCase(repository = repository)
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    fun `invoke should return correct result based on repository response`(
        repositoryId: Long,
        repoResponse: Either<Unit, DomainError>
    ) = runTest {
        // Given
        whenever(repository.deleteGithubRepositoryById(repositoryId)).thenReturn(repoResponse)

        // When
        val result = useCase(repositoryId)

        // Then
        verify(repository).deleteGithubRepositoryById(repositoryId)
        assertEquals(repoResponse.isSuccess(), result.isSuccess())
        if (!repoResponse.isSuccess()) {
            assertEquals(DOMAIN_ERROR, (result as Either.Failure).error.error)
        }
    }

    companion object {
        private const val DOMAIN_ERROR = "DOMAIN_ERROR"
        private const val NUMBER_OF_REPOSITORY_5 = 5L
        private const val NUMBER_OF_REPOSITORY_10 = 10L

        @JvmStatic
        fun provideParameters() = listOf(
            Arguments.of(NUMBER_OF_REPOSITORY_5, buildSuccess(Unit)),
            Arguments.of(NUMBER_OF_REPOSITORY_10, buildFailure(DomainError(DOMAIN_ERROR)))
        )
    }
}