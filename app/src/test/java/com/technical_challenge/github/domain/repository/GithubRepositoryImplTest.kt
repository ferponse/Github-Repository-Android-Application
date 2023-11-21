import androidx.paging.Pager
import androidx.paging.PagingSource
import com.technical_challenge.github.data.local.GithubRepositoryDao
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.domain.model.RepositoryDomainModelMother
import com.technical_challenge.github.repository.GithubRepositoryImpl
import com.technical_challenge.github.kotlin_utils.isSuccess
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GithubRepositoryImplTest {

    @Mock
    private lateinit var githubDatabase: GithubDatabase

    @Mock
    private lateinit var pager: Pager<Int, GithubRepositoryEntity>

    @Mock
    private lateinit var githubRepositoryDao: GithubRepositoryDao

    @Mock
    private lateinit var pagingSource: PagingSource<Int, GithubRepositoryEntity>

    private lateinit var repository: GithubRepositoryImpl

    @BeforeEach
    fun setUp() {
        whenever(githubDatabase.githubRepositoryDao).thenReturn(githubRepositoryDao)
        repository = GithubRepositoryImpl(
            githubDatabase = githubDatabase,
            pager = pager
        )
    }

    @Test
    fun `deleteGithubRepositoryById should return success when no exception`() = runTest {
        // Given
        whenever(githubRepositoryDao.deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_5)).thenReturn(Unit)
        whenever(githubRepositoryDao.pagingSource()).thenReturn(pagingSource)

        // When
        val result = repository.deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_5)

        // Then
        verify(githubRepositoryDao).deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_5)
        verify(githubRepositoryDao.pagingSource()).invalidate()
        assertTrue(result.isSuccess())
    }

    @Test
    fun `deleteGithubRepositoryById should return failure when exception occurs`() = runTest {
        // Given
        whenever(githubRepositoryDao.deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_10)).thenThrow(EXCEPTION)

        // When
        val result = repository.deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_10)

        // Then
        verify(githubRepositoryDao).deleteGithubRepositoryById(NUMBER_OF_REPOSITORY_10)
        assertFalse(result.isSuccess())
    }

    @Test
    fun `updateGithubRepository should return success when no exception`() = runTest {
        // Given
        val githubRepositoryModel = RepositoryDomainModelMother.mock()
        whenever(githubDatabase.githubRepositoryDao).thenReturn(githubRepositoryDao)
        whenever(githubRepositoryDao.updateGithubRepository(githubRepositoryModel)).thenReturn(Unit)
        whenever(githubRepositoryDao.pagingSource()).thenReturn(pagingSource)

        // When
        val result = repository.updateGithubRepository(githubRepositoryModel)

        // Then
        verify(githubRepositoryDao).updateGithubRepository(githubRepositoryModel)
        verify(githubRepositoryDao.pagingSource()).invalidate()
        assertTrue(result.isSuccess())
    }

    @Test
    fun `updateGithubRepository should return success when exception occurs`() = runTest {
        // Given
        val githubRepositoryModel = RepositoryDomainModelMother.mock()
        whenever(githubDatabase.githubRepositoryDao).thenReturn(githubRepositoryDao)
        whenever(githubRepositoryDao.updateGithubRepository(githubRepositoryModel)).thenThrow(EXCEPTION)

        // When
        val result = repository.updateGithubRepository(githubRepositoryModel)

        // Then
        verify(githubRepositoryDao).updateGithubRepository(githubRepositoryModel)
        assertFalse(result.isSuccess())
    }

    companion object {
        private const val DATABASE_ERROR = "DATABASE_ERROR"
        private const val NUMBER_OF_REPOSITORY_5 = 5L
        private const val NUMBER_OF_REPOSITORY_10 = 10L
        private val EXCEPTION = RuntimeException(DATABASE_ERROR)
    }
}