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

export default function PolicySeveritySelect() {
  const { policySeveritySelected, setPolicySeveritySelected } = useSelectType()
  const handleServiceChange = (value: any) => {
    setPolicySeveritySelected(value === 'none' ? '' : value)
  }

  return (
    <div className='col-span-2 flex h-10 w-full items-center border-2 bg-white text-black'>
      <span className='flex h-full w-[40%] items-center justify-center  bg-gray-200'>
        취약점 등급
      </span>
      <Select value={policySeveritySelected} onValueChange={handleServiceChange}>
        <SelectTrigger className='h-full w-[60%]'>
          <SelectValue placeholder='취약점 등급 선택' />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value='none'>취약점 등급 선택</SelectItem>
            <SelectItem value='High'>High</SelectItem>
            <SelectItem value='Medium'>Medium</SelectItem>
            <SelectItem value='Low'>Low</SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  )
}
