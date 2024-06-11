# cspm-web-main

교보DTS(이하 고객사)의 <Strong>AWS 자산 보유 현황</Strong>과 해당 자원들의 <Strong>취약점 탐지 현황</Strong>을 보여주는 Next.js Application 입니다. 위 두 결과를 직관적인 데이터 업데이트와 함께 최적화된 페이지 로딩 속도를 제공하는 프로그램입니다.

---

## 프로젝트 참여자

ASAC_04

- 윤승한(yunsh0712@gmail.com) : scanresult.page, components/PageComponents/scanresultPage/\*
- 정승근(wjd15sheep@naver.com) : resource.page, config/account/_.page, components/PageComponents/accountPage/_, components/PageComponents/resourcePage/\*

### 프로젝트 기간

2024.04.15 ~ 2024.06.05

---

# 리소스 스캔 결과 페이지

리소스 스캔 결과 페이지는 고객사의 <Strong>AWS 자원 스캔</Strong> 관련 페이지 입니다.

## 고객사 선택

- resourceScanMultiFilterMenuBar에 종속된 ClientSelectBox를 통해 고객사 별로 데이터를 확인할 수 있습니다.
- Java Spring Application으로 부터 받은 데이터를 resourceTable의 setClientFilter(clientListData)를 통해 선택하여 해당하는 고객사의 정보를 리스팅 합니다.
- Zustand의 setClientFilter를 사용하여 resourceTable에서 clientListData를 받아 clientSelectBox에서 리스트를 map 방식으로 표시 합니다.

## Acoount Name

- resourceScanMultiFilterMenuBar에 종속된 AccountNameSelect를 통해 AccountName 별로 데이터를 확인할 수 있습니다.
- Java Spring Application으로 부터 받은 데이터를 resourceTable의 setAccountNameFilter(accountNameListdata)를 통해 선택하여 해당하는 Account Name 정보를 리스팅 합니다.
- Zustand의 setAccountNameFilter를 사용하여 resouceTable에서 accountNameListdata를 받아 accountNameSelectBox에서 리스트를 map 방식으로 표시합니다.

## Account ID 선택

- resourceScanMultiFilterMenuBar에 종속된 AccountIdSelect를 통해 AccountId 별로 데이터를 확인할 수 있습니다.
- Java Spring Application으로 부터 받은 데이터를 resourceTable의 setAccountIdFilter(accountIdListData)를 통해 선택하여 해당하는 Account ID 정보를 리스팅 합니다.
- Zustand의 setAccountIdFilter 사용하여 resouceTable에서 accountIdListData를 받아 accountIdSelectBox에서 리스트를 map 방식으로 표시합니다.

## Service 선택

- resourceScanMultiFilterMenuBar에 종속된 ServiceSelect를 통해 Service 별로 데이터를 확인할 수 있습니다.

### 자원 스캔 범위에 따른 Service Group

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

## 기간 선택

- resourceScanMultiFilterMenuBar에 종속된 CalendarSelect를 통해 기간 별로 데이터를 확인할 수 있습니다.

- fromCalendar.tsx은 from 기간, toCalendar.tsx은 to 기간을 지정 합니다.
- format.ts은 데이터를 패치하기 전의 형태를 변환합니다. ('yyyy-MM-dd HH:mm')

## 검색

- resourceScanMultiFilterMenuBar에 종속된 search.tsx 입니다.
- 사용자의 입력 받은 데이터를 zustand setBaseSearcheValue(입력값)를 통해 resourceTable fetchList에 담고 API 패치를 통해 포함된 리스트를 가져옵니다.
- 각 페이지마다 공통적으로 사용합니다.

## 필터 해제

- resourceScanMultiFilterMenuBar에 종속된 fillterResetButton.tsx 입니다.
- setRequestMeta()함수를 zustand에 정의하여 모든 값에 대한 필터 초기화를 중앙화 합니다.

## Excel

- resourceScanMultiFilterMenuBar에 종속된 excelDownloadButton.tsx 입니다.
- 버튼 클릭 시 현재 filter 값이 Java Spring Application에 전달 되고 .csv로 변환된 파일을 브라우저에서 다운로드 할 수 있게 합니다.

## 상세 조회

- 결과 값 리스트에 표시된 AWS Resource를 JSON 형태로 보여주는 모달 입니다.

---

# 취약점 스캔 결과 페이지

취약점 스캔 결과 페이지는 고객사의 AWS 자원에 대해 <Strong>Compliance/Policy 기반으로 취약점을 탐지하는</Strong> 페이지 입니다.

## 취약점 상태

- 취약점 상태 값은 초기 <Strong>취약점으로 탐지 될 시</Strong> `Open`을 고정 값으로 설정합니다.
- 이후 결과 입력 기능을 통해 `Exception`, `Close`로 변경 가능하며, shadcn/ui 라이브러리의 vulnerabilityStatus.tsx에 정의합니다.
- 선택된 상태 값은 zustand에 정의한 setVulnerabilityStatusSelected, scanResultTableComponent.tsx로 전달하여 결과를 출력합니다.

## 취약점 등급

- 취약점 등급은 `High`, `Medium`, `Low`로 고정 값을 가지며 shadcn/ui, select 라이브러리의 poliicyServeritySelectBox.tsx에 정의합니다.
- 선택된 값은 zustand로 setPolicySeveritySelected, scanResultTableComponent.tsx에 값을 전달하여 결과를 출력합니다.

## 결과 입력 Modal

- 체크박스로 결과 값 변경할 리스트를 선택, 결과 입력 버튼을 클릭하면 Modal창이 열립니다.
- Exception, Open, Close 중 변경하고자 하는 상태를 선택하고, 내용을 입력합니다.

---

# 사용자 관리 페이지

사용자 관리 페이지는 <Strong>고객사 정보</Strong> 및 <Strong>스캔 시작과 스캔 결과</Strong> 를 확인할 수 있습니다.

## 스캔 시작

- 체크박스로 고객사를 선택하고 스캔 시작을 누릅니다. 이때 선택된 값은 하나 또는 여러개 입니다.
- 몇 개의 고객사가 선택 되었는지 확인하는 메세지가 출력되고, 확인 버튼을 누르면 연결된 Java Spring Application에 요청을 보내고 응답이 올때까지 10초에 1초 간격으로 패치 합니다.
- 응답을 받으면 해당 결과를 출력하고, 결과 모달 창을 닫으면 스캔 성공 여부를 리스트에서 확인 할 수 있습니다.

### Fail

- 스캔에 실패하면 Fail을 출력합니다. Fail 버튼을 누르면 Error log관련 API를 호출합니다.
  - Fail의 원인, 메시지를 Error Type, Error Servie, Error Response로 구분하여 출력합니다.

## 대상 등록

- /config/account/accountadd 패이지로 이동
- 고객사 : 고객사 입력 창
- CODE : 고객사를 구분하기 위한 코드, 영어 다섯 글자만 입력 가능합니다.
- Account Name : 고객사 별로 중복되지 않는 Data를 저장, 중복에 대한 검증은 대상 추가 버튼을 눌렀을 때 API를 호출하며 실시합니다.
- Access Key : AWS Iam 계정 또는 Root 계정의 Access Key
- Secret Key : 위와 동일, 게정의 Secret Key
- Region : AWS의 Region, 현재 서울 지역으로 고정되어있습니다.('ap-northeast-2')
- 비고 : 고객사에 대한 부가적인 내용을 입력할 수 있습니다.
- Account Id : Access Key, Secret Key 입력 후, 오른쪽 조회 버튼을 누르면 API호출, 패치한 값을 출력합니다. (사용자에게 입력 받지 않습니다.)

## 대상 수정

- 버튼 클릭 시 선택한 고객사의 데이터를 패, accountEdit 페이지에 값을 출력합니다.
- 이하 대상 등록 기능과 동일합니다.

## 대상 삭제

- 채크 박스에 선택된 한 개 또는 여러 개의 리스트를 선택, 삭제 버튼을 클릭합니다.
- 삭제에 대한 성공과 실패 결과를 모달에 출력, 사용자에게 전달합니다.

---

# 버전 관리 및 시작

해당 프로젝트에서는 Next.js 14를 사용합니다.
React 라이브러리를 사용하여 CSR(Client-Side Rendering)과 SSR(Server-Side Rendering)을 구분해서 작성해야 합니다.

GitLab을 사용하여 버전 및 형상 관리를 합니다. 원하는 폴더에서 GitLab clone을 받습니다.

```sh
cd 원하는 폴더
git clone http://localhost:8000/cspm/cspm-web-main.git
cd cspm-web-main
code . //vscode 실행(설정을 안한 경우 Vscode 아이콘 선택해서 열기)
```

# 설치

node.js 설치 방법

```sh
npm install
```

만약 NVM으로 특정 Node.js 설치한다면 프로젝트 버전에 맞게 설치해야 합니다.

```sh
nvm install 20.11.0
```

# 서버 접속 방법

### local 환경:

```sh
npm run dev
```

크롬 및 인터넷 브라우저에 URL: [http//localhost3000](http://localhost:3000/)

### 배포 환경 접속:

교보 vpn에 접속
URL: [http//192.168.199.50:8081](http://192.168.199.50:8081/)

# 배포하기

- `.env` 파일, NEXT_APP_BASE_URL, NEXT_APP_DOMAIN_URL을 배포용으로 변경합니다.(localhost 작업용을 주석 처리합니다.)

```sh
# 배포용 (주석 제거)
NEXT_APP_BASE_URL=http://192.168.199.50:8080
NEXT_APP_DOMAIN_URL=http://192.168.199.50:8081

# localhost 작업용 (주석 처리)
# NEXT_APP_BASE_URL=http://localhost:8080
# NEXT_APP_DOMAIN_URL=http://localhost:3000
```

nextJs는 정적 배포와 동적 배포로 나누어져있습니다.

### 정적 배포(SSG)

- 설명: 빌드 시점에 모든 페이지를 정적으로 생성하여 HTML 파일로 저장합니다. 이후 요청이 들어오면 해당 HTML 파일을 그대로 제공합니다.
- 장점: 매우 빠른 성능을 제공하며, 서버 부하 부담이 낮습니다. CDN을 통해 전 세계적으로 빠르게 콘텐츠를 제공할 수 있습니다.
- 단점: 데이터의 실시간성을 보장하기 어렵고, 빌드 이후 데이터가 변경되면 페이지를 다시 빌드해야 합니다.

### 동적 배포(SSR)

- 설명: 각 요청마다 서버에서 페이지를 렌더링하고 HTML을 생성하여 클라이언트에 전달합니다.
- 장점: 데이터의 실시간성을 보장할 수 있습니다. 사용자에게 맞춤화된 데이터를 제공할 수 있습니다.
- 단점: 서버 부하 부담이 높고, 서버의 응답 시간에 따라 페이지 로딩 시간이 길어질 수 있습니다.
  - 사용 예: 사용자 프로필 페이지, 대시보드, 실시간 데이터가 필요한 애플리케이션 등.

데이터의 실시간성과 데이터의 정확성이 필요한 프로젝트이므로 동적 배포를 선택하여 진행하였습니다.

[백엔드 및 정적 배포](https://kyobodts.atlassian.net/wiki/spaces/DTSCSPM/pages/23494660?atlOrigin=eyJpIjoiYWM4MGFkMjIzYjkwNDQ0MmE1NjJlM2YyMjAzNDFjNTgiLCJwIjoiaiJ90)

# 기술 스택 및 도구

## 라이브러리

| 패키지명                 | 버전    | 비고                              |
| ------------------------ | ------- | --------------------------------- |
| @radix-ui/react-checkbox | v1.0.4  | 체크박스 UI 컴포넌트              |
| @radix-ui/react-select   | v2.0.0  | 셀렉트 UI 컴포넌트                |
| date-fns                 | v3.6.0  | 날짜 관련 유틸리티                |
| next                     | v14.2.3 | React 기반의 프레임워크           |
| react                    | v18     | UI 빌드를 위한 라이브러리         |
| react-dom                | v18     | DOM과 상호 작용을 위한 라이브러리 |
| tailwindcss              | v3.4.1  | 유틸리티 우선 CSS 프레임워크      |
| zustand                  | v4.5.2  | 상태 관리 라이브러리              |

## ui

shadcn/ui의 select를 사용하면서 생긴 폴더

- button.tsx
- buttonLayer.tsx: button의 공통된 css 처리를 위해서 만듬
- select.tsx

## lib - utils.ts

shandcn/ui를 사용하기 위한 라이브러리(자동으로 생성)입니다.

---

# 라이선스

(주)교보정보통신

# 메모, 코멘트

- 성능 개선
  - shadcn/ui의 select를 제거하고 다른 것으로 대체해야 합니다. chrom 개발자 도구의 Lighthouse로 돌려본 결과 접근성에서 아쉬운 부분이 있어서 수정했으면 합니다.
  - 각 페이지의 테이블 리스트와 필터 값을 한 api로 가져오는데 이를 변경해야 합니다. 한 api 요청에 너무 많은 데이터를 가져오므로 더 많은 데이터가 쌓이면 많은 로딩 시간이 걸릴 것으로 예상이 되어 각 테이블 리스트 페이지 번호를 누르면 그만큼의 데이터만 호출이 되도록 변경해야 합니다.
    마감 하루 전에 변경하려고 시도를 해봤으나 버그가 많이 발견되어 롤백을 하였는데 꼭 수정하셨으면 좋겠습니다.
  - 모르는 것, 알기 어려운 코드 있으면 이메일로 연락을 주시면 답장해 드리겠습니다. 원하는 만큼 이루어서 꼭 성공하십쇼!!
