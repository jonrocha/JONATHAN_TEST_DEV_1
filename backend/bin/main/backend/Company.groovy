package backend

class Company {

    String name
    String segment
    BigDecimal standardDeviation

    static transients = ['standardDeviation']

    static constraints = {
        name maxSize: 255
        segment maxSize: 255
    }

    public BigDecimal getStandardDeviation() {
        return this.standardDeviation;
    }
}
