package backend

import groovy.sql.Sql
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.List;
import java.lang.System

class StockService {

    static def stocks = new ArrayList<Stock>()

    def put(BigDecimal price, Date priceDate, Company company) {
        stocks.add(new Stock(price: price, priceDate: priceDate, company: company))
    }

    def getStocks(Company company, Integer numbersOfHoursUntilNow) {
        def init = System.currentTimeMillis()
        
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(-1*numbersOfHoursUntilNow)
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

        def companyFilter = { stock -> stock.company.id == company.id }
        def hourFilter = { stock -> stock.priceDate.compareTo(date) >= 0 }
        def quoties = stocks.stream()
            .filter(companyFilter)
            .filter(hourFilter)
            .collect(Collectors.toList())

        def end = System.currentTimeMillis()
        
        quoties.each { println it.price }
        
        println(end - init)

        println(quoties.size())
    }

    def getStandardDeviation(Company company) {

        def companyFilter = { stock -> stock.company.id == company.id }
        def companyQuoties = stocks.stream().filter( companyFilter ).collect(Collectors.toList())

        def mapPriceDouble = { stock -> stock.priceDouble }
        def average = companyQuoties.stream().mapToDouble(  mapPriceDouble  ).average().orElse(0)

        def mapFraction = { stock -> Math.pow(stock.price - average,2) }
        def deviationAverage = companyQuoties.stream().mapToDouble(  mapFraction ).average().orElse(0)

        BigDecimal standarDeviation = new BigDecimal(Math.sqrt(deviationAverage))

        standarDeviation.setScale(2, RoundingMode.HALF_UP)
    }
}
