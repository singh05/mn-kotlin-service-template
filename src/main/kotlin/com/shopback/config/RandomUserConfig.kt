package com.shopback.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires

@ConfigurationProperties(RandomUserConfig.PREFIX)
@Requires(property = RandomUserConfig.PREFIX)
class RandomUserConfig {
    var baseUrl: String? = null

    companion object {
        const val PREFIX = "randomUsers"
    }
}
