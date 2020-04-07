package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeRequest
import br.com.bidei.integrations.payments.infrastructure.dto.IuguCustomerRequest
import br.com.bidei.integrations.payments.infrastructure.dto.IuguPaymentMethodsRequest
import br.com.bidei.integrations.payments.infrastructure.dto.IuguPaymentTokenRequest
import br.com.bidei.integrations.payments.infrastructure.ports.IuguApiPort
import org.springframework.stereotype.Service

@Service
class IntegrationsPaymentsAclAdapter(
        private val iuguApi: IuguApiPort
) : IntegrationsPaymentsAclPort {

    override fun createCustomer(iuguCustomerRequest: IuguCustomerRequest) = iuguApi.createCustomer(iuguCustomerRequest)

    override fun createPaymentToken(iuguPaymentTokenRequest: IuguPaymentTokenRequest) = iuguApi.createPaymentToken(iuguPaymentTokenRequest)

    override fun createPaymentMethods(iuguCustomerId: String, iuguPaymentMethodsRequest: IuguPaymentMethodsRequest) =
            iuguApi.createPaymentMethods(iuguCustomerId, iuguPaymentMethodsRequest)

    override fun listPaymentMethods(iuguCustomerId: String) = iuguApi.listPaymentMethods(iuguCustomerId)

    override fun removePaymentMethods(iuguCustomerId: String, paymentMethodsId: String) =
            iuguApi.removePaymentMethods(iuguCustomerId, paymentMethodsId)

    override fun charge(iuguChargeRequest: IuguChargeRequest) = iuguApi.charge(iuguChargeRequest)
}