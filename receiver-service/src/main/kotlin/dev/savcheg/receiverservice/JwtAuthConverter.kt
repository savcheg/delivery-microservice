package dev.savcheg.receiverservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class JwtAuthConverter : Converter<Jwt, AbstractAuthenticationToken> {
    private final val jwtGrantedAuthConverter = JwtGrantedAuthoritiesConverter()
    @Value("\${jwt.auth.converter.principal-attribute}")
    private lateinit var principalAttribute: String
    @Value("\${jwt.auth.converter.resource-id}")
    private lateinit var resourceId : String
    override fun convert(source: Jwt): AbstractAuthenticationToken? {
        val authorities = Stream.concat(
            jwtGrantedAuthConverter.convert(source)!!.stream(),
            extractRoles(source).stream()
        ).collect(Collectors.toSet())
        return JwtAuthenticationToken(
            source,
            authorities,
            getPrincipleClaimName(source)
        )
    }

    private fun getPrincipleClaimName(source: Jwt): String? {
        var claimName = JwtClaimNames.SUB
        if (principalAttribute != null)
            claimName = principalAttribute
        return claimName
    }

    private fun extractRoles(source: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = source.getClaim<MutableMap<String, Any>>("resource_access") ?: return emptySet()
        val resource = (resourceAccess["receive-rest-api"] ?: return emptySet()) as MutableMap<String, Any>
        val resourceRoles = (resource["roles"] ?: return emptySet()) as Collection<String>
        return resourceRoles.stream()
            .map { role -> SimpleGrantedAuthority("ROLE_$role") }
            .collect(Collectors.toSet())
    }
}