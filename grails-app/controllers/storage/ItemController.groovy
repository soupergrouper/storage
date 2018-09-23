package storage

import grails.core.GrailsApplication

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static defaultAction = "list"

    def exportService

    def uploadService

    GrailsApplication grailsApplication

    String lastExecutedQuery

    def itemList

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        itemList = Item.createCriteria().list(params) {
            if (params.query) {
                and {
                    params.query.split(" ").each { word ->
                        or {
                            brand {
                                ilike("name", "%${word}%")
                            }
                            ilike("name", "%${word}%")
                        }
                    }
                }
            }
        }

        respond itemList, model: [itemCount: itemList.totalCount]
    }

    def lowStock(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Item.list(params).findAll{it.quantity < 5}, model:[itemCount: Item.count()]
    }

    def show(Item item) {
        respond item
    }

    def create() {
        respond new Item(params)
    }

    @Transactional
    def save(Item item) {
        if (item == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (item.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond item.errors, view:'create'
            return
        }

        item.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), item.id])
                redirect item
            }
            '*' { respond item, [status: CREATED] }
        }
    }

    def edit(Item item) {
        respond item
    }

    @Transactional
    def update(Item item) {
        if (item == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (item.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond item.errors, view:'edit'
            return
        }

        item.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), item.id])
                redirect item
            }
            '*'{ respond item, [status: OK] }
        }
    }

    @Transactional
    def delete(Item item) {

        if (item == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        item.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), item.id])
                redirect action:"list", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def export() {
        if(params?.f && params.f != "html") {
            List fields = ["externalId", "name", "brand", "size", "price", "quantity"]

            response.contentType = grailsApplication.config.getProperty("grails.mime.types.${params.f}")
            def extension = params.f == 'excel' ? 'xls' : params.f
            response.setHeader("Content-disposition", "attachment; filename=items.${extension}")

            exportService.export(params.f, response.outputStream, itemList, fields, null, [:], [:])
        }
    }

    def upload() {
        def file = request.getFile("uploadedFile")
        if (!file) {
            flash.message = "File is not selected"
            redirect action:"list"
        }

        log.info "Trying to export data from file [{}]", file.filename
        try {
            uploadService.importItemsFromFile(file.inputStream, file.filename)
            flash.message = "Items were imported successfully"
        }
        catch (Exception e) {
            log.error(e.message)
            flash.message = "Error occurred while importing items: " + e.message
        }
        redirect action: "list"
    }
}
