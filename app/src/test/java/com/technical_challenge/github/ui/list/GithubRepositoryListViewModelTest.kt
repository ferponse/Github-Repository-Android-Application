package com.technical_challenge.github.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.usecase.DeleteGithubRepositoryUseCase
import com.technical_challenge.github.domain.usecase.GetGithubRepositoriesUseCase
import com.technical_challenge.github.kotlin_utils.Either
import com.technical_challenge.github.kotlin_utils.buildFailure
import com.technical_challenge.github.kotlin_utils.buildSuccess
import com.technical_challenge.github.kotlin_utils.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class GithubRepositoryListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase

    @Mock
    private lateinit var deleteGithubRepositoryUseCase: DeleteGithubRepositoryUseCase

    @Mock
    private lateinit var data: RepositoryListData

    @Mock
    private lateinit var event: RepositoryListEvent

    private lateinit var viewModel: GithubRepositoryListViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GithubRepositoryListViewModel(
            data = data,
            event = event,
            getGithubRepositoriesUseCase = getGithubRepositoriesUseCase,
            deleteGithubRepositoryUseCase = deleteGithubRepositoryUseCase,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `ViewModel initialization sets github repositories paging data`() = runTest(testDispatcher) {
        // Given

        // When

        // Then
        verify(getGithubRepositoriesUseCase).invoke()
        verify(data).updateGithubRepositoryPagingData(any())
    }

    @ParameterizedTest
    @MethodSource("provideGithubRepositoryDeletionScenarios")
    fun `deleteGithubRepository emits appropriate event based on result`(
        githubRepositoryId: Long,
        useCaseResult: Either<Unit, DomainError>
    ) = runTest(testDispatcher) {
        // Given
        whenever(deleteGithubRepositoryUseCase(any())).thenReturn(useCaseResult)

        // When
        viewModel.deleteGithubRepository(githubRepositoryId)
        testDispatcher.scheduler.apply { advanceTimeBy(4000); runCurrent() }

        // Then
        verify(deleteGithubRepositoryUseCase).invoke(githubRepositoryId)
        verify(data).showLoading()
        if (useCaseResult.isSuccess()) {
            verify(event).sendSuccessWhenDeletingGithubRepositoryEvent()
        } else {
            verify(event).sendErrorWhenDeletingGithubRepositoryEvent()
        }
        verify(data).hideLoading()
    }

    companion object {
        private const val DOMAIN_ERROR = "DOMAIN_ERROR"
        private const val NUMBER_OF_REPOSITORY_5 = 5
        private const val NUMBER_OF_REPOSITORY_10 = 10

        @JvmStatic
        fun provideGithubRepositoryDeletionScenarios() = listOf(
            Arguments.of(
                NUMBER_OF_REPOSITORY_5,
                buildSuccess(Unit)
            ),
            Arguments.of(
                NUMBER_OF_REPOSITORY_10,
                buildFailure(DomainError(DOMAIN_ERROR))
            )
        )
    }
}
