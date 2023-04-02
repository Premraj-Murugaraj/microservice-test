import org.springframework.cloud.contract.spec.Contract
import java.time.LocalDate;

Contract.make {
    name("should create a car")
    request {
        urlPath("/api/create/car")
        headers {
            contentType(applicationJson())
        }
        method(POST())
        body(
                id: 123,
                modelName: "Toyota_Chrysta",
                manufactureYear: [LocalDate.now().minusYears(2).getYear(),
                                  LocalDate.now().minusYears(2).getMonthValue(),
                                  LocalDate.now().minusYears(2).getDayOfMonth()],
                price: "2300000"
        )
        bodyMatchers {
            jsonPath('$.id', byRegex('[0-9]{3}'))
            jsonPath('$.modelName', byRegex('[A-Za-z_]*'))
            jsonPath('$.manufactureYear', byType {
                minOccurrence(1)
                maxOccurrence(3)
            })
        }
    }

    response {
        status(CREATED())
        body(
                id: 123,
                modelName: "Toyota_Chrysta",
                manufactureYear: [
                        LocalDate.now().minusYears(2).getYear(),
                        LocalDate.now().minusYears(2).getMonthValue(),
                        LocalDate.now().minusYears(2).getDayOfMonth()
                ],
                price: "2300000"
        )
        bodyMatchers {
            jsonPath('$.id', byRegex('[0-9]{3}'))
            jsonPath('$.modelName', byRegex('[A-Za-z_]*'))
            jsonPath('$.manufactureYear', byType {
                minOccurrence(1)
                maxOccurrence(3)
            })
        }
    }
}