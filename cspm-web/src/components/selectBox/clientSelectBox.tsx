'use client'

import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { useFilter, useSelectType } from '@/components/Zustand/store'

export default function ClientSelectBox() {
  /**
   * client 필터
   * clientSelected: client 필터에서 선택된 값
   * setClientSelected: client 필터에서 선택된 값 담아둔 변수
   *
   * clientFilter: 필터의 리스트 data
   */
  const { clientSelected, setClientSelected } = useSelectType()
  const { clientFilter } = useFilter()

  /**
   * client 필터에서 선택된 값에 따른 메서드
   * @param value : 선택된 값
   * setClientSelected: none 이면 빈값, 아니면 value를 set
   */
  const handleClientChange = (value: any) => {
    setClientSelected(value === 'none' ? '' : value)
  }
  return (
    <div className='col-span-2 flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>
        고객사 선택
      </span>
      <Select value={clientSelected} onValueChange={handleClientChange}>
        <SelectTrigger className='h-full w-[60%]'>
          <SelectValue placeholder='고객사 선택' />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value='none'>고객사 선택</SelectItem>
            {clientFilter?.map((client, index) => (
              <SelectItem key={index} value={client}>
                {client}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
