package br.com.bidei.integrations.aws.s3.infrastructure.adapters

import br.com.bidei.auction.domain.ports.services.FileUploadServicePort
import br.com.bidei.integrations.aws.s3.config.S3Config
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream


@Service
class S3FileUploadServiceAdapter : FileUploadServicePort{
    @Autowired
    lateinit var config: S3Config

    private fun getClient() =
            AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.valueOf(config.s3PhotoBucketRegion))
                    .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(config.s3AccessKey, config.s3AccessSecret)))
                    .build()

    override fun uploadImage(base64: String, name: String): String {
        val decodedByte = Base64.decode(base64)
        val inputStream = ByteArrayInputStream(decodedByte)
        val s3 = getClient()

        val metadata = ObjectMetadata()
        metadata.contentType = "image/png"
        metadata.contentLength = decodedByte.size.toLong()

        s3.putObject(config.s3PhotoBucket , name, inputStream, metadata)

        return config.s3PhotoBucketUrl + name
    }

    override fun deletePhoto(name: String) {
        val s3 = getClient()
        s3.deleteObject(config.s3PhotoBucket, name)
    }
}