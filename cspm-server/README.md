# cspm-server-main

교보DTS(이하 고객사)의 AWS 계정(IAM 계정)이 보유하고 있는 자원들을 java Spring Application을 통해 접근, 스캔, DataBase에 저장합니다.
스캔한 데이터들을 취약점 탐지 쿼리로 조회하여 탐지된 취약점 결과를 DataBase에 저장합니다.

위 두 결과 즉, <Strong>AWS 자산 보유 현황</Strong>과 해당 자원들의 <Strong>취약점 탐지 현황</Strong>을 사용자에게 보여주기 위한 프로그램입니다.


## 프로젝트 참여자

ASAC_04 </span>
- 김동규: (dev.sentory1989@gmail.com) : Multi DataSource, Async Describe, AWS Key AES256, Exception Log, Common Response, Auto SQL, Dev/Release Setting, Other ...   
- 윤승한: (yunsh0712@gmail.com) : downloadDescribeData, downloadCompliance, downloadAccountData
- 이루다: (ruda991@gmail.com) : Resource ERD, getDetailDescribe, getComplianceList, getDetailCompliance, createException, getDetailException
- 이승주: (dltmdwn0111@gmail.com) :WBS, Resource ERD, getDetailCompliance, getAwsDescribeResultList, createException, getDetailException, back & front Release
- 정승근: (wjd15sheep@gmail.com) : complianceSave, findComplianceByPolicy, queryPatternAndReturnValue, complianceFilterList, getAwsDescribeResultList, accountAllList, DescribeRepositoryCustomImpl , Swagger, Other...

### 프로젝트 버전 
v 1.0.0

### 프로젝트 기간

2024.04.15 ~ 2024.06.05

### ERD

CSPM

![cspm.png](src%2Fmain%2Fresources%2FREADME-JPG%2Fcspm.png)

<style>
  img {
    max-width: 70%;
    height: auto;
  }
</style>

COMPLIANCE

![compliance.png](src%2Fmain%2Fresources%2FREADME-JPG%2Fcompliance.png)

---
### 핵심 기능 



<div align="center">


|                                                         **기능**                                                          |                                                                                                                                                                                                                                     **참고사진**                                                                                                                                                                                                                                      |  
|:-----------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                 <h2>관리자 페이지</h2> - 고객사 등록, 수정, 삭제 기능 <br/>- AWS 자원 스캔 기능 <br/>- 자원 스캔 권한이 없을 시 실패 모달 기능                 |             ![사용자등록V2.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%82%AC%EC%9A%A9%EC%9E%90%EB%93%B1%EB%A1%9DV2.png)                                 <br/>![관리자 페이지.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EA%B4%80%EB%A6%AC%EC%9E%90%20%ED%8E%98%EC%9D%B4%EC%A7%80.png)<br/> ![스캔 실패 모달.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%8A%A4%EC%BA%94%20%EC%8B%A4%ED%8C%A8%20%EB%AA%A8%EB%8B%AC.png)                                               |
|       <h2>리소스 스캔 결과 페이지</h2> - 스캔한 AWS 자원에 대한 <Strong>전체</Strong> 조회 <br/>- 스캔한 AWS 자원에 대한 <Strong>상세 조회</Strong>       |                                                                                    ![자원 스캔 결과페이지.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%9E%90%EC%9B%90%20%EC%8A%A4%EC%BA%94%20%EA%B2%B0%EA%B3%BC%ED%8E%98%EC%9D%B4%EC%A7%80.png)<br/>  ![자원 상세 조회 모달.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%9E%90%EC%9B%90%20%EC%83%81%EC%84%B8%20%EC%A1%B0%ED%9A%8C%20%EB%AA%A8%EB%8B%AC.png)                                                                                    |
| <h2>취약점 스캔 결과 페이지</h2> - 자원에 대한 취약점 <Strong>전체</Strong> 조회<br/> - 자원에 대한 취약점 <Strong>상세</Strong> 조회<br/>- 취약점 상태 Exception 변경 | ![취약점 전체 조회 페이지.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%B7%A8%EC%95%BD%EC%A0%90%20%EC%A0%84%EC%B2%B4%20%EC%A1%B0%ED%9A%8C%20%ED%8E%98%EC%9D%B4%EC%A7%80.png)  <br/>     ![취약점 상세 조회 모달.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%B7%A8%EC%95%BD%EC%A0%90%20%EC%83%81%EC%84%B8%20%EC%A1%B0%ED%9A%8C%20%EB%AA%A8%EB%8B%AC.png)<br/>   \ ![취약점 Exception 변경.png](src%2Fmain%2Fresources%2FREADME-JPG%2F%EC%B7%A8%EC%95%BD%EC%A0%90%20Exception%20%EB%B3%80%EA%B2%BD.png) | 



</div>


---

# account 도메인

account 도메인은 <Strong>고객사 정보</Strong>와 관련된 기능을 담당합니다.

## 엔터티

### accounts: 고객사 정보를 담고 있으며 AWS SDK를 이용하여 자원에 접근하기 위한 주요 엔터티입니다.
- id (Long) : 생성된 accounts 계정의 고유 아이디입니다.
- accountId (String): AWS SDK의 AwsBasicCredentials를 이용하여 AWS Access Key ID와 AWS Secret Access Key 조합으로 생성된 Id입니다.
- accessKey (String): IAM 계정의 AWS Access Key ID 입니다. 사용자에게 입력 받은 후 AES256 알고리즘으로 암호화 하여 DataBase에 저장합니다.
- secretKey (String): IAM 계정의 AWS Secret Access Key 입니다. 사용자에게 입력 받은 후 AES256 알고리즘으로 암호화 하여 DataBase에 저장합니다.
- accountName (String): 사용자에게 입력 받는 부서 이름 입니다.
- client (String): 사용자에게 입력 받은 고객사 이름 입니다.
- code (String): 고객사 혹은 계열사를 구분하기 위한 이름입니다. 중복 될 수 있습니다.
- comment (String): 비고란 혹은 추가 정보를 입력합니다.
- describeResult (String): 스캔 결과 정보입니다. Success, Fail을 저장합니다.
- lastUpdateDescribeTime (String): 최근 리소스 스캔 시간 정보입니다.
- region (String): 등록된 IAM 계정의 지역 정보입니다.
- registerTime (String): 고객사 등록 시간 정보입니다.

## 컨트롤러

### AcccountController
- **getAccountIdAndName**: IAM 계정의 accessKey, secretAccessKey로 accountId를 조회하고, accountId와 accountName을 반환하는 메서드입니다.
  - **`Endpoint`**: `accounts/info`
  - **`Method`**: `GET`
  
- **createAccount**: 관리자 페이지에서 고객사를 등록하는 메서드입니다.
  - **`Endpoint`**: `/accounts`
  - **`Method`**: `POST`

- **getAccountList**: 관리자 페이지에서 DataBase에 저장되어있는 모든 고객사의 정보를 반환하는 메서드입니다.
  - **`Endpoint`**: `accounts/list`
  - **`Method`**: `POST`

- **updateAccount**: 고객사의 정보를 수정하는 메서드입니다.
  - **`Endpoint`**: `/accounts/{accountId}/{accountName}`
  - **`Method`**: `PUT`

- **getAcccount**: 수정할 고객사 정보를 DataBase에서 가져오는 메서드입니다. 
  - **`Endpoint`**: `/accounts/{accountId}/{accountName}`
  - **`Method`**: `GET`

- **deleteAccount**: DataBase에 저장된 고객사를 삭제하는 메서드입니다.
  - **`Endpoint`**: `/accounts`
  - **`Method`**: `DELETE`

- **getAccountError**: AWS 자원 스캔에 실패한 결과를 반환해주는 메서드입니다.
  - **`Endpoint`**: `/accounts/error/{accountId}/{scanTime}/{accountName}`
  - **`Method`**: `GET`

## Swagger
[Account Swagger 문서 참조](http://localhost:8080/swagger-ui/index.html#/CSPM%20account%20controller)

----

# Resource 도메인

Resource 도메인은 고객사의 <Strong>AWS 자원 스캔</Strong> 기능을 담당합니다.

## 엔터티

### 1. resource_describe: AWS SDK를 통해 스캔한 정보를 담고 있는 엔터티입니다.
- id (Long): 스캔 된 자원의 고유 아이디입니다.
- accountId (String): 고객사의 AWS accountId 입니다.
- accountName (String): 사용자에게 입력 받는 부서 이름 입니다.
- client (String): 고객사 이름입니다.
- createTime (String): 고객사를 등록한 시각입니다.
- describeResult (String): 스캔 결과 입니다. Success, Fail을 저장합니다.
- resourceId (String): 자원의 고유 아이디입니다.
- resourceJson (String): 자원의 정보를 JSON 형식으로 저장합니다.
- scanTime (String): 스캔한 시각 입니다.
- serviceGroup (String): 자원을 서비스로 묶은 그룹 정보 입니다.
- tag (String) : 자원에 할당된 태그 정보입니다.

### 2. error_log : AWS SDK 접근 시 발생한 에러를 저장하기 위한 엔터티입니다.
- id (Long): 에러 엔터티의 고유 아이디입니다.
- accountId (String): 고객사의 AWS accountId입니다.
- accountName (String): 사용자에게 입력 받는 부서 이름 입니다.
- describeTime (String): 자원을 스캔한 시각입니다.
- serviceGroup (String): 자원을 서비스로 묶은 그룹 정보 입니다.
- resource (String): AWS 자원 정보 입니다. 
- exceptionCode (String): 정의된 예외 코드 정보입니다.
- exceptionMsg (String): 정의된 예외의 메세지 정보입니다.

### 자원 스캔 범위

- VPC
  - VPC
  - Subnet
  - Security Group
  - Routing
  - Internet GateWay (IGW)

- EC2
  - Instance
  - EBS
  - ENI (Network Interface)

- S3
  - S3 (Bucket)

#### 스캔 범위에 따른 각 자원 엔터티

- VPC
  - VPCEntity
  - SubnetEntity
  - SecurityGroupEntity
  - RouteEntity
  - IGWEntity

- EC2
  - InstanceEntity
  - EBSEntity
  - ENIEntity

- S3
  - S3Entity


## 컨트롤러

### ResourceController
- **saveDescribe**: 고객사의 AWS 자원을 스캔, 저장하고 해당 자원들의 취약점을 탐지, 저장하는 메서드 입니다.
  - **`Endpoint`**: `/resources`
  - **`Method`**: `POST`

- **getDescribeList**: AWS 자원을 스캔한 전체 결과를 반환하는 메서드입니다.
  - **`Endpoint`**: `/resorces/list`
  - **`Method`**: `POST`

- **getDetailDescribe**: AWS 자원을 스캔한 해당 자원의 모든 정보를 반환하는 상세 조회 메서드입니다.
  - **`Endpoint`**: `/resources/{resourceId}/{scanTime}/{accountId}/{accountName}`
  - **`Method`**: `GET`

## Swagger
[Resource Swagger 문서 참조](http://localhost:8080/swagger-ui/index.html#/CSPM%20Resource%20controller)

---

# Compliance 도메인

Compliance 도메인은 저장된 고객사의 AWS 자원을 <Strong>Compliance/Policy 기반으로 취약점을 탐지</Strong>하는 기능을 담당합니다.

## 엔터티

### 1. policy : 취약점을 스캔하기 위한 정책을 담고 있는 주요 엔터티입니다.
- id (Long): 탐지 패턴의 고유 아이디입니다.
- policyCompliance (String): Compliance 정보입니다.
- policyDescription (String): 탐지된 취약점에 대한 설명 정보입니다.
- policyPattern (String): 탐지 패턴(Query) 정보입니다.
- policyResponse (String): 탐지된 취약점의 대응 방안 정보입니다.
- policySeverity (String): 탐지 패턴의 중요도 정보입니다.
- policyTitle (String): 정책 이름 정보입니다.
- policyType (String): 탐지 패턴 분류 정보입니다.

### 2. Compliance: 고객사의 AWS 자원을 탐지 패턴으로 스캔한 결과, 취약점으로 발견된 정보를 담는 엔터티입니다.
- id (Long): Compliance의 고유 아이디입니다.
- accountId (String): 고객사의 AWS accountId 입니다.
- accountName (String): 사용자에게 입력 받는 부서 이름 입니다.
- client (String): 고객사 이름 입니다.
- policyCompliance (String): Compliance 정보입니다.
- policyDescription (String): 탐지된 취약점에 대한 설명 정보입니다.
- policyResponse (String): 탐지된 취약점의 대응 방안 정보입니다.
- policySeverity (String): 탐지 패턴의 중요도 정보입니다.
- policyTitle (String): 정책 이름 정보입니다.
- policyType (String): 탐지 패턴 분류 정보입니다.
- resourceId (String): 자원의 고유 아이디입니다.
- rid (String): 스캔 결과값을 고객사별로 구분할 수 있는 아이디입니다.
- scanTime (String): 탐지 패턴으로 자원을 스캔한 시각 입니다.
- service (String): 자원을 서비스로 묶은 그룹 정보입니다.
- vulnerabilityStatus (String): 취약점 상태 정보입니다.


### 3. Compliance_Exception : 탐지된 취약점을 예외로 처리하기 위한 정보를 담는 엔터티입니다.
- id (Long): Exception의 고유 아이디입니다.
- accountId (String): 고객사의 AWS accountId 입니다.
- accountName (String): 사용자에게 입력 받는 부서 이름 입니다.
- exceptionContent (String): 고객사가 입력한 예외 처리 내용 정보입니다.
- exceptionHandler (String): 해당 예외 처리자 정보입니다.
- exceptionTime (String): 예외를 처리 한 시각 입니다.
- policyTitle (String): 정책 이름 정보 입니다.
- resourceId (String): 자원의 고유 아이디입니다.

## 컨트롤러

### ComplianceController
- **getComplianceList**: 탐지 패턴으로 스캔한 자원의 전체 결과를 반환하는 메서드입니다.
  - **`Endpoint`**: `/compliances/list`
  - **`Method`**: `POST`

- **getDetailCompliance**: 탐지 패턴으로 스캔한 해당 자원의 모든 정보를 반환하는 상세 조회 메서드입니다.
  - **`Endpoint`**: `/compliances/{resourceId}/{scanTime}/{accountId}/{accountName}`
  - **`Method`**: `GET`

- **createException**: Open인 취약점 상태를 Exception으로 변경하고 내용을 저장하는 메서드입니다.
  - **`Endpoint`**: `/compliances/exception`
  - **`Method`**: `POST`

- **getDetailException**: 예외로 처리한 내용을 반환하는 상세 조회 메서드입니다.
  - **`Endpoint`**: `/compliances/exception/{resourceId}/{policyTitle}/{accountId}/{accountName}`
  - **`Method`**: `GET`

## Swagger
[Compliance Swagger 문서 참조](http://localhost:8080/swagger-ui/index.html#/CSPM%20Compliance%20Controller)

---

# Download 도메인

고객사, 리소스 스캔, 취약점 스캔 데이터를 .csv 파일로 변환하여 클라이언트(브라우저)에서 다운로드할 수 있는 기능을 담당합니다.


## 컨트롤러

### DownloadController
- **downloadDescribeData**: `DescribeFilterDto`에 부합하는 .csv 파일을 생성하여 다운로드 할 수 있는 기능을 담당하는 메서드입니다.
  - **``Endpoint``**: `/downloads/xlsx/resource`
  - **`Method`**: `POST`

- **downloadCompliance**: `ComplianceRequestDto`에 부합하는 .csv 파일을 생성하여 다운로드 할 수 있는 기능을 담당하는 메서드입니다.
  - **``Endpoint``**: `/downloads/xlsx/compliance`
  - **`Method`**: `POST`

- **downloadAccountData**: `ScanConfigFilterDto`에 부합하는 .csv 파일을 생성하여 다운로드 할 수 있는 기능을 담당하는 메서드입니다.
  - **``Endpoint``**: `/downloads/xlsx/account`
  - **`Method`**: `POST`


---

# 버전 관리 및 시작

GitLab을 사용하여 버전 및 형상 관리를 합니다. 원하는 폴더에서 GitLab clone을 받습니다.

```sh
cd 원하는 폴더
git clone http://localhost:8080/cspm/cspm-server-main.git
cd cspm-server-main
```

----

# Server 접속 방법

[Server 및 DB 접속 방법 Jira ](https://kyobodts.atlassian.net/wiki/spaces/DTSCSPM/pages/98508/CSPM)

---

# DataBase 접속 방법

[Server 및 DB 접속 방법 Jira ](https://kyobodts.atlassian.net/wiki/spaces/DTSCSPM/pages/98508/CSPM)

---

# 배포

[백엔드 및 정적 배포](https://kyobodts.atlassian.net/wiki/spaces/DTSCSPM/pages/23494660?atlOrigin=eyJpIjoiYWM4MGFkMjIzYjkwNDQ0MmE1NjJlM2YyMjAzNDFjNTgiLCJwIjoiaiJ90)


# 기술 버전 정보

| 항목       | 명칭          | 버전               | 비고      |
|----------|-------------|------------------|---------|
| Server   | Java        | java corretto-17 | -       |
| -        | Spring Boot | v3.24            | -       |
| -        | Data JPA    | -                | -       |
| -        | Lombok      | -                | -       |
| AWS SDK  | awssdk:ec2  | v2.25.31         | -       |
| -        | awssdk:s3   | v2.13.0          | -       |
| Query    | QueryDSL    | v5.0.0           | -       |
| Database | MariaDB     | v11.3.2          | DBeaver |
| UI       | Swagger     | v2.5.0           | -       |

---

# 라이선스


(주)교보정보통신

---

# 메모, 코멘트
