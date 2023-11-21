package com.technical_challenge.github.domain.usecase

import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.domain.model.RepositoryDomainModelMother
import com.technical_challenge.github.domain.repository.GithubRepository
import com.technical_challenge.github.kotlin_utils.Either
import com.technical_challenge.github.kotlin_utils.buildFailure
import com.technical_challenge.github.kotlin_utils.buildSuccess
import com.technical_challenge.github.kotlin_utils.isSuccess
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class UpdateGithubRepositoryUseCaseTest {

    @Mock
    private lateinit var repository: GithubRepository

    private val useCase by lazy { UpdateGithubRepositoryUseCase(repository) }

    @ParameterizedTest
    @MethodSource("provideGithubRepositories")
    fun `invoke should return correct result based on repository response`(
        githubRepositoryDomainModel: GithubRepositoryDomainModel,
        repoResponse: Either<Unit, DomainError>
    ) = runTest {
        // Given
        whenever(repository.updateGithubRepository(githubRepositoryDomainModel)).thenReturn(repoResponse)

        // When
        val result = useCase(githubRepositoryDomainModel)

        // Then
        assertEquals(repoResponse.isSuccess(), result.isSuccess())
    }

    companion object {
        private const val DOMAIN_ERROR = "DOMAIN_ERROR"
        private const val NUMBER_OF_REPOSITORY_5 = 5L
        private const val NUMBER_OF_REPOSITORY_10 = 10L
        @JvmStatic
        fun provideGithubRepositories() = listOf(
            Arguments.of(
                RepositoryDomainModelMother.mock(
                    id = NUMBER_OF_REPOSITORY_5
                ),
                buildSuccess(Unit)
            ),
            Arguments.of(
                RepositoryDomainModelMother.mock(
                    id = NUMBER_OF_REPOSITORY_10
                ),
                buildFailure(DomainError(DOMAIN_ERROR))
            )
        )
    }
}