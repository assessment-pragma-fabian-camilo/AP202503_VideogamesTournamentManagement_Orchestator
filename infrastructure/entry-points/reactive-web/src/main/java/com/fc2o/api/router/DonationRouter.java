package com.fc2o.api.router;

import com.fc2o.api.handler.DonationHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DonationRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/donations", method = RequestMethod.POST, beanClass = DonationHandler.class, beanMethod = "createDonation", operation = @Operation(operationId = "createDonation", summary = "Create donation", tags = {"Donations"})),
            @RouterOperation(path = "/api/v1/donations/{id}", method = RequestMethod.GET, beanClass = DonationHandler.class, beanMethod = "getDonation", operation = @Operation(operationId = "getDonation", summary = "Get donation by id", tags = {"Donations"})),
            @RouterOperation(path = "/api/v1/donations/{id}/commission", method = RequestMethod.GET, beanClass = DonationHandler.class, beanMethod = "calculateDonationCommission", operation = @Operation(operationId = "calculateDonationCommission", summary = "Calculate donation commission", tags = {"Donations"})),
            @RouterOperation(path = "/api/v1/donations/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = DonationHandler.class, beanMethod = "listDonations", operation = @Operation(operationId = "listDonations", summary = "List donations by tournament", tags = {"Donations"})),
            @RouterOperation(path = "/api/v1/donations/{id}/status", method = RequestMethod.PATCH, beanClass = DonationHandler.class, beanMethod = "updateDonationStatus", operation = @Operation(operationId = "updateDonationStatus", summary = "Update donation status", tags = {"Donations"}))
    })
    public RouterFunction<ServerResponse> donationRouterFunction(DonationHandler donationHandler) {
        return RouterFunctions.route()
                .path("/api/v1/donations", builder -> builder
                        .POST("", donationHandler::createDonation)
                        .GET("/{id}", donationHandler::getDonation)
                        .GET("/{id}/commission", donationHandler::calculateDonationCommission)
                        .GET("/tournament/{tournamentId}", donationHandler::listDonations)
                        .PATCH("/{id}/status", donationHandler::updateDonationStatus))
                .build();
    }
}

