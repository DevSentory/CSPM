package kyobo.cspm.compliance.entity;

import jakarta.persistence.*;
import kyobo.cspm.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "compliance_exception")
public class ComplianceExceptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exception_time", nullable = true)
    private String exceptionTime;

    @Column(name = "exception_content", nullable = true)
    private String exceptionContent;

    @Column(name = "exception_handler", nullable = true)
    private String exceptionHandler;

    @Column(name = "policy_title", nullable = true)
    private String policyTitle;

    @Column(name = "resource_id",nullable = false, length = Constants.LENGTH_SMALL)
    private String resourceId;

    @Column(name = "account_id", nullable = false, length = Constants.LENGTH_SMALL)
    private String accountId;

    @Column(name = "account_name",nullable = false, length = Constants.LENGTH_SMALL)
    private String accountName;

}