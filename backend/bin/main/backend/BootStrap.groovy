package backend

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

class BootStrap {

    CompanyService companyService
    StockService stockService

    def init = { servletContext ->

        Company ford = companyService.save("Ford", "Vehicle")
        Company weg = companyService.save("Weg", "Motors")
        Company vale = companyService.save("Vale", "Metal Mining")

        generateFakeData(ford, weg, vale);

    }
    def destroy = {
    }
	
	def generateFakeData(Company ford, Company weg, Company vale) {
		LocalDateTime today = LocalDateTime.now()
		
		LocalDateTime pastDate = LocalDateTime.of(LocalDate.now().plusDays(-29), LocalTime.of(10, 0))
		
		LocalDateTime stopTime
		
		while (pastDate.isBefore(today)) {
			if (isBusinessDay(pastDate)) {
				stopTime = pastDate.plusHours(8)
				while (pastDate.isBefore(stopTime)) {
					Date priceDate = Date.from(pastDate.atZone(ZoneId.systemDefault()).toInstant())

					stockService.put(getRandomPrice(), priceDate, ford)
					stockService.put(getRandomPrice(), priceDate, weg)
					stockService.put(getRandomPrice(), priceDate, vale)
		
					pastDate = pastDate.plusMinutes(1)
				}
			}
			pastDate = LocalDateTime.of(pastDate.plusDays(1).toLocalDate(), LocalTime.of(10, 0))
		}

		stockService.getStocks(ford,30)
	}

    BigDecimal getRandomPrice() {
        BigDecimal max = new BigDecimal(100)
        BigDecimal randFromDouble = new BigDecimal(Math.random())
        BigDecimal actualRandomDec = randFromDouble.multiply(max)
        actualRandomDec.setScale(2, BigDecimal.ROUND_DOWN)
    }

    boolean isBusinessDay(LocalDateTime date) {
		return (date.getDayOfWeek() != DayOfWeek.SATURDAY
				&& date.getDayOfWeek() != DayOfWeek.SUNDAY);
	}
}
