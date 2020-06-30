package backend

class Stock {

    BigDecimal price;
    Date priceDate;
    
    Double getPriceDouble() { price.doubleValue() }

    static belongsTo = [ company: Company ]

    static transients = ['priceDouble']

    static constraints = {
        price min: new BigDecimal("0.0")
        priceDate max: new Date()
    }
}
