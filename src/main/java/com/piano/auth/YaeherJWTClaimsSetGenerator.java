package com.piano.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;

import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class YaeherJWTClaimsSetGenerator extends JWTClaimsSetGenerator {
    public YaeherJWTClaimsSetGenerator(TokenConfiguration tokenConfiguration,
                                       @Nullable JwtIdGenerator jwtIdGenerator,
                                       @Nullable ClaimsAudienceProvider claimsAudienceProvider,
                                       @Nullable ApplicationConfiguration applicationConfiguration) {
        super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration);
    }

    @Override
    protected void populateIss(JWTClaimsSet.Builder builder) {
        builder.issuer("daka");
    }

    @Override
    protected void populateAud(JWTClaimsSet.Builder builder) {
        builder.audience("daka");
    }
}
