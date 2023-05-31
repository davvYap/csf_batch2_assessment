package ibf2022.batch2.csf.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    private static final String SPACE_BUCKET_ENDPOINT = "sgp1.digitaloceanspaces.com";

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        EndpointConfiguration epConfiguration = new EndpointConfiguration(SPACE_BUCKET_ENDPOINT, "sgp1");

        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(epConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .build();

        return client;
    }

}
