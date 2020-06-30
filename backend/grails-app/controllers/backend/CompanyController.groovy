package backend

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.JSON
import java.util.stream.Collectors;

class CompanyController {

    StockService stockService

    static responseFormats = ['json']

    def index() { }

    def list() {
        def companies = Company.list()

        def map = { company -> [ id:company.id+"", 
            name:company.name, 
            segment:company.segment, 
            standardDeviation:stockService.getStandardDeviation(company)+"" ] }

        def list = companies.parallelStream().map( map ).collect(Collectors.toList())
        
        def json = render list as JSON

        respond json
    }
}
