package br.com.skip.the.dishes.domain.product.commands

import br.com.skip.the.dishes.domain.product.Detail
import br.com.skip.the.dishes.domain.product.Product
import br.com.zup.eventsourcing.core.AggregateId
import br.com.zup.eventsourcing.core.Repository
import br.com.zup.eventsourcing.eventstore.EventStoreRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ProductCommandHandlerTest {

    private val product = Product("Rice", "Delicious rice", "1", 10.12)

    private val eventRepository = mock<EventStoreRepository<Product>> {
        on { find(any()) } doReturn product
    }

    private val productCommandHandle = ProductCommandHandler(eventRepository)

    private val productId = AggregateId("prod-1")

    @Test
    fun `Handle create product command`() {
        val createProductCommand = CreateProductCommand("Rice", "Delicious rice", "1", 10.12)

        productCommandHandle.handle(createProductCommand)

        verify(eventRepository).save(any(), eq(Repository.OptimisticLock.ENABLED))
    }

    @Test
    fun `Update details for a product`() {
        val updateDetailCommand = UpdateDetailCommand(productId, Detail("Bean", "Delicious bean"))

        productCommandHandle.handle(updateDetailCommand)

        verify(eventRepository).find(eq(productId))
        verify(eventRepository).save(any(), eq(Repository.OptimisticLock.ENABLED))
    }

    @Test
    fun `Update price for a product`() {
        val updatePriceCommand = UpdatePriceCommand(productId, 20.10)

        productCommandHandle.handle(updatePriceCommand)

        verify(eventRepository).find(eq(productId))
        verify(eventRepository).save(any(), eq(Repository.OptimisticLock.ENABLED))
    }

}