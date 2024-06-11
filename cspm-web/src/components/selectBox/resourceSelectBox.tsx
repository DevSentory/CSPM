'use client'

import { useSelectType } from '@/components/Zustand/store'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

export default function ResourceSelect() {
  /**
   * service 필터
   * serviceSelected: service 필터에서 선택된 값
   * setServiceSelected: service 필터에서 선택된 값 담아둔 변수
   *
   * serviceFilter: 필터의 리스트 data
   */
  const { selectedResource, setSelectedResource, serviceSelected } = useSelectType()

  /**
   * service 필터에서 선택된 값에 따른 메서드
   * @param value : 선택된 값
   * setServiceSelected: none 이면 빈값, 아니면 value를 set
   */
  const handleServiceChange = (value: any) => {
    setSelectedResource(value === 'none' ? '' : value)
  }

  const selectlist = (serviceSelected: string) => {
    if (serviceSelected === 'VPC') {
      return (
        <>
          <SelectItem value='vpc-'>VPC</SelectItem>
          <SelectItem value='rtb-'>라우팅 테이블</SelectItem>
          <SelectItem value='igw-'>인터넷 게이트웨이</SelectItem>
          <SelectItem value='subnet-'>서브넷</SelectItem>
          <SelectItem value='sg-'>보안 그룹</SelectItem>
        </>
      )
    }
    if (serviceSelected === 'EC2') {
      return (
        <>
          <SelectItem value='i-'>인스턴스</SelectItem>
          <SelectItem value='vol-'>EBS 볼륨</SelectItem>
          <SelectItem value='eni-'>네트워크 인터페이스</SelectItem>
        </>
      )
    }

    if (serviceSelected === 'S3') {
      return (
        <>
          <SelectItem value='bucket'>S3</SelectItem>
        </>
      )
    }

    return (
      <>
        <SelectItem value='vpc-'>VPC</SelectItem>
        <SelectItem value='rtb-'>라우팅 테이블</SelectItem>
        <SelectItem value='igw-'>인터넷 게이트웨이</SelectItem>
        <SelectItem value='subnet-'>서브넷</SelectItem>
        <SelectItem value='sg-'>보안 그룹</SelectItem>
        <SelectItem value='i-'>인스턴스</SelectItem>
        <SelectItem value='vol-'>EBS 볼륨</SelectItem>
        <SelectItem value='eni-'>네트워크 인터페이스</SelectItem>
        <SelectItem value='bucket'>S3</SelectItem>
      </>
    )
  }

  return (
    <div className='col-span-2 flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>Resource</span>
      <Select
        value={selectedResource === '' ? 'none' : selectedResource}
        onValueChange={handleServiceChange}
      >
        <SelectTrigger className='h-full w-[60%]'>
          <SelectValue placeholder='Service 선택' />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value='none'>Resource 선택</SelectItem>
            {selectlist(serviceSelected)}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
