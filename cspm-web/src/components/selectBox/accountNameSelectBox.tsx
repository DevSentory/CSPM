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

export default function AccountNameSelect() {
  /**
   * accountName 필터
   * accountNameSelected: accountName 필터에서 선택된 값
   * setAccountNameSelected: accountName 필터에서 선택된 값 담아둔 변수
   *
   * accountNameFilter: 필터의 리스트 data
   */

  const { accountNameSelected, setAccountNameSelected } = useSelectType()
  const { accountNameFilter } = useFilter()

  /**
   * accountName 필터에서 선택된 값에 따른 메서드
   * @param value : 선택된 값
   * setAccountNameSelected: none 이면 빈값, 아니면 value를 set
   */
  const handleaccountNameChange = (value: any) => {
    setAccountNameSelected(value === 'none' ? '' : value)
  }

  return (
    <div className='flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>
        Account Name
      </span>
      <Select value={accountNameSelected} onValueChange={handleaccountNameChange}>
        <SelectTrigger
          id='account-selecttrigger'
          name='acocunt-selecttrigger'
          className='h-full w-[60%]'
        >
          <SelectValue id='' placeholder='Account Name' />
        </SelectTrigger>
        <SelectContent id=''>
          <SelectGroup id=''>
            <SelectItem id='account-select' value='none'>
              Account Name
            </SelectItem>
            {accountNameFilter?.map((accountName, index) => (
              <SelectItem id={`account-${index}-select`} key={index} value={accountName}>
                {accountName}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
