package br.com.bidei.auction.domain.ports.services

interface FileUploadServicePort {
    fun uploadImage(base64: String, name: String): String
    fun deletePhoto(name: String)
}