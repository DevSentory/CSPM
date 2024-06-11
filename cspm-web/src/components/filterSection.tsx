import { useSelectType } from '@/components/Zustand/store'
import { useEffect } from 'react'
import CancleIcon from '@image/icons/cancelIcon.svg'

interface Props {
  index: number
  id: string
  label: string
  value: string
  onClear: () => void
  borderColor: string
}

const FilterItem = ({ index, id, label, value, onClear, borderColor }: Props) => {
  if (value.length === 0) return null

  return (
    <div
      key={index}
      id={id}
      className={`${borderColor === 'orange' ? 'border-orange' : 'border-liteBule'} flex items-center gap-1 rounded-full border-2 px-1`}
    >
      {label} : {value}
      <button className='flex h-3 w-3 items-center' onClick={onClear}>
        <CancleIcon />
      </button>
    </div>
  )
}

export default function FilterSection() {
  const {
    accountNameSelected,
    clientSelected,
    accountIdSelected,
    vulnerabilityStatusSelected,
    policySeveritySelected,
    serviceSelected,
    selectedResource,
    setSelectedResource,
    setServiceSelected,
    setVulnerabilityStatusSelected,
    setAccountNameSelected,
    setClientSelected,
    setAccountIdSelected,
    setPolicySeveritySelected,
  } = useSelectType()

  const resourceName = (selectedResource: string) => {
    if (selectedResource === 'vpc-') {
      return 'VPC'
    }
    if (selectedResource === 'rtb-') {
      return '라우팅 테이블'
    }
    if (selectedResource === 'igw-') {
      return '인터넷 게이트웨이'
    }
    if (selectedResource === 'subnet-') {
      return '서브넷'
    }
    if (selectedResource === 'sg-') {
      return '보안 그룹'
    }
    if (selectedResource === 'i-') {
      return '인스턴스'
    }
    if (selectedResource === 'vol-') {
      return 'EBS 볼륨'
    }
    if (selectedResource === 'eni-') {
      return '네트워크 인터페이스'
    }
    if (selectedResource === 'bucket') {
      return 'S3'
    }
    if (selectedResource === 'none') {
      return ''
    }

    return ''
  }

  const filters = [
    {
      id: 'accountNameFilter',
      label: 'accountName',
      value: accountNameSelected,
      onClear: () => setAccountNameSelected(''),
      borderColor: 'liteBule',
    },
    {
      id: 'clientFilter',
      label: '고객사 선택',
      value: clientSelected,
      onClear: () => setClientSelected(''),
      borderColor: 'liteBule',
    },
    {
      id: 'accountIdFilter',
      label: 'accountId',
      value: accountIdSelected,
      onClear: () => setAccountIdSelected(''),
      borderColor: 'liteBule',
    },
    {
      id: 'vulnerabilityStatusFilter',
      label: '취약점 상태',
      value: vulnerabilityStatusSelected,
      onClear: () => setVulnerabilityStatusSelected(''),
      borderColor: 'orange',
    },
    {
      id: 'policySeverityFilter',
      label: '취약점 등급',
      value: policySeveritySelected,
      onClear: () => setPolicySeveritySelected(''),
      borderColor: 'orange',
    },
    {
      id: 'serviceFilter',
      label: 'service',
      value: serviceSelected,
      onClear: () => setServiceSelected(''),
      borderColor: 'orange',
    },
    {
      id: 'resourceFilter',
      label: 'Resource',
      value: resourceName(selectedResource),
      onClear: () => setSelectedResource(''),
      borderColor: 'orange',
    },
  ]
  useEffect(() => {}, [
    clientSelected,
    accountNameSelected,
    accountIdSelected,
    vulnerabilityStatusSelected,
    policySeveritySelected,
    serviceSelected,
  ])
  return (
    <div className='flex items-center gap-3'>
      {filters.map((filter, index: number) => (
        <FilterItem
          index={index}
          key={filter.id}
          id={filter.id}
          label={filter.label}
          value={filter.value}
          onClear={filter.onClear}
          borderColor={filter.borderColor}
        />
      ))}
    </div>
  )
}
