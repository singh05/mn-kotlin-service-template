package com.shopback

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*
import jakarta.inject.Singleton
import java.text.SimpleDateFormat


@OpenAPIDefinition(
    info = Info(
            title = "mn-shopback-app",
            version = "0.0"
    )
)
object Api {}

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.shopback")
		.start()
}


//TODO: move to utils
@Singleton
internal class ObjectMapperBeanEventListener : BeanCreatedEventListener<ObjectMapper> {
    override fun onCreated(event: BeanCreatedEvent<ObjectMapper>): ObjectMapper {
        val mapper: ObjectMapper = event.getBean()
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return mapper
    }
}
