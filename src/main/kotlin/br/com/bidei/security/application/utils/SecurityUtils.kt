package br.com.bidei.security.application.utils

import br.com.bidei.customers.domain.repository.CustomersRepository
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class SecurityUtils(
        private val customersRepository: CustomersRepository
): Logging {

    fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            bearerToken.substring(7, bearerToken.length)
        else null
    }

    fun isCustomerIdEqualsJwtId(tokenUID: String, request: HttpServletRequest): Boolean {
        val customer = customersRepository.findByReferenceId(tokenUID)
        val customerIds = getCustomerIdFromRequest(request)
        return customer.isPresent && customerIds.isNotEmpty() && customerIds.contains(customer.get().id)
    }

    private fun getCustomerIdFromRequest(request: HttpServletRequest): ArrayList<UUID> {
        val customerIds = arrayListOf<UUID>()

        try {

            //
            // Get ID from path
            customerIds.addAll(getUidFromPath(request.servletPath))

            //
            // Get ID from parameter
            var customerIdParameter = request.getParameter("customerId")
            if (customerIdParameter != null && customerIdParameter.isNotEmpty()) customerIds.add(UUID.fromString(customerIdParameter))

            customerIdParameter = request.getParameter("customer_id")
            if (customerIdParameter != null && customerIdParameter.isNotEmpty()) customerIds.add(UUID.fromString(customerIdParameter))

            //
            // Get ID from header
            var customerIdHeader = request.getHeader("customerId")
            if (customerIdHeader != null && customerIdHeader.isNotEmpty()) customerIds.add(UUID.fromString(customerIdHeader))

            customerIdHeader = request.getHeader("customer_id")
            if (customerIdHeader != null && customerIdHeader.isNotEmpty()) customerIds.add(UUID.fromString(customerIdHeader))

        } catch (e: Exception) {
            logger.error(e)
        } finally {
            return customerIds
        }
    }

    private fun getUidFromPath(servletPath: String): ArrayList<UUID> {
        val ids = arrayListOf<UUID>()
        servletPath
                .split("/")
                .forEach {
                    try {
                        ids.add(UUID.fromString(it))
                    } catch (e: Exception) {}
                }
        return ids
    }

    fun isUnverifiedMethod(request: HttpServletRequest): Boolean {
        return request.method == "POST" && request.servletPath == "/api/v1/customers"
    }

//    fun getPrincipal(): User? {
//        val principal = SecurityContextHolder.getContext().authentication.principal
//        if (principal is User)
//            return principal
//        return null
//    }

}