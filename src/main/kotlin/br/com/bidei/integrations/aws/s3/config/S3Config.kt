package br.com.bidei.integrations.aws.s3.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "aws")
data class S3Config(
        var s3PhotoBucket: String = "",
        var s3PhotoBucketRegion: String = "",
        var s3AccessKey: String = "",
        var s3AccessSecret: String = "",
        var s3PhotoBucketUrl: String = ""
)