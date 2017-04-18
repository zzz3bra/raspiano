package by.bru.raspiano.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(by.bru.raspiano.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Climate.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Climate.class.getName() + ".dataHistories", jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Climate.class.getName() + ".actionHistories", jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.I2cDevice.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.I2cSensor.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.I2cController.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.ControllerActon.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Raspberry.class.getName(), jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Raspberry.class.getName() + ".devices", jcacheConfiguration);
            cm.createCache(by.bru.raspiano.domain.Measurement.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
