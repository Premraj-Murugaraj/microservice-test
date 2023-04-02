import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description("contract definition for get all car")
            name("should return all car")
            priority(1)
            request {
                method(GET())
                url("/api/cars")
                headers {
                    contentType(applicationJson())
                    accept(applicationJson())
                }
            }
            response {
                status(200)
                status(OK())
                body(file("list_cars.json"))
            }
        },

        Contract.make {

            description("contract definition for get a car")
            name("should return a single car")
            request {
                method(GET())
                urlPath(value(regex("/api/aCar/[0-9]")))
                headers {
                    contentType(applicationJson())
                    accept(applicationJson())
                }
            }
            response {
                status(200)
                status(OK())
                body([
                        id: value(test(regex("[0-9]{3}")), stub(1)),
                        modelName: $(producer(anyNonBlankString()), consumer("Toyota_Chrysta")),
                        price: $(p(anyNonBlankString()), c("2300000")),
                        manufactureYear: [$(producer(regex('[0-9]{4}')),consumer(2021)),
                                          $(producer(regex('[0-9]{1}')),consumer(4)),
                                          $(p(regex('[0-9]{1}')),c(2))]
                ])
            }
        }

]
