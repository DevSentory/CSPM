package kyobo.cspm.describe.entity;

import jakarta.persistence.*;
import kyobo.cspm.utils.Constants;
import lombok.Getter;

@Getter
@Entity
@Table(name = "policy", schema = "cspm")
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = Constants.LENGTH_SMALL)
    private String policyType;

    @Column(nullable = false,  length = Constants.LENGTH_MIDDLE)
    private String policyTitle;

    @Column(nullable = false, length = Constants.LENGTH_SMALL)
    private String policySeverity;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String policyDescription;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String policyResponse;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String policyCompliance;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String policyPattern;

}
