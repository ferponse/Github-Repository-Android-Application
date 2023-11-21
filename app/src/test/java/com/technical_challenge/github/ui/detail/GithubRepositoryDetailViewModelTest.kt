package com.technical_challenge.github.ui.detail

import androidx.lifecycle.MutableLiveData
import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.usecase.UpdateGithubRepositoryUseCase
import com.technical_challenge.github.kotlin_utils.Either
import com.technical_challenge.github.kotlin_utils.buildFailure
import com.technical_challenge.github.kotlin_utils.buildSuccess
import com.technical_challenge.github.kotlin_utils.isSuccess
import com.technical_challenge.github.ui.mapper.toGithubRepositoryDomainModel
import com.technical_challenge.github.ui.model.GithubRepositoryUIModelMother
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever


@ExtendWith(MockitoExtension::class)
@ExperimentalCoroutinesApi
class GithubRepositoryDetailViewModelTest {

    @Mock
    private lateinit var updateGithubRepositoryUseCase: UpdateGithubRepositoryUseCase

    @Mock
    private lateinit var data: GithubRepositoryDetailData

    @Mock
    private lateinit var event: GithubRepositoryDetailEvent

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: GithubRepositoryDetailViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GithubRepositoryDetailViewModel(
            data = data,
            event = event,
            updateGithubRepositoryUseCase = updateGithubRepositoryUseCase,
            ioDispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun loadGithubRepositoryTest() = runTest(testDispatcher) {
        // Given
        val githubRepository = GithubRepositoryUIModelMother.mock()

        // When
        viewModel.loadGithubRepository(githubRepository)

        // Then
        verify(data).updateGithubRepositoryData(githubRepository)
    }

    @ParameterizedTest
    @MethodSource("provideSaveButtonClickArguments")
    fun `onSaveButtonClick emits expected events`(result: Either<Unit, DomainError>) =
        runTest(testDispatcher) {
            // Given
            val githubRepository = GithubRepositoryUIModelMother.mock()
            viewModel.loadGithubRepository(githubRepository)
            whenever(data.name).thenReturn(MutableLiveData(githubRepository.name))
            whenever(updateGithubRepositoryUseCase(anyOrNull())).thenReturn(result)

            // When
            viewModel.onSaveButtonClick()
            testDispatcher.scheduler.apply { advanceTimeBy(4000); runCurrent() }

            // Then
            verify(updateGithubRepositoryUseCase).invoke(githubRepository = githubRepository.toGithubRepositoryDomainModel())
            verify(data).showLoading()
            if (result.isSuccess()) {
                verify(event).sendSuccessWhenUpdatingGithubRepositoryDataEvent()
            } else {
                verify(event).sendErrorWhenUpdatingGithubRepositoryDataEvent()
            }
            verify(data).hideLoading()
        }

    companion object {
        private const val DOMAIN_ERROR = "DOMAIN_ERROR"

        @JvmStatic
        fun provideSaveButtonClickArguments() = listOf(
            Arguments.of(
                buildSuccess(Unit)
            ),
            Arguments.of(
                buildFailure(DOMAIN_ERROR)
            )
        )
    }
}