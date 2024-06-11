package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "resource_s3", schema = "cspm")
public class S3Entity {

    @Id
    @Column(nullable = false, name = "s3_id")
    private String s3Id;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(columnDefinition = "TEXT", name = "server_side_encryption_configuration")
    private String serverSideEncryptionConfiguration;

    @Column(columnDefinition = "TEXT", name = "public_access_block_configuration")
    private String publicAccessBlockConfiguration;
}