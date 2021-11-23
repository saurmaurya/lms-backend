package com.srsdev.tech.lms.scheduled

import com.srsdev.tech.lms.repositories.CheckoutBookRepository
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDate

@Configuration
@EnableScheduling
class AccessRevoke(private val checkoutBookRepository: CheckoutBookRepository) {
    @Scheduled(cron = "0 0 0 * * *")
    fun revokeAccess() {
        val checkoutBookList = checkoutBookRepository.findAllByIsExpiredFalseAndValidTillBefore(LocalDate.now())
        checkoutBookList.map { checkoutBook ->
            {
                checkoutBook.isExpired = true
                checkoutBookRepository.save(checkoutBook)
            }
        }
    }
}