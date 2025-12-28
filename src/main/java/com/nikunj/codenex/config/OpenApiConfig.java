package com.nikunj.codenex.config;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Codenex API")
                        .description("Multi agent web builder with Spring Boot and Spring AI")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact()
                                .name("Nikunj")
                                .email("contact@codenex.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .components(new Components()
                        .addSchemas("ApiError", createApiErrorSchema())
                        .addSchemas("ValidationApiError", createValidationApiErrorSchema())
                        .addResponses("BadRequest", createBadRequestResponse())
                        .addResponses("ValidationError", createValidationErrorResponse())
                        .addResponses("NotFound", createNotFoundResponse())
                        .addResponses("Forbidden", createForbiddenResponse())
                        .addResponses("Conflict", createConflictResponse())
                        .addResponses("InternalServerError", createInternalServerErrorResponse()));
    }

    @SuppressWarnings("rawtypes")
    private Schema createApiErrorSchema() {
        return new ObjectSchema()
                .description("Standard error response")
                .addProperty("status", new IntegerSchema()
                        .description("HTTP status code")
                        .example(400))
                .addProperty("error", new StringSchema()
                        .description("Error type/code")
                        .example("Bad Request"))
                .addProperty("message", new StringSchema()
                        .description("Human-readable error message")
                        .example("Invalid request parameters"))
                .addProperty("path", new StringSchema()
                        .description("Request path that caused the error")
                        .example("/api/projects/1"))
                .addProperty("timestamp", new StringSchema()
                        .format("date-time")
                        .description("Timestamp when the error occurred")
                        .example("2024-01-15T10:30:00Z"))
                .addRequiredItem("status")
                .addRequiredItem("error")
                .addRequiredItem("message")
                .addRequiredItem("path")
                .addRequiredItem("timestamp");
    }

    @SuppressWarnings("rawtypes")
    private Schema createValidationApiErrorSchema() {
        return new ObjectSchema()
                .description("Validation error response with field-specific errors")
                .addProperty("status", new IntegerSchema()
                        .description("HTTP status code")
                        .example(400))
                .addProperty("error", new StringSchema()
                        .description("Error type/code")
                        .example("Bad Request"))
                .addProperty("message", new StringSchema()
                        .description("Human-readable error message")
                        .example("Validation failed"))
                .addProperty("path", new StringSchema()
                        .description("Request path that caused the error")
                        .example("/api/auth/signup"))
                .addProperty("timestamp", new StringSchema()
                        .format("date-time")
                        .description("Timestamp when the error occurred")
                        .example("2024-01-15T10:30:00Z"))
                .addProperty("validationErrors", new MapSchema()
                        .description("Map of field names to list of validation error messages")
                        .additionalProperties(new ArraySchema().items(new StringSchema()))
                        .example(Map.of(
                                "email", List.of("must be a valid email address"),
                                "password", List.of("size must be between 4 and 50"))))
                .addRequiredItem("status")
                .addRequiredItem("error")
                .addRequiredItem("message")
                .addRequiredItem("path")
                .addRequiredItem("timestamp");
    }

    private ApiResponse createBadRequestResponse() {
        return new ApiResponse()
                .description("Bad Request - Invalid input or malformed request")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }

    private ApiResponse createValidationErrorResponse() {
        return new ApiResponse()
                .description("Validation Error - Request body validation failed")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ValidationApiError"))));
    }

    private ApiResponse createNotFoundResponse() {
        return new ApiResponse()
                .description("Not Found - Resource not found")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }

    private ApiResponse createForbiddenResponse() {
        return new ApiResponse()
                .description("Forbidden - You don't have permission to access this resource")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }

    private ApiResponse createConflictResponse() {
        return new ApiResponse()
                .description("Conflict - Resource conflict (e.g., duplicate entry)")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }

    private ApiResponse createInternalServerErrorResponse() {
        return new ApiResponse()
                .description("Internal Server Error - Unexpected error occurred")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }
}
