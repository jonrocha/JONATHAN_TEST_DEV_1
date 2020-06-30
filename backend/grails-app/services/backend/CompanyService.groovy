package backend

import grails.gorm.transactions.Transactional

@Transactional
class CompanyService {

    Company save(String name, String segment) {
        Company company = new Company(name: name, segment: segment)
        company.save()
        company
    }
}
