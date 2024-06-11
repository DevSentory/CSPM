'use client'

import { useFilter, useSelectType } from '@/components/Zustand/store'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

export default function AccountIdSelect() {
  /**
   * accountId 필터
   * accountIdSelected: accountId 필터에서 선택된 값
   * setAccountIdSelected: accountId 필터에서 선택된 값 담아둔 변수
   *
   * accountIdFilter: 필터의 리스트 data
   */

  const { accountIdSelected, setAccountIdSelected } = useSelectType()
  const { accountIdFilter } = useFilter()

  /**
   * accountId 필터에서 선택된 값에 따른 메서드
   * @param value : 선택된 값
   * setAccountIdSelected: none 이면 빈값, 아니면 value를 set
   */
  const handleAccountIdChange = (value: any) => {
    setAccountIdSelected(value === 'none' ? '' : value)
  }

  return (
    <div className='col-span-2 flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>
        Account ID
      </span>
      <Select
        value={accountIdSelected === '' ? 'none' : accountIdSelected}
        onValueChange={handleAccountIdChange}
      >
        <SelectTrigger className='h-full w-[60%]'>
          <SelectValue placeholder='Account ID' />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem id='select' value='none'>
              Account ID
            </SelectItem>
            {accountIdFilter.map((accountId, index) => (
              <SelectItem id={'select' + index} key={index} value={accountId}>
                {accountId}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
