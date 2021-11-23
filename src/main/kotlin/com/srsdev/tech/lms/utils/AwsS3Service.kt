package com.srsdev.tech.lms.utils

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.IOUtils
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream

@Service
class AwsS3Service(
    private val awsS3Client: AmazonS3Client
) {
    fun uploadFile(bucketName: String, key: String, objectMetaData: ObjectMetadata, inputStream: InputStream): String{
        try {
            awsS3Client.putObject(bucketName, key,inputStream, objectMetaData)
        } catch (e: AmazonServiceException){
            throw IllegalStateException("Failed to upload the file", e)
        }
//        awsS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead)
        return awsS3Client.getResourceUrl(bucketName, key)
    }

    fun downloadFile(bucketName: String, key: String): ByteArray{
        try{
            val obj = awsS3Client.getObject(bucketName,key)
            val objContent = obj.objectContent
            return IOUtils.toByteArray(objContent)
        } catch (e: AmazonServiceException){
            throw IllegalStateException("Failed to download the file", e)
        } catch (e: IOException){
            throw IllegalStateException("Failed to download the file", e)
        }
    }
//    fun uploadFile(file: MultipartFile): String{
//        val filenameExtension = StringUtils.getFilenameExtension(file.originalFilename)
//        val key = UUID.randomUUID().toString() + "." + filenameExtension
//        val metaData = ObjectMetadata()
//        metaData.contentLength = file.size
//        metaData.contentType = file.contentType
//        try {
//            awsS3Client.putObject("lmsportal-amazon-aws", key, file.inputStream, metaData)
//        } catch (e: IOException) {
//            throw ResponseStatusException(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "An exception occured while uploading the file"
//            )
//        }
//        awsS3Client.setObjectAcl("lmsportal-amazon-aws", key, CannedAccessControlList.PublicRead)
//        return awsS3Client.getResourceUrl("my-videos-bucket-05", key)
//    }
}