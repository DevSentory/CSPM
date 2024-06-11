package kyobo.cspm.compliance.entity;

import jakarta.persistence.*;
import kyobo.cspm.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "compliance", schema = "compliance")
public class ComplianceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id",nullable = false, length = Constants.LENGTH_SMALL)
    private String resourceId;

    @Getter
    @Column(length = Constants.LENGTH_SMALL, nullable = false)
    private String rid;

    @Column(name = "scan_time",nullable = false)
    private String scanTime;

    @Column(name = "vulnerability_status",nullable = false, length = Constants.LENGTH_SMALL)
    private String vulnerabilityStatus;

    @Column(name="policy_type",nullable = false, length = Constants.LENGTH_SMALL)
    private String policyType;

    @Column(name="account_name",nullable = false, length = Constants.LENGTH_SMALL)
    private String accountName;

    @Column(nullable = false, length = Constants.LENGTH_SMALL)
    private String client;

    @Column(name="policy_severity",nullable = false, length = Constants.LENGTH_SMALL)
    private String policySeverity;

    @Column(name="policy_compliance",nullable = true, columnDefinition = "TEXT")
    private String policyCompliance;

    @Column(name = "policy_title", nullable = false, length = Constants.LENGTH_SMALL)
    private String policyTitle;

    @Column(name = "account_id",nullable = false, length = Constants.LENGTH_SMALL)
    private String accountId;

    @Column(nullable = false, length = Constants.LENGTH_SMALL)
    private String service;

    @Column(name = "policy_description",nullable = true, columnDefinition = "TEXT")
    private String policyDescription;

    @Column(name = "policy_response",nullable = true, columnDefinition = "TEXT")
    private String policyResponse;

}
