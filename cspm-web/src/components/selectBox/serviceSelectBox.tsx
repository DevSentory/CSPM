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

export default function ServiceSelect() {
  /**
   * service 필터
   * serviceSelected: service 필터에서 선택된 값
   * setServiceSelected: service 필터에서 선택된 값 담아둔 변수
   *
   * serviceFilter: 필터의 리스트 data
   */
  const { serviceSelected, setServiceSelected, selectedResource } = useSelectType()

  /**
   * service 필터에서 선택된 값에 따른 메서드
   * @param value : 선택된 값
   * setServiceSelected: none 이면 빈값, 아니면 value를 set
   */
  const handleServiceChange = (value: any) => {
    setServiceSelected(value === 'none' ? '' : value)
  }

  return (
    <div className='col-span-2 flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>Service</span>
      <Select value={serviceSelected} onValueChange={handleServiceChange}>
        <SelectTrigger className='h-full w-[60%]'>
          <SelectValue placeholder='Service 선택' />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value='none'>Service 선택</SelectItem>
            <SelectItem value='VPC'>VPC</SelectItem>
            <SelectItem value='EC2'>EC2</SelectItem>
            <SelectItem value='S3'>S3</SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
