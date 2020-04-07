package br.com.bidei.security.application.token

import br.com.bidei.security.application.utils.SecurityUtils
import br.com.bidei.security.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import org.apache.logging.log4j.kotlin.Logging
import org.apache.logging.log4j.kotlin.logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenFilter(
        private val securityUtils: SecurityUtils
) : OncePerRequestFilter(), Logging {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val idToken = securityUtils.getTokenFromRequest(request)
        var decodedToken: FirebaseToken? = null
        if (idToken != null) {
            try {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
            } catch (e: FirebaseAuthException) {
                logger().error(e)
            }
        }

        if (decodedToken != null) {

            val isCustomerValid = securityUtils.isCustomerIdEqualsJwtId(decodedToken.uid, request)
            val isUnverifiedMethod = securityUtils.isUnverifiedMethod(request)

            if (isCustomerValid || isUnverifiedMethod) {
                val user = User(
                        decodedToken.uid,
                        decodedToken.name,
                        decodedToken.email,
                        decodedToken.isEmailVerified,
                        decodedToken.issuer,
                        decodedToken.picture
                )
                val authentication = UsernamePasswordAuthenticationToken(user, decodedToken, null)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

}