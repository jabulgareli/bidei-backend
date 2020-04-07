package br.com.bidei.integrations.payments.infrastructure.ports

import br.com.bidei.integrations.payments.infrastructure.dto.*

interface IuguApiPort {
    fun createCustomer(iuguCustomerRequest: IuguCustomerRequest): IuguCustomerResponse
    fun createPaymentToken(iuguPaymentTokenRequest: IuguPaymentTokenRequest): IuguPaymentTokenResponse
    fun createPaymentMethods(iuguCustomerId: String, iuguPaymentMethodsRequest: IuguPaymentMethodsRequest): Boolean
    fun listPaymentMethods(iuguCustomerId: String): List<IuguPaymentMethodsResponse>
    fun removePaymentMethods(iuguCustomerId: String, paymentMethodsId: String): Boolean
    fun charge(iuguChargeRequest: IuguChargeRequest): IuguChargeResponse
}